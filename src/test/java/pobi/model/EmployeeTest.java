package pobi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void testEmployeeConstructorAndGetters() {
        Employee employee = new Employee(1, "Adam", "Nowak");
        assertEquals(1, employee.getId());
        assertEquals("Adam", employee.getFirstName());
        assertEquals("Nowak", employee.getLastName());
        assertEquals(5, employee.getMaxServices());
        assertEquals(0, employee.getActiveServices());
        assertTrue(employee.canTakeMoreServices());
        assertTrue(employee.getServices().isEmpty());
    }

    @Test
    public void testEmployeeConstructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Employee(-1, "Adam", "Nowak"));
        assertThrows(IllegalArgumentException.class, () -> new Employee(1, "", "Nowak"));
        assertThrows(IllegalArgumentException.class, () -> new Employee(1, "Adam", ""));
        assertThrows(IllegalArgumentException.class, () -> new Employee(1, null, "Nowak"));
    }

    @Test
    public void testAddServiceAndLimit() {
        Employee employee = new Employee(1, "Adam", "Nowak");
        Client client = new Client(1, "Jan", "Kowalski", "123456789", "Miasto", "Ulica", "1");
        ServiceOffering offering = new ServiceOffering(1, 100.0, "Usługa");
        Device device = new PC(1, "PC", "Dell", "Brak", "Intel", false);

        for (int i = 1; i <= 5; i++) {
            assertTrue(employee.canTakeMoreServices());
            Service service = new Service(i, client, employee, offering, device, "Zlecenie " + i);
            employee.addService(service);
            assertEquals(i, employee.getActiveServices());
        }

        // Limit 5 aktywnych zleceń osiągnięty
        assertFalse(employee.canTakeMoreServices());
        Service extraService = new Service(6, client, employee, offering, device, "Zlecenie 6");
        assertThrows(IllegalStateException.class, () -> employee.addService(extraService));

        // Po zakończeniu jednego zlecenia limit znowu pozwala na dodanie nowego
        employee.getServices().getFirst().finishService();
        assertEquals(4, employee.getActiveServices());
        assertTrue(employee.canTakeMoreServices());
        assertDoesNotThrow(() -> employee.addService(extraService));
    }
}
