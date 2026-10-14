// package pobi.managers;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// import pobi.model.Client;
// import pobi.model.Device;
// import pobi.model.Service;

// import pobi.repositories.LogicContainer;

// public class ServiceManagerTest {
//     private LogicContainer container;

//     @BeforeEach
//     public void setUp() {
//         container = new LogicContainer();
//         container.fillData();
//     }

//     @Test
//     public void testServiceManagerRegisterAndGet() {
//         ClientManager clientManager = container.getClientManager();
//         DeviceManager deviceManager = container.getDeviceManager();
//         ServiceManager serviceManager = container.getServiceManager();

//         serviceManager.registerService("S1nowy", 300.0, clientManager.getClient("123456789"), deviceManager.getDevice("PC1"), "Wymiana plyty");
//         Service service = serviceManager.getService("S1nowy");
        
//         assertEquals("Wymiana plyty", service.getFaultDescription());
//         assertEquals(300.0, service.getBasePrice());
//     }

//     @Test
//     public void testServiceManagerArchive() {
//         ServiceManager serviceManager = container.getServiceManager();
//         ClientManager clientManager = container.getClientManager();
//         DeviceManager deviceManager = container.getDeviceManager();

//         Client client = clientManager.getClient("123456789");
//         Device device = deviceManager.getDevice("PC1");

//         client.setArchive(true);
//         assertThrows(IllegalStateException.class, () -> serviceManager.registerService("S2", 200.0, client, device, "Czyszczenie"));

//         Service service = serviceManager.getService("Service1");
//         assertNotNull(service);

//         service.setArchive(true);
//         Service archivedService = serviceManager.getService("Service1");
//         assertNull(archivedService);
//     }

//     @Test
//     public void testServiceManagerGetNonExisting() {
//         ServiceManager serviceManager = container.getServiceManager();
//         assertThrows(IllegalArgumentException.class, () -> serviceManager.getService("2137"));
//     }

//     @Test
//     public void testServiceManagerUnregister() {
//         ServiceManager serviceManager = container.getServiceManager();
//         assertEquals(3, serviceManager.findAllServices().size());

//         serviceManager.unregisterService("Service1");
        
//         assertEquals(2, serviceManager.findAllServices().size());
//         assertThrows(IllegalArgumentException.class, () -> serviceManager.getService("Service1"));
//     }

//     @Test
//     public void testMaxServicesLimit() {
//         ClientManager clientManager = container.getClientManager();
//         DeviceManager deviceManager = container.getDeviceManager();
//         ServiceManager serviceManager = container.getServiceManager();

//         Client client = clientManager.getClient("123456789");
//         Device device = deviceManager.getDevice("PC1");

//         serviceManager.registerService("S1", 300.0, client, device, "Wymiana płyty głównej");
//         serviceManager.registerService("S2", 200.0, client, device, "Czyszczenie");

//         assertThrows(IllegalStateException.class, () -> serviceManager.registerService("S3", 400.0, client, device, "Wymiana dysku"));
//     }
// }
