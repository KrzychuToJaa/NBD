package pobi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeviceTest {

    @Test
    public void testDeviceConstructors() {
        PC pc = new PC(1, "OptiPlex", "Dell", "Mysz i klawiatura", "Intel", true);
        assertEquals(1, pc.getId());
        assertEquals("OptiPlex", pc.getModel());
        assertEquals("Dell", pc.getBrand());
        assertEquals("Mysz i klawiatura", pc.getAccessories());
        assertEquals("Intel", pc.getCpuManufacturer());
        assertTrue(pc.hasWaterCooling());

        Laptop laptop = new Laptop(2, "ThinkPad", "Lenovo", "Zasilacz", "AMD");
        assertEquals(2, laptop.getId());
        assertEquals("AMD", laptop.getCpuManufacturer());

        Console console = new Console(3, "Switch", "Nintendo", "JoyCon", Console.ConsoleType.Nintendo);
        assertEquals(3, console.getId());
        assertEquals(Console.ConsoleType.Nintendo, console.getConsoleType());
        assertEquals("Nintendo", console.getConsoleTypeName());
    }

    @Test
    public void testDeviceConstructorExceptions() {
        assertThrows(IllegalArgumentException.class, () -> new Laptop(1, "", "Lenovo", "Zasilacz", "AMD"));
        assertThrows(IllegalArgumentException.class, () -> new Laptop(1, "ThinkPad", "", "Zasilacz", "AMD"));
        assertThrows(IllegalArgumentException.class, () -> new Laptop(1, "ThinkPad", "Lenovo", "", "AMD"));
        assertThrows(IllegalArgumentException.class, () -> new Laptop(1, "ThinkPad", "Lenovo", "Zasilacz", ""));
        assertThrows(IllegalArgumentException.class, () -> new Laptop(-1, "ThinkPad", "Lenovo", "Zasilacz", "AMD"));

        assertThrows(IllegalArgumentException.class, () -> new Console(1, "PS5", "Sony", "Pad", null));
    }

    @Test
    public void testPCActualPrice() {
        // Intel (1.5x) + chłodzenie wodne (+200): 1000 * 1.5 + 200 = 1700
        PC pcIntelWater = new PC(1, "Custom", "Corsair", "Kabel", "Intel", true);
        assertEquals(1700.0, pcIntelWater.getActualPrice(1000.0));

        // AMD (bez mnożnika) + bez chłodzenia wodnego: 1000
        PC pcAmdNoWater = new PC(2, "Custom", "NZXT", "Kabel", "AMD", false);
        assertEquals(1000.0, pcAmdNoWater.getActualPrice(1000.0));
    }

    @Test
    public void testLaptopActualPrice() {
        // Intel: 1.5x
        Laptop laptopIntel = new Laptop(1, "X1", "Lenovo", "Zasilacz", "Intel");
        assertEquals(1500.0, laptopIntel.getActualPrice(1000.0));

        // Inny producent procesora: bez zmian
        Laptop laptopAmd = new Laptop(2, "Zephyrus", "Asus", "Zasilacz", "AMD");
        assertEquals(1000.0, laptopAmd.getActualPrice(1000.0));
    }

    @Test
    public void testConsoleActualPrice() {
        Console ps = new Console(1, "PS5", "Sony", "Pad", Console.ConsoleType.PlayStation);
        assertEquals(1500.0, ps.getActualPrice(1000.0)); // 1.5x

        Console xbox = new Console(2, "Series X", "Microsoft", "Pad", Console.ConsoleType.Xbox);
        assertEquals(2000.0, xbox.getActualPrice(1000.0)); // 2.0x

        Console nintendo = new Console(3, "Switch", "Nintendo", "JoyCon", Console.ConsoleType.Nintendo);
        assertEquals(900.0, nintendo.getActualPrice(1000.0)); // 0.9x
    }

    @Test
    public void testDeviceInfoAndSpecialInfo() {
        PC pc = new PC(1, "OptiPlex", "Dell", "Mysz", "Intel", true);
        assertTrue(pc.getInfo().contains("Dell"));
        assertEquals("Intel,tak", pc.getSpecialInfo());

        Console console = new Console(2, "Switch", "Nintendo", "JoyCon", Console.ConsoleType.Nintendo);
        assertEquals("Nintendo", console.getSpecialInfo());
    }
}
