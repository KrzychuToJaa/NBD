package pobi.managers;

import memory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pobi.model.Client;
import pobi.model.Device;
import pobi.model.Employee;
import pobi.model.Service;
import pobi.model.ServiceOffering;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceManagerTest {
    private ServiceManager manager;
    private Client client;
    private Employee employee;
    private Employee secondEmployee;
    private ServiceOffering offering;
    private Device device;

    @BeforeEach
    public void setUp() {
        var clients = new MemoryClientRepository();
        var devices = new MemoryDeviceRepository();
        var employees = new MemoryEmployeeRepository();
        var offerings = new MemoryServiceOfferingRepository();
        var services = new MemoryServiceRepository();
        client = new ClientManager(clients).registerClient("Świętosław", "Panikiewicz", "123456789", "Łęczno", "Łysa", "12");
        employee = new EmployeeManager(employees).registerEmployee("Bonifacy", "Patulewicz");
        secondEmployee = new EmployeeManager(employees).registerEmployee("Jaromir", "Kosssak");
        offering = new ServiceOfferingManager(offerings).registerOffering(100, "Aktualizacja systemu");
        device = new DeviceManager(devices).registerDevice("jabko pro", "Apple", "dysk zewnętrzny, zasilacz, myszka");
        manager = new ServiceManager(services, clients, devices, employees, offerings);
    }

    @Test
    public void testRegisterServicesForTheSameDevice() {
        Service first = manager.registerService(client.getClientId(), employee.getId(), offering.getId(), device.getId(), "Aktualizacja systemu");
        Service second = manager.registerService(client.getClientId(), employee.getId(), offering.getId(), device.getId(), "Ponowna konfiguracja");
        assertTrue(first.getId() > 0);
        assertNotEquals(first.getId(), second.getId());
        assertSame(first, manager.getService(first.getId()));
        assertSame(client, first.getClient());
        assertSame(employee, first.getEmployee());
        assertSame(device, first.getDevice());
        assertEquals(100, first.getFinalPrice());
        assertEquals(2, manager.getClientServices(client.getClientId()).size());
        assertEquals(2, client.getServices().size());
        assertEquals(2, employee.getActiveServices());
    }

    @Test
    public void testArchivedClientCannotRegisterService() {
        client.archive();
        assertThrows(IllegalStateException.class, () -> manager.registerService(client.getClientId(), employee.getId(), offering.getId(), device.getId(), "Aktualizacja"));
        assertTrue(manager.getAllServices().isEmpty());
    }

    @Test
    public void testEmployeeLimitDoesNotLimitClient() {
        for (int i = 0; i < 5; i++) {
            manager.registerService(client.getClientId(), employee.getId(), offering.getId(), device.getId(), "Zlecenie " + i);
        }
        assertThrows(IllegalStateException.class, () -> manager.registerService(client.getClientId(), employee.getId(), offering.getId(), device.getId(), "Szóste zlecenie"));
        assertEquals(5, manager.getAllServices().size());
        manager.registerService(client.getClientId(), secondEmployee.getId(), offering.getId(), device.getId(), "Zlecenie drugiego pracownika");
        assertEquals(6, manager.getClientServices(client.getClientId()).size());
        assertEquals(5, employee.getActiveServices());
        assertEquals(1, secondEmployee.getActiveServices());
    }

    @Test
    public void testClosingServiceFreesEmployeeCapacity() {
        for (int i = 0; i < 5; i++) {
            manager.registerService(client.getClientId(), employee.getId(), offering.getId(), device.getId(), "Zlecenie " + i);
        }
        Service first = manager.getAllServices().getFirst();
        manager.closeService(first.getId());
        assertTrue(manager.getService(first.getId()).isArchive());
        assertEquals(4, employee.getActiveServices());
        manager.registerService(client.getClientId(), employee.getId(), offering.getId(), device.getId(), "Nowe zlecenie");
        assertEquals(5, employee.getActiveServices());
        assertEquals(6, manager.getAllServices().size());
    }

    @Test
    public void testDescriptionAndMissingService() {
        Service service = manager.registerService(client.getClientId(), employee.getId(), offering.getId(), device.getId(), "Pierwotny opis");
        manager.changeServiceDescription(service.getId(), "Nowy opis");
        assertEquals("Nowy opis", manager.getService(service.getId()).getFaultDescription());
        manager.closeService(service.getId());
        assertThrows(IllegalStateException.class, () -> manager.changeServiceDescription(service.getId(), "Zmiana po zakończeniu"));
        assertThrows(IllegalArgumentException.class, () -> manager.getService(999));
    }
}
