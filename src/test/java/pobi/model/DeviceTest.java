package pobi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pobi.repositories.LogicContainer;

public class DeviceTest {
    private LogicContainer container;

    @BeforeEach
    public void setUp() {
        container = new LogicContainer();
        container.fillData();
    }

    @Test
    public void testDeviceConstructors() {
        PC pc = new PC("ID1", "PC", "MegaDuperPC", "SuperBrand", "Myszka i monitor", 1000.0, "Intel", true);
        assertEquals("ID1", pc.getID());
        assertEquals("PC", pc.getType());
        assertEquals(1000.0, pc.getBasePrice());
        assertEquals("Intel", pc.getCpuManufacturer());

        Laptop laptop = new Laptop("ID2", "Laptop", "Model Astra Zeneca", "Szczepion", "Torba", 2000.0, "AMD");
        assertEquals("ID2", laptop.getID());
        assertEquals("AMD", laptop.getCpuManufacturer());

        Console console = new Console("ID3", "Console", "Model Y", "Tesla", "Pad i kabel", 1500.0, Console.ConsoleType.PlayStation);
        assertEquals(Console.ConsoleType.PlayStation, console.getConsoleType());
        assertEquals("PlayStation", console.getConsoleTypeName());
    }

    @Test
    public void testDeviceConstructorExceptions() {
        assertThrows(IllegalArgumentException.class, () -> new PC("ID1", "PC", "Pocket", "NeoGeo", "klawiatura", -10.0, "Intel", true));
        assertThrows(IllegalArgumentException.class, () -> new Laptop("ID2", "Laptop", "Plaster", "Asus", "kabel zasilający", -1.0, "AMD"));
        assertThrows(IllegalArgumentException.class, () -> new Console("ID3", "Console", "Micro", "GameBoy", "dwa pady", -500.0, Console.ConsoleType.PlayStation));

        assertThrows(IllegalArgumentException.class, () -> new PC("", "PC", "KFD", "Robur", "brak", 1000.0, "Intel", true));
        assertThrows(IllegalArgumentException.class, () -> new Laptop("ID2", "", "Earth", "Jupiter", "Myszka i torba", 2000.0, "AMD"));
    }

    @Test
    public void testDeviceActualPrice() {
        PC pcIntelWater = new PC("ID1", "PC", "Karbo", "Politechnik", "Kabel usb i myszka", 1000.0, "Intel", true);
        assertEquals(1700.0, pcIntelWater.getActualPrice(pcIntelWater.getBasePrice()));

        PC pcAmdNoWater = new PC("ID2", "PC", "Smol", "Politechnik", "kabel zasilający", 1000.0, "AMD", false);
        assertEquals(1000.0, pcAmdNoWater.getActualPrice(pcAmdNoWater.getBasePrice()));

        Laptop laptopIntel = new Laptop("ID3", "Laptop", "Bigga", "USA-comp", "Torba", 1000.0, "Intel");
        assertEquals(1500.0, laptopIntel.getActualPrice(laptopIntel.getBasePrice()));

        Console consoleXbox = new Console("ID4", "Console", "thin", "Rat", "Stacja do dyskietek", 1000.0, Console.ConsoleType.Xbox);
        assertEquals(2000.0, consoleXbox.getActualPrice(consoleXbox.getBasePrice()));
        
        Console consoleNintendo = new Console("ID5", "Console", "big", "Boy", "Stacja do płyt CD", 1000.0, Console.ConsoleType.Nintendo);
        assertEquals(900.0, consoleNintendo.getActualPrice(consoleNintendo.getBasePrice()));
    }
}