package pobi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {
    private Client client;
    private Employee employee;
    private ServiceOffering offering;
    private Device device;

    @BeforeEach
    public void setUp() {
        client = new Client(1, "Maciej", "Mamrot", "123456789", "Łęczno", "Łysa", "12");
        employee = new Employee(1, "Adam", "Nowak");
        offering = new ServiceOffering(1, 200.0, "Wymiana dysku");
        device = new PC(1, "OptiPlex", "Dell", "Mysz", "Intel", false);
    }

    @Test
    public void testServiceConstructorPositive() {
        Service service = new Service(1, client, employee, offering, device, "Wymiana uszkodzonego dysku");

        assertEquals(1, service.getId());
        assertSame(client, service.getClient());
        assertSame(employee, service.getEmployee());
        assertSame(offering, service.getOffering());
        assertSame(device, service.getDevice());
        assertEquals(200.0, service.getBasePrice());
        assertEquals(300.0, service.getFinalPrice());
        assertEquals("Wymiana uszkodzonego dysku", service.getFaultDescription());
        assertFalse(service.isArchive());
        assertTrue(service.isActive());
    }

    @Test
    public void testServiceConstructorExceptions() {
        assertThrows(IllegalArgumentException.class, () -> new Service(-1, client, employee, offering, device, "Opis"));
        assertThrows(IllegalArgumentException.class, () -> new Service(1, null, employee, offering, device, "Opis"));
        assertThrows(IllegalArgumentException.class, () -> new Service(1, client, null, offering, device, "Opis"));
        assertThrows(IllegalArgumentException.class, () -> new Service(1, client, employee, null, device, "Opis"));
        assertThrows(IllegalArgumentException.class, () -> new Service(1, client, employee, offering, null, "Opis"));
        assertThrows(IllegalArgumentException.class, () -> new Service(1, client, employee, offering, device, ""));
        assertThrows(IllegalArgumentException.class, () -> new Service(1, client, employee, offering, device, "   "));
        assertThrows(IllegalArgumentException.class, () -> new Service(1, client, employee, offering, device, null));
    }

    @Test
    public void testServicePriceValidationExceptions() {
        assertThrows(IllegalArgumentException.class, () -> new Service(1, client, employee, offering, device, "Opis", -10.0, 100.0, false));
        assertThrows(IllegalArgumentException.class, () -> new Service(1, client, employee, offering, device, "Opis", 100.0, -5.0, false));
        assertThrows(IllegalArgumentException.class, () -> new Service(1, client, employee, offering, device, "Opis", Double.NaN, 100.0, false));
        assertThrows(IllegalArgumentException.class, () -> new Service(1, client, employee, offering, device, "Opis", 100.0, Double.POSITIVE_INFINITY, false));
    }

    @Test
    public void testCannotCreateServiceForArchivedClient() {
        client.archive();
        assertThrows(IllegalStateException.class, () -> new Service(1, client, employee, offering, device, "Opis"));
    }

    @Test
    public void testFinishService() {
        Service service = new Service(1, client, employee, offering, device, "Opis");
        assertTrue(service.isActive());
        assertFalse(service.isArchive());

        service.finishService();
        assertTrue(service.isArchive());
        assertFalse(service.isActive());

        assertThrows(IllegalStateException.class, service::finishService);
        assertThrows(IllegalStateException.class, () -> service.setFaultDescription("Nowy opis"));
    }

    @Test
    public void testSetFaultDescription() {
        Service service = new Service(1, client, employee, offering, device, "Pierwotny opis");
        service.setFaultDescription("Zaktualizowany opis");
        assertEquals("Zaktualizowany opis", service.getFaultDescription());

        assertThrows(IllegalArgumentException.class, () -> service.setFaultDescription(""));
        assertThrows(IllegalArgumentException.class, () -> service.setFaultDescription("   "));
        assertThrows(IllegalArgumentException.class, () -> service.setFaultDescription(null));
    }

    @Test
    public void testGetInfo() {
        Service service = new Service(1, client, employee, offering, device, "Diagnoza");
        String info = service.getInfo();
        assertTrue(info.contains("ID: 1"));
        assertTrue(info.contains("Klient: Maciej Mamrot"));
        assertTrue(info.contains("Pracownik: Adam Nowak"));
        assertTrue(info.contains("Usługa: Wymiana dysku"));
        assertTrue(info.contains("aktywna"));
    }
}
