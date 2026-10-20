package pobi.managers;

import memory.MemoryServiceOfferingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pobi.model.ServiceOffering;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceOfferingManagerTest {
    private ServiceOfferingManager manager;

    @BeforeEach
    public void setUp() {
        manager = new ServiceOfferingManager(new MemoryServiceOfferingRepository());
    }

    @Test
    public void testRegisterAndFindOffering() {
        ServiceOffering offering = manager.registerOffering(100, "Aktualizacja systemu");
        assertTrue(offering.getId() > 0);
        assertSame(offering, manager.getOffering(offering.getId()));
        assertEquals(100, manager.getOffering(offering.getId()).getBasePrice());
        assertEquals("Aktualizacja systemu", manager.getOffering(offering.getId()).getName());
        assertEquals(1, manager.findAllOfferings().size());
    }

    @Test
    public void testInvalidAndMissingOffering() {
        assertThrows(IllegalArgumentException.class, () -> manager.registerOffering(-1, "Diagnoza"));
        assertThrows(IllegalArgumentException.class, () -> manager.registerOffering(100, ""));
        assertTrue(manager.findAllOfferings().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> manager.getOffering(999));
    }
}