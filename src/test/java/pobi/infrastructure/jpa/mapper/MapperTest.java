package pobi.infrastructure.jpa.mapper;

import org.junit.jupiter.api.Test;
import pobi.infrastructure.jpa.entity.*;
import pobi.model.*;

import static org.junit.jupiter.api.Assertions.*;

public class MapperTest {

    @Test
    public void testDeviceMapperPolymorphism() {
        PC pc = new PC(1, "OptiPlex", "Dell", "Mysz", "Intel", true);
        DeviceEnt pcEnt = DeviceMapper.toEntity(pc);
        assertInstanceOf(PCEnt.class, pcEnt);
        assertEquals("Dell", pcEnt.getBrand());
        assertEquals("Intel", ((PCEnt) pcEnt).getCpuManufacturer());
        assertTrue(((PCEnt) pcEnt).isWaterCooling());

        Device restoredPc = DeviceMapper.toDomain(pcEnt);
        assertInstanceOf(PC.class, restoredPc);
        assertEquals("OptiPlex", restoredPc.getModel());
        assertTrue(((PC) restoredPc).hasWaterCooling());

        Laptop laptop = new Laptop(2, "ThinkPad", "Lenovo", "Zasilacz", "AMD");
        DeviceEnt laptopEnt = DeviceMapper.toEntity(laptop);
        assertInstanceOf(LaptopEnt.class, laptopEnt);
        assertEquals("AMD", ((LaptopEnt) laptopEnt).getCpuManufacturer());

        Device restoredLaptop = DeviceMapper.toDomain(laptopEnt);
        assertInstanceOf(Laptop.class, restoredLaptop);
        assertEquals("ThinkPad", restoredLaptop.getModel());

        Console console = new Console(3, "Switch", "Nintendo", "JoyCon", Console.ConsoleType.Nintendo);
        DeviceEnt consoleEnt = DeviceMapper.toEntity(console);
        assertInstanceOf(ConsoleEnt.class, consoleEnt);
        assertEquals(Console.ConsoleType.Nintendo, ((ConsoleEnt) consoleEnt).getConsoleType());

        Device restoredConsole = DeviceMapper.toDomain(consoleEnt);
        assertInstanceOf(Console.class, restoredConsole);
        assertEquals(Console.ConsoleType.Nintendo, ((Console) restoredConsole).getConsoleType());

        assertNull(DeviceMapper.toEntity(null));
        assertNull(DeviceMapper.toDomain(null));
    }

    @Test
    public void testClientMapper() {
        Client client = new Client(10, "Jan", "Kowalski", "123456789", "Warszawa", "Złota", "44", false);
        ClientEnt clientEnt = ClientMapper.toEntity(client);

        assertNotNull(clientEnt);
        assertEquals(10, clientEnt.getClientId());
        assertEquals("Jan", clientEnt.getFirstName());
        assertEquals("Kowalski", clientEnt.getLastName());
        assertEquals("123456789", clientEnt.getPhoneNumber());
        assertFalse(clientEnt.isArchive());

        Client restored = ClientMapper.toDomain(clientEnt);
        assertNotNull(restored);
        assertEquals(10, restored.getClientId());
        assertEquals("Jan", restored.getFirstName());
        assertEquals("Kowalski", restored.getLastName());
        assertEquals("123456789", restored.getPhoneNumber());

        assertNull(ClientMapper.toDomain(null));
    }

    @Test
    public void testEmployeeMapper() {
        Employee employee = new Employee(5, "Adam", "Nowak");
        EmployeeEnt entity = EmployeeMapper.toEntity(employee);

        assertEquals(5, entity.getId());
        assertEquals("Adam", entity.getFirstName());
        assertEquals("Nowak", entity.getLastName());

        Employee restored = EmployeeMapper.toDomain(entity);
        assertEquals(5, restored.getId());
        assertEquals("Adam", restored.getFirstName());
        assertEquals("Nowak", restored.getLastName());

        assertNull(EmployeeMapper.toEntity(null));
        assertNull(EmployeeMapper.toDomain(null));
    }

    @Test
    public void testServiceOfferingMapper() {
        ServiceOffering offering = new ServiceOffering(7, 250.0, "Wymiana matrycy");
        ServiceOfferingEnt entity = ServiceOfferingMapper.toEntity(offering);

        assertEquals(7, entity.getId());
        assertEquals(250.0, entity.getBasePrice());
        assertEquals("Wymiana matrycy", entity.getName());

        ServiceOffering restored = ServiceOfferingMapper.toDomain(entity);
        assertEquals(7, restored.getId());
        assertEquals(250.0, restored.getBasePrice());
        assertEquals("Wymiana matrycy", restored.getName());

        assertNull(ServiceOfferingMapper.toEntity(null));
        assertNull(ServiceOfferingMapper.toDomain(null));
    }

    @Test
    public void testServiceMapperAndPriceSnapshot() {
        Client client = new Client(1, "Jan", "Kowalski", "123456789", "Miasto", "Ulica", "1");
        Employee employee = new Employee(1, "Adam", "Nowak");
        ServiceOffering offering = new ServiceOffering(1, 100.0, "Czyszczenie");
        PC pc = new PC(1, "PC", "Dell", "Brak", "Intel", true);

        Service service = new Service(100, client, employee, offering, pc, "Czyszczenie i wymiana pasty");
        assertEquals(100.0, service.getBasePrice());
        assertEquals(350.0, service.getFinalPrice());

        ServiceEnt entity = ServiceMapper.toEntity(service);
        assertNotNull(entity);
        assertEquals(100, entity.getId());
        assertEquals(100.0, entity.getBasePrice());
        assertEquals(350.0, entity.getFinalPrice());
        assertEquals("Czyszczenie i wymiana pasty", entity.getDescription());
        assertFalse(entity.isArchive());

        Service restored = ServiceMapper.toDomain(entity);
        assertNotNull(restored);
        assertEquals(100, restored.getId());
        assertEquals(100.0, restored.getBasePrice());
        assertEquals(350.0, restored.getFinalPrice());
        assertEquals("Czyszczenie i wymiana pasty", restored.getFaultDescription());
        assertTrue(restored.getClient().getServices().stream().anyMatch(s -> s.getId() == 100));
        assertTrue(restored.getEmployee().getServices().stream().anyMatch(s -> s.getId() == 100));

        assertNull(ServiceMapper.toEntity(null));
        assertNull(ServiceMapper.toDomain(null));
    }
}
