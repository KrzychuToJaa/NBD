package pobi.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pobi.repositories.LogicContainer;
import pobi.model.Client;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ClientManagerTest {
    private LogicContainer container;

    @BeforeEach
    public void setUp() {
        container = new LogicContainer();
        container.fillData();
    }

    @Test
    public void testGetExistingClient() {
        ClientManager manager = container.getClientManager();
        Client client = manager.getClient("123456789");
        
        assertEquals("LeBron", client.getFirstName());
        assertEquals("James", client.getLastName());
    }

    @Test
    public void testGetArchivedClient() {
        ClientManager manager = container.getClientManager();
        Client client = manager.getClient("123456789");
        client.setArchive(true);

        Client archivedClient = manager.getClient("123456789");
        assertNull(archivedClient);
    }

    @Test
    public void testRegisterDuplicateClient() {
        ClientManager manager = new ClientManager();
        manager.registerClient("Tomasz", "Szkocki", "111222333", "Houston", "Hardena", "13");
        
        assertEquals(1, manager.findAllClients().size());
        
        assertThrows(IllegalArgumentException.class, () -> {
            manager.registerClient("Konrad", "Lemur", "111222333", "Los Angeles", "Beverly", "2");
        });
    }

    @Test
    public void testUnregisterClient() {
        ClientManager manager = new ClientManager();
        manager.registerClient("Michał", "Jordański", "111222333", "Chicago", "drugi najlepszy", "23");
        
        assertEquals(1, manager.findAllClients().size());
        manager.unregisterClient("111222333");
        
        assertThrows(IllegalArgumentException.class, manager::findAllClients);
        assertEquals(0, manager.getClientRepository().size());
    }

    @Test
    public void testSaveAndLoadFromFile() throws Exception {
        ClientManager saveManager = new ClientManager();
        saveManager.registerClient("Stefan", "Curry", "444555666", "San Francisco", "Crackheadow", "13");
        
        String testFileName = "test_clients_manager.csv";
        saveManager.saveToFile(testFileName);
        saveManager.registerClient("Onufry", "Curry", "111222333", "San Francisco", "Crackheadow", "13");

        ClientManager loadManager = new ClientManager();
        loadManager.loadFromFile(testFileName);
        
        Client loadedClient = loadManager.getClient("444555666");
        assertThrows(IllegalArgumentException.class, () -> loadManager.getClient("111222333"));
        assertEquals("Stefan", loadedClient.getFirstName());
        assertEquals("San Francisco", loadedClient.getCity());
        
        Files.deleteIfExists(Paths.get(testFileName));
    }
}