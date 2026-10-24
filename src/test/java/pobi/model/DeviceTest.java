package pobi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeviceTest {
    @Test
    public void testDeviceConstructor() {
        Device device = new Device(1, "jabko pro", "Apple", "dysk zewnętrzny, zasilacz, myszka");
        assertEquals(1, device.getId());
        assertEquals("jabko pro", device.getModel());
        assertEquals("Apple", device.getBrand());
        assertEquals("dysk zewnętrzny, zasilacz, myszka", device.getAccessories());
    }

    @Test
    public void testInvalidDeviceData() {
        assertThrows(IllegalArgumentException.class, () -> new Device(-1, "ThinkPad", "Lenovo", "zasilacz"));
        assertThrows(IllegalArgumentException.class, () -> new Device(0, " ", "Lenovo", "zasilacz"));
        assertThrows(IllegalArgumentException.class, () -> new Device(0, "ThinkPad", null, "zasilacz"));
        assertThrows(IllegalArgumentException.class, () -> new Device(0, "ThinkPad", "Lenovo", ""));
    }

    @Test
    public void testDeviceInfo() {
        Device device = new Device(1, "ThinkPad", "Lenovo", "zasilacz");
        assertTrue(device.getInfo().contains("ThinkPad"));
        assertTrue(device.getInfo().contains("Lenovo"));
        assertTrue(device.getInfo().contains("zasilacz"));
    }
}
