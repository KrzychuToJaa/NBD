package pobi.managers;

import memory.MemoryEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pobi.model.Employee;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeManagerTest {
    private EmployeeManager manager;

    @BeforeEach
    public void setUp() {
        manager = new EmployeeManager(new MemoryEmployeeRepository());
    }

    @Test
    public void testRegisterAndFindEmployees() {
        Employee first = manager.registerEmployee("Grzymisław", "Panikiewicz");
        Employee second = manager.registerEmployee("Paweł", "Świebodziński");
        assertTrue(first.getId() > 0);
        assertNotEquals(first.getId(), second.getId());
        assertSame(first, manager.getEmployee(first.getId()));
        assertEquals("Grzymisław", manager.getEmployee(first.getId()).getFirstName());
        assertEquals(2, manager.findAllEmployees().size());
    }

    @Test
    public void testInvalidAndMissingEmployee() {
        assertThrows(IllegalArgumentException.class, () -> manager.registerEmployee("", "Kosssak"));
        assertTrue(manager.findAllEmployees().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> manager.getEmployee(999));
    }
}