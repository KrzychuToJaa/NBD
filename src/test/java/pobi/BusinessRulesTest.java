package pobi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pobi.model.*;
import pobi.repositories.LogicContainer;
import pobi.infrastructure.jpa.mapper.ClientMapper;
import pobi.infrastructure.jpa.mapper.ServiceMapper;
import memory.MemoryDeviceRepository;
import memory.MemoryClientRepository;
import static org.junit.jupiter.api.Assertions.*;

class BusinessRulesTest {
    private LogicContainer container;
    private Client client;
    private Employee employee;
    private Device device;
    private ServiceOffering offering;

    @BeforeEach
    void setUp() {
        container = new LogicContainer();
        client = container.getClientManager().registerClient("Jan", "Kowalski", "012345678", "Łódź", "Polna", "1");
        employee = container.getEmployeeManager().registerEmployee("Anna", "Nowak");
        device = container.getDeviceManager().registerPC("Model", "Marka", "Zasilacz", "Intel", true);
        offering = container.getOfferingManager().registerOffering(100, "Naprawa");
    }

    private Service registerService() {
        return container.getServiceManager().registerService(client.getClientId(), employee.getId(),
            offering.getId(), device.getId(), "Uszkodzenie");
    }

    @Test
    void automaticIdsAreReturnedAndNeverReusedAfterRemoval() {
        assertTrue(client.getClientId() > 0);
        assertTrue(employee.getId() > 0);
        assertTrue(offering.getId() > 0);
        Service first = registerService();
        Service second = registerService();
        assertTrue(second.getId() > first.getId());
        MemoryDeviceRepository repository = new MemoryDeviceRepository();
        Device removed = repository.add(new Laptop(0, "M", "B", "A", "Intel"));
        repository.remove(removed);
        Device next = repository.add(new Laptop(0, "M", "B", "A", "Intel"));
        assertTrue(next.getId() > removed.getId());
        assertSame(next, repository.findById(next.getId()));
    }

    @Test
    void restoredIdsAdvanceTheCounterAndDuplicatesAreRejected() {
        MemoryClientRepository repository = new MemoryClientRepository();
        Client restored = new Client(40, "A", "B", "111222333", "C", "D", "1");
        repository.add(restored);
        Client next = repository.add(new Client(0, "A", "B", "222333444", "C", "D", "1"));
        assertEquals(41, next.getClientId());
        assertThrows(IllegalArgumentException.class, () -> repository.add(restored));
    }

    @Test
    void changingThePriceListDoesNotChangeStoredPricesOrMappedPrices() {
        Service service = registerService();
        double finalPrice = service.getFinalPrice();
        offering.setBasePrice(500);
        assertEquals(100, service.getBasePrice());
        assertEquals(finalPrice, service.getFinalPrice());
        Service restored = ServiceMapper.toDomain(ServiceMapper.toEntity(service));
        assertEquals(100, restored.getBasePrice());
        assertEquals(finalPrice, restored.getFinalPrice());
        assertEquals(client.getClientId(), restored.getClient().getClientId());
    }

    @Test
    void finishedServiceCannotBeEditedOrFinishedAgainEvenOutsideTheMenu() {
        Service service = registerService();
        container.getServiceManager().closeService(service.getId());
        assertThrows(IllegalStateException.class, () -> service.setFaultDescription("Nowy opis"));
        assertThrows(IllegalStateException.class, service::finishService);
        assertThrows(IllegalStateException.class, () -> container.getServiceManager().changeServiceDescription(service.getId(), "Opis"));
        assertThrows(IllegalStateException.class, () -> container.getServiceManager().closeService(service.getId()));
        assertSame(service, container.getServiceManager().getService(service.getId()));
        assertEquals("Uszkodzenie", service.getFaultDescription());
    }

    @Test
    void activeServiceCanBeEditedAndReleasesTheEmployeeLimitWhenClosed() {
        for (int i = 0; i < 5; i++) { registerService(); }
        assertThrows(IllegalStateException.class, this::registerService);
        var first = employee.getServices().getFirst();
        first.setFaultDescription("Diagnoza");
        assertEquals("Diagnoza", first.getFaultDescription());
        first.finishService();
        assertDoesNotThrow(this::registerService);
        assertEquals(5, employee.getActiveServices());
    }

    @Test
    void archivingIsRejectedUntilEveryServiceIsFinished() {
        Service first = registerService();
        Service second = registerService();
        assertThrows(IllegalStateException.class, client::archive);
        first.finishService();
        assertThrows(IllegalStateException.class, () -> container.getClientManager().archiveClient(client.getClientId()));
        second.finishService();
        container.getClientManager().archiveClient(client.getClientId());
        assertTrue(client.isArchive());
        assertSame(client, container.getClientManager().getClient(client.getClientId()));
        assertEquals(2, container.getServiceManager().getClientServices(client.getClientId()).size());
    }

