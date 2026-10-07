package pobi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pobi.repositories.LogicContainer;

public class ServiceTest {
    private LogicContainer container;

    @BeforeEach
    public void setUp() {
        container = new LogicContainer();
        container.fillData();
    }

    @Test
    public void testServiceConstructorPositive() {
        Client client = new Client("Maciej", "Mamrot", "696969696", "Kalinkow", "Knurowa", "12");
        Device device = new PC("PC1", "PC", "Boy", "Fat", "Mysz i kabel zasialający", 1000.0, "Intel", true);
        
        Service service = new Service("S1", 150.0, client, device, "Wymiana zasilacza");

        assertEquals("S1", service.getID());
        assertEquals(150.0, service.getBasePrice());
        assertEquals("Wymiana zasilacza", service.getFaultDescription());
        assertFalse(service.isArchive());
        assertNotNull(service.getClient());
        assertNotNull(service.getDevice());
    }

    @Test
    public void testServiceConstructorExceptions() {
        Client client = new Client("Maciej", "Mamrot", "696969696", "Kalinkow", "Knurowa", "12");
        Device device = new PC("PC1", "PC", "Boy", "Fat", "Mysz i kabel zasialający", 1000.0, "Intel", true);

        assertThrows(IllegalArgumentException.class, () -> new Service("", 150.0, client, device, "Opis"));
        assertThrows(IllegalArgumentException.class, () -> new Service("S1", -10.0, client, device, "Opis"));
        assertThrows(IllegalArgumentException.class, () -> new Service("S1", 150.0, null, device, "Opis"));
        assertThrows(IllegalArgumentException.class, () -> new Service("S1", 150.0, client, null, "Opis"));
        assertThrows(IllegalArgumentException.class, () -> new Service("S1", 150.0, client, device, ""));
    }

    @Test
    public void testServiceArchive() {
        Client client = new Client("Maciej", "Mamrot", "696969696", "Kalinkow", "Knurowa", "12");
        Device device = new PC("PC1", "PC", "Boy", "Fat", "Mysz i kabel zasialający", 1000.0, "Intel", true);
        Service service = new Service("S1", 150.0, client, device, "Wymiana obudowy na większą");

        assertFalse(service.isArchive());
        service.setArchive(true);
        assertTrue(service.isArchive());
    }

    @Test
    public void testServiceSetFaultDescription() {
        Client client = new Client("Maciej", "Mamrot", "696969696", "Kalinkow", "Knurowa", "12");
        Device device = new PC("PC1", "PC", "Boy", "Fat", "Mysz i kabel zasialający", 1000.0, "Intel", true);
        Service service = new Service("S1", 150.0, client, device, "Wymiana obudowy na większą");

        assertEquals("Wymiana obudowy na większą", service.getFaultDescription());

        service.setFaultDescription("Wymiana obudowy na większą i dodanie dodatkowego wentylatora");
        assertEquals("Wymiana obudowy na większą i dodanie dodatkowego wentylatora", service.getFaultDescription());
    }

    @Test
    public void testServicePrice() {
        Client client = new Client("Maciej", "Mamrot", "696969696", "Kalinkow", "Knurowa", "12");
        Device device = new PC("PC1", "PC", "Boy", "Fat", "Mysz i kabel zasialający", 1000.0, "Intel", true);
        
        Service service = new Service("S1", 1000.0, client, device, "Diagnoza sprzetu");

        assertEquals(1000.0, service.getBasePrice());
        assertEquals(1700.0, service.getDevice().getActualPrice(service.getBasePrice()));
    }
}