package pobi.managers;

import memory.MemoryClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pobi.model.Client;

import static org.junit.jupiter.api.Assertions.*;

public class ClientManagerTest {
    private ClientManager manager;

    @BeforeEach
    public void setUp() {
        manager = new ClientManager(new MemoryClientRepository());
    }

    @Test
    public void testRegisterAndFindClient() {
        Client client = manager.registerClient("Pankracy", "Panikiewicz", "123456789", "Łęczno", "Łysa", "12");
        assertTrue(client.getClientId() > 0);
        assertSame(client, manager.getClient(client.getClientId()));
        assertSame(client, manager.getClientByPhoneNumber("123456789"));
        assertEquals(1, manager.findAllClients().size());
    }

    @Test
    public void testDuplicatePhoneDoesNotAddClient() {
        manager.registerClient("Pankracy", "Panikiewicz", "123456789", "Łęczno", "Łysa", "12");
        assertThrows(IllegalArgumentException.class, () -> manager.registerClient("Stary Jaremba", "Kosssak", "123456789", "Ręczno", "Piotrkowska", "3"));
        assertEquals(1, manager.findAllClients().size());
    }

    @Test
    public void testChangePhoneNumber() {
        Client first = manager.registerClient("Mateusz", "Patulewicz", "123456789", "Łęczno", "Łysa", "12");
        manager.registerClient("Michał", "Świebodziński", "987654321", "Ręczno", "Piotrkowska", "3");
        manager.changePhoneNumber(first.getClientId(), "111222333");
        assertSame(first, manager.getClientByPhoneNumber("111222333"));
        assertThrows(IllegalArgumentException.class, () -> manager.getClientByPhoneNumber("123456789"));
        assertThrows(IllegalArgumentException.class, () -> manager.changePhoneNumber(first.getClientId(), "987654321"));
        assertEquals("111222333", first.getPhoneNumber());
    }

    @Test
    public void testArchiveAndMissingClient() {
        Client client = manager.registerClient("Patryk", "Kosssak", "123456789", "Łęczno", "Łysa", "12");
        manager.archiveClient(client.getClientId());
        assertTrue(manager.getClient(client.getClientId()).isArchive());
        assertThrows(IllegalStateException.class, () -> manager.changePhoneNumber(client.getClientId(), "111222333"));
        assertThrows(IllegalArgumentException.class, () -> manager.getClient(999));
    }
}
