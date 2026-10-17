// package pobi.model;

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// public class ClientTest {

//     @Test
//     public void testClientConstructor() {
//         Client client = new Client("Mateusz", "Jankowski", 123456789L, "Warszawa", "Knurowa", "12");
        
//         assertEquals("Mateusz", client.getFirstName());
//         assertEquals("Jankowski", client.getLastName());
//         assertEquals(123456789L, client.getPhoneNumber());
//         assertEquals("Warszawa", client.getCity());
//         assertEquals("Knurowa", client.getStreet());
//         assertEquals("12", client.getNumber());
//         assertFalse(client.isArchive());
//     }

//     @Test
//     public void testClientConstructorExceptions() {
//         assertThrows(IllegalArgumentException.class, () -> new Client("   ", "Jankowski", 123456789L, "Warszawa", "Knurowa", "12"));
//         assertThrows(IllegalArgumentException.class, () -> new Client("", "Jankowski", 123456789L, "Warszawa", "Knurowa", "12"));
//         // Test sprawdzający długość numeru telefonu (za krótki - 8 cyfr):
//         assertThrows(IllegalArgumentException.class, () -> new Client("Mateusz", "Jankowski", 12345678L, "Warszawa", "Knurowa", "12"));
//         // Test sprawdzający długość numeru telefonu (za długi - 10 cyfr):
//         assertThrows(IllegalArgumentException.class, () -> new Client("Mateusz", "Jankowski", 1234567890L, "Warszawa", "Knurowa", "12"));
//         // Test sprawdzający ujemny numer telefonu:
//         assertThrows(IllegalArgumentException.class, () -> new Client("Mateusz", "Jankowski", -123456789L, "Warszawa", "Knurowa", "12"));
//         // Za długi numer domu:
//         assertThrows(IllegalArgumentException.class, () -> new Client("Mateusz", "Jankowski", 123456789L, "Warszawa", "Knurowa", "1234"));
//     }

//     @Test
//     public void testClientArchiveStatus() {
//         Client client = new Client("Mateusz", "Jankowski", 123456789L, "Warszawa", "Knurowa", "12");
//         assertFalse(client.isArchive());
        
//         client.setArchive(true);
//         assertTrue(client.isArchive());
//     }
// }
