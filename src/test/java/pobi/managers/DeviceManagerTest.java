package pobi.managers;

import memory.MemoryDeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pobi.model.Device;
import static org.junit.jupiter.api.Assertions.*;

public class DeviceManagerTest {
    private DeviceManager manager;

    @BeforeEach
    public void setUp() {
        manager = new DeviceManager(new MemoryDeviceRepository());
    }

    @Test
    public void testRegisterDevices() {
        Device first = manager.registerDevice("ThinkPad", "Lenovo", "myszka");
        Device second = manager.registerDevice("jabko pro", "Apple", "zasilacz");
        assertTrue(first.getId() > 0);
        assertNotEquals(first.getId(), second.getId());
        assertEquals("ThinkPad", manager.getDevice(first.getId()).getModel());
        assertEquals(2, manager.findAllDevices().size());
    }

    @Test
    public void testUnregisterDevice() {
        Device device = manager.registerDevice("jabko pro", "Apple", "zasilacz");
        manager.unregisterDevice(device.getId());
        assertTrue(manager.findAllDevices().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> manager.getDevice(device.getId()));
    }

    @Test
    public void testInvalidAndMissingDevice() {
        assertThrows(IllegalArgumentException.class, () -> manager.registerDevice("", "Apple", "zasilacz"));
        assertTrue(manager.findAllDevices().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> manager.getDevice(999));
    }
}
