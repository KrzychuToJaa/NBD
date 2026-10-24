package pobi;

import memory.*;
import org.junit.jupiter.api.Test;
import pobi.repositories.LogicContainer;
import static org.junit.jupiter.api.Assertions.*;

public class LogicContainerTest {
    @Test
    public void testFillData() {
        LogicContainer container = new LogicContainer();
        container.fillData();
        assertEquals(2, container.getClientManager().findAllClients().size());
        assertEquals(2, container.getDeviceManager().findAllDevices().size());
        assertEquals(2, container.getEmployeeManager().findAllEmployees().size());
        assertEquals(2, container.getOfferingManager().findAllOfferings().size());
        assertEquals(3, container.getServiceManager().getAllServices().size());
        container.fillData();
        assertEquals(3, container.getServiceManager().getAllServices().size());
    }

    @Test
    public void testInjectedRepositories() {
        var clients = new MemoryClientRepository();
        LogicContainer container = new LogicContainer(clients, new MemoryDeviceRepository(), new MemoryEmployeeRepository(), new MemoryServiceOfferingRepository(), new MemoryServiceRepository());
        var client = container.getClientManager().registerClient("Pankracy", "Panikiewicz", "123456789", "Łęczno", "Łysa", "12");
        assertSame(client, clients.findById(client.getClientId()));
    }
}