    @Test
    void archivedClientIsReadOnlyButStillSearchable() {
        container.getClientManager().archiveClient(client.getClientId());
        assertThrows(IllegalStateException.class, () -> client.changePhoneNumber("123456789"));
        assertThrows(IllegalStateException.class, () -> container.getClientManager().changePhoneNumber(client.getClientId(), "123456789"));
        assertThrows(IllegalStateException.class, client::archive);
        assertThrows(IllegalStateException.class, this::registerService);
        assertSame(client, container.getClientManager().getClientByPhoneNumber("012345678"));
        assertTrue(container.getClientManager().findAllClients().contains(client));
        assertEquals("012345678", client.getPhoneNumber());
    }

    @Test
    void phoneChangeKeepsIdentityAndServiceHistory() {
        Service service = registerService();
        long id = client.getClientId();
        container.getClientManager().changePhoneNumber(id, "123456789");
        assertEquals(id, client.getClientId());
        assertSame(client, service.getClient());
        assertEquals("123456789", service.getClient().getPhoneNumber());
        assertSame(client, container.getClientManager().getClientByPhoneNumber("123456789"));
        assertThrows(IllegalArgumentException.class, () -> container.getClientManager().getClientByPhoneNumber("012345678"));
        assertEquals(1, container.getServiceManager().getClientServices(id).size());
    }

    @Test
    void duplicatePhoneIsRejectedOnRegistrationAndChangeIncludingArchivedClients() {
        var other = container.getClientManager().registerClient("A", "B", "111222333", "C", "D", "1");
        assertThrows(IllegalArgumentException.class, () -> container.getClientManager().registerClient("A", "B", new String("012345678"), "C", "D", "1"));
        assertThrows(IllegalArgumentException.class, () -> container.getClientManager().changePhoneNumber(other.getClientId(), "012345678"));
        client.archive();
        assertThrows(IllegalArgumentException.class, () -> container.getClientManager().changePhoneNumber(other.getClientId(), "012345678"));
        assertEquals("111222333", other.getPhoneNumber());
    }

    @Test
    void phoneValidationPreservesLeadingZerosAndRejectsInvalidInput() {
        assertEquals("012345678", client.getPhoneNumber());
        for (String invalid : new String[]{null, "", "12345678", "1234567890", "abcdefghi"}) {
            assertThrows(IllegalArgumentException.class, () -> container.getClientManager().changePhoneNumber(client.getClientId(), invalid));
        }
        assertEquals("012345678", client.getPhoneNumber());
    }

    @Test
    void missingObjectsConsistentlyThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> container.getClientManager().getClient(999));
        assertThrows(IllegalArgumentException.class, () -> container.getClientManager().getClientByPhoneNumber("999999999"));
        assertThrows(IllegalArgumentException.class, () -> container.getDeviceManager().getDevice(999));
        assertThrows(IllegalArgumentException.class, () -> container.getEmployeeManager().getEmployee(999));
        assertThrows(IllegalArgumentException.class, () -> container.getOfferingManager().getOffering(999));
        assertThrows(IllegalArgumentException.class, () -> container.getServiceManager().getService(999));
        assertThrows(IllegalArgumentException.class, () -> container.getServiceManager().getClientServices(999));
    }

    @Test
    void mappedArchiveAndFinishedStateCannotBeReversed() {
        Service service = registerService();
        service.finishService();
        client.archive();
        Client restoredClient = ClientMapper.toDomain(ClientMapper.toEntity(client));
        assertEquals(client.getClientId(), restoredClient.getClientId());
        assertTrue(restoredClient.isArchive());
        assertThrows(IllegalStateException.class, () -> restoredClient.changePhoneNumber("111222333"));
        Service restored = ServiceMapper.toDomain(ServiceMapper.toEntity(service));
        assertTrue(restored.isArchive());
        assertThrows(IllegalStateException.class, restored::finishService);
        assertThrows(IllegalStateException.class, () -> restored.setFaultDescription("Zmiana"));
    }

    @Test
    void mappedActiveServiceBlocksClientArchiving() {
        Service restored = ServiceMapper.toDomain(ServiceMapper.toEntity(registerService()));
        assertThrows(IllegalStateException.class, restored.getClient()::archive);
        restored.finishService();
        assertDoesNotThrow(restored.getClient()::archive);
    }

    @Test
    void invalidSnapshotPricesAreRejected() {
        for (double price : new double[]{-1, Double.NaN, Double.POSITIVE_INFINITY}) {
            assertThrows(IllegalArgumentException.class, () -> new Service(0, client, employee, offering, device, "Opis", price, 100, false));
            assertThrows(IllegalArgumentException.class, () -> new Service(0, client, employee, offering, device, "Opis", 100, price, false));
        }
    }

    @Test
    void exampleDataStillLoadsWithGeneratedIds() {
        LogicContainer examples = new LogicContainer();
        examples.fillData();
        assertEquals(2, examples.getClientManager().findAllClients().size());
        assertEquals(4, examples.getDeviceManager().findAllDevices().size());
        assertEquals(3, examples.getServiceManager().getAllServices().size());
    }
}
