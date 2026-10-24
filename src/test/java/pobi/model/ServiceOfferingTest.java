package pobi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceOfferingTest {
    @Test
    public void testCreateOffering() {
        ServiceOffering offering = new ServiceOffering(1, 100.0, "Aktualizacja systemu");
        assertEquals(1, offering.getId());
        assertEquals("Aktualizacja systemu", offering.getName());
        assertEquals(100.0, offering.getBasePrice());
    }

    @Test
    public void testChangePrice() {
        ServiceOffering offering = new ServiceOffering(1, 100.0, "Aktualizacja systemu");
        offering.setBasePrice(150.0);
        assertEquals(150.0, offering.getBasePrice());
    }

    @Test
    public void testInvalidOffering() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceOffering(0, -1.0, "Aktualizacja"));
        assertThrows(IllegalArgumentException.class, () -> new ServiceOffering(0, Double.NaN, "Aktualizacja"));
        assertThrows(IllegalArgumentException.class, () -> new ServiceOffering(0, 100.0, " "));
    }
}
