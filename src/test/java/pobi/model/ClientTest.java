package pobi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testClientConstructor() {
        Client client = new Client(1, "Mateusz", "Jankowski", "123456789", "Warszawa", "Knurowa", "12");

        assertEquals(1, client.getClientId());
        assertEquals("Mateusz", client.getFirstName());
        assertEquals("Jankowski", client.getLastName());
        assertEquals("123456789", client.getPhoneNumber());
        assertEquals("Warszawa", client.getCity());
        assertEquals("Knurowa", client.getStreet());
        assertEquals("12", client.getNumber());
        assertFalse(client.isArchive());
        assertTrue(client.getServices().isEmpty());
    }

    @Test
    public void testClientConstructorExceptions() {
        assertThrows(IllegalArgumentException.class, () -> new Client(0, "   ", "Jankowski", "123456789", "Warszawa", "Knurowa", "12"));
        assertThrows(IllegalArgumentException.class, () -> new Client(0, "", "Jankowski", "123456789", "Warszawa", "Knurowa", "12"));
        assertThrows(IllegalArgumentException.class, () -> new Client(0, "Mateusz", "", "123456789", "Warszawa", "Knurowa", "12"));
        assertThrows(IllegalArgumentException.class, () -> new Client(-1, "Mateusz", "Jankowski", "123456789", "Warszawa", "Knurowa", "12"));
        assertThrows(IllegalArgumentException.class, () -> new Client(0, "Mateusz", "Jankowski", "12345678", "Warszawa", "Knurowa", "12"));
        assertThrows(IllegalArgumentException.class, () -> new Client(0, "Mateusz", "Jankowski", "1234567890", "Warszawa", "Knurowa", "12"));
        assertThrows(IllegalArgumentException.class, () -> new Client(0, "Mateusz", "Jankowski", "12345678a", "Warszawa", "Knurowa", "12"));
        assertThrows(IllegalArgumentException.class, () -> new Client(0, "Mateusz", "Jankowski", "123456789", "Warszawa", "Knurowa", "1234"));
    }

    @Test
    public void testChangePhoneNumber() {
        Client client = new Client(1, "Mateusz", "Jankowski", "012345678", "Warszawa", "Knurowa", "12");
        client.changePhoneNumber("987654321");
        assertEquals("987654321", client.getPhoneNumber());

        assertThrows(IllegalArgumentException.class, () -> client.changePhoneNumber("invalid"));
        assertThrows(IllegalArgumentException.class, () -> client.changePhoneNumber(null));
    }

    @Test
    public void testArchivingClient() {
        Client client = new Client(1, "Mateusz", "Jankowski", "123456789", "Warszawa", "Knurowa", "12");
        assertFalse(client.isArchive());

        client.archive();
        assertTrue(client.isArchive());

        assertThrows(IllegalStateException.class, client::archive);
        assertThrows(IllegalStateException.class, () -> client.changePhoneNumber("999888777"));
    }

    @Test
    public void testArchivingClientWithActiveServicesFails() {
        Client client = new Client(1, "Mateusz", "Jankowski", "123456789", "Warszawa", "Knurowa", "12");
        Employee employee = new Employee(1, "Adam", "Nowak");
        ServiceOffering offering = new ServiceOffering(1, 100.0, "Diagnoza");
        Device device = new Device(1, "Optiplex", "Dell", "Brak");

        Service service = new Service(1, client, employee, offering, device, "Opis problemu");
        client.addService(service);

        assertThrows(IllegalStateException.class, client::archive);

        service.finishService();
        assertDoesNotThrow(client::archive);
        assertTrue(client.isArchive());
    }

    @Test
    public void testGetInfo() {
        Client client = new Client(1, "Mateusz", "Jankowski", "123456789", "Warszawa", "Knurowa", "12");
        String info = client.getInfo();
        assertTrue(info.contains("Mateusz"));
        assertTrue(info.contains("Jankowski"));
        assertTrue(info.contains("123456789"));
        assertTrue(info.contains("aktywny"));
    }
}
