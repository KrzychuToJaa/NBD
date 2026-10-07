package pobi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pobi.repositories.LogicContainer;

public class ClientTest {
    private LogicContainer container;

    @BeforeEach
    public void setUp() {
        container = new LogicContainer();
        container.fillData();
    }

    @Test
    public void testClientConstructor() {
        Client client = new Client("Mateusz", "Jankowski", "123456789", "Warszawa", "Knurowa", "12");
        
        assertEquals("Mateusz", client.getFirstName());
        assertEquals("Jankowski", client.getLastName());
        assertEquals("123456789", client.getPhoneNumber());
        assertEquals("Warszawa", client.getCity());
        assertEquals("Knurowa", client.getStreet());
        assertEquals("12", client.getNumber());
        assertFalse(client.isArchive());
        assertEquals(3, client.getMaxServices());
    }

    @Test
    public void testClientConstructorExceptions() {
        assertThrows(IllegalArgumentException.class, () -> new Client("", "Jankowski", "123456789", "Warszawa", "Knurowa", "12"));
        assertThrows(IllegalArgumentException.class, () -> new Client("Mateusz", "Jankowski", "12345678", "Warszawa", "Knurowa", "12"));
        assertThrows(IllegalArgumentException.class, () -> new Client("Mateusz", "Jankowski", "1234567890", "Warszawa", "Knurowa", "12"));
        assertThrows(IllegalArgumentException.class, () -> new Client("Mateusz", "Jankowski", "123456789", "Warszawa", "Knurowa", "1234"));
    }

    @Test
    public void testClientArchiveStatus() {
        Client client = new Client("Mateusz", "Jankowski", "123456789", "Warszawa", "Knurowa", "12");
        assertFalse(client.isArchive());
        
        client.setArchive(true);
        assertTrue(client.isArchive());
    }

    @Test
    public void testClientMaxServices() {
        Client client = new Client("Mateusz", "Kisielewski", "676767676", "Komputerowo", "Knurowa", "12");
        assertEquals(3, client.getMaxServices());
        
        client.setMaxServices(5);
        assertEquals(5, client.getMaxServices());
        
        assertThrows(IllegalArgumentException.class, () -> client.setMaxServices(-1));
    }
}