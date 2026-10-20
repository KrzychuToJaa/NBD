package pobi.managers;

import memory.MemoryDeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pobi.model.Console;
import pobi.model.Device;
import pobi.model.Laptop;
import pobi.model.PC;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceManagerTest {
    private DeviceManager manager;

    @BeforeEach
    public void setUp() {
        manager = new DeviceManager(new MemoryDeviceRepository());
    }

    @Test
    public void testRegisterDifferentDeviceTypes() {
        Device pc = manager.registerPC("OptiPlex", "Dell", "myszka", "Intel", true);
        Device laptop = manager.registerLaptop("jabko pro", "Apple", "dysk zewnętrzny, zasilacz, myszka", "Intel");
        Device console = manager.registerConsole("Switch", "Nintendo", "JoyCon", Console.ConsoleType.Nintendo);
        assertInstanceOf(PC.class, manager.getDevice(pc.getId()));
        assertInstanceOf(Laptop.class, manager.getDevice(laptop.getId()));
        assertInstanceOf(Console.class, manager.getDevice(console.getId()));
        assertTrue(pc.getId() > 0);
        assertNotEquals(pc.getId(), laptop.getId());
        assertNotEquals(laptop.getId(), console.getId());
        assertEquals("jabko pro", manager.getDevice(laptop.getId()).getModel());
        assertEquals(3, manager.findAllDevices().size());
    }

    @Test
    public void testUnregisterDevice() {
        Device laptop = manager.registerLaptop("jabko pro", "Apple", "dysk zewnętrzny, zasilacz, myszka", "Intel");
        manager.unregisterDevice(laptop.getId());
        assertTrue(manager.findAllDevices().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> manager.getDevice(laptop.getId()));
    }

    @Test
    public void testInvalidAndMissingDevice() {
        assertThrows(IllegalArgumentException.class, () -> manager.registerLaptop("", "Apple", "zasilacz", "Intel"));
        assertTrue(manager.findAllDevices().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> manager.getDevice(999));
    }
}