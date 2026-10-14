// package pobi.managers;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// import pobi.model.Device;
// import pobi.repositories.LogicContainer;

// public class DeviceManagerTest {
//     private LogicContainer container;

//     @BeforeEach
//     public void setUp() {
//         container = new LogicContainer();
//         container.fillData();
//     }

//     @Test
//     public void testGetExistingDevice() {
//         DeviceManager manager = container.getDeviceManager();
//         Device device = manager.getDevice("PC1");
        
//         assertNotNull(device);
//         assertEquals("Dell", device.getBrand());
//         assertEquals(800.0, device.getBasePrice());
//     }

//     @Test
//     public void testGetNonExistingDevice() {
//         DeviceManager manager = container.getDeviceManager();
//         assertThrows(IllegalArgumentException.class, () -> manager.getDevice("2137"));
//     }

//     @Test
//     public void testFindAllEmpty() {
//         DeviceManager emptyManager = new DeviceManager();
//         assertThrows(IllegalArgumentException.class, emptyManager::findAllDevices);
//     }

//     @Test
//     public void testUnregisterDevice() {
//         DeviceManager manager = container.getDeviceManager();
//         assertEquals(3, manager.findAllDevices().size());
        
//         manager.unregisterDevice("Console1");
//         assertEquals(2, manager.findAllDevices().size());
        
//         assertThrows(IllegalArgumentException.class, () -> manager.getDevice("Console1"));
//     }

// }
