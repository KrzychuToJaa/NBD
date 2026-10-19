package pobi.infrastructure.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import pobi.infrastructure.jpa.adapter.*;
import pobi.infrastructure.jpa.repository.*;
import pobi.model.*;
import pobi.repositories.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy integracyjne bazy danych i adapterów JPA weryfikujące operacje
 * na danych zainicjalizowanych przez TestDataInitializer.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private ClientRepository clientRepository;
    private DeviceRepository deviceRepository;
    private EmployeeRepository employeeRepository;
    private ServiceOfferingRepository offeringRepository;
    private ServiceRepository serviceRepository;
    private boolean dbAvailable = false;

    @BeforeAll
    public void setUpFactory() {
        try {
            emf = Persistence.createEntityManagerFactory("nbd");
            em = emf.createEntityManager();
            dbAvailable = true;
        } catch (Exception e) {
            System.out.println("PostgreSQL nie jest uruchomiony na porcie 5433 (uruchom: cd Docker && docker compose up -d). Testy bazy danych zostaną pominięte.");
            dbAvailable = false;
        }
    }

    @AfterAll
    public void tearDown() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @BeforeEach
    public void setUp() {
        Assumptions.assumeTrue(dbAvailable, "Baza danych PostgreSQL nie jest aktywna.");

        // Inicjalizacja bazy predefiniowanymi danymi testowymi
        TestDataInitializer.initializeTestData(em);
        em.clear();

        clientRepository = new JpaClientRepositoryAdapter(new JpaClientRepository(em));
        deviceRepository = new JpaDeviceRepositoryAdapter(new JpaDeviceRepository(em));
        employeeRepository = new JpaEmployeeRepositoryAdapter(new JpaEmployeeRepository(em));
        offeringRepository = new JpaServiceOfferingRepositoryAdapter(new JpaServiceOfferingRepository(em));
        serviceRepository = new JpaServiceRepositoryAdapter(new JpaServiceRepository(em));
    }

    @Test
    public void testClientsLoadedFromDatabase() {
        Client client1 = clientRepository.findById(TestDataInitializer.CLIENT_1_ID);
        assertNotNull(client1);
        assertEquals("Jan", client1.getFirstName());
        assertEquals("Kowalski", client1.getLastName());
        assertEquals("111222333", client1.getPhoneNumber());

        Client clientByPhone = clientRepository.findByPhoneNumber("444555666");
        assertNotNull(clientByPhone);
        assertEquals("Anna", clientByPhone.getFirstName());
        assertEquals(TestDataInitializer.CLIENT_2_ID, clientByPhone.getClientId());

        assertEquals(2, clientRepository.size());
    }

    @Test
    public void testPolymorphicDevicesLoadedFromDatabase() {
        // Weryfikacja polimorfizmu: PC
        Device pc = deviceRepository.findById(TestDataInitializer.PC_ID);
        assertInstanceOf(PC.class, pc);
        assertEquals("OptiPlex", pc.getModel());
        assertTrue(((PC) pc).hasWaterCooling());

        // Weryfikacja polimorfizmu: Laptop
        Device laptop = deviceRepository.findById(TestDataInitializer.LAPTOP_ID);
        assertInstanceOf(Laptop.class, laptop);
        assertEquals("ThinkPad", laptop.getModel());

        // Weryfikacja polimorfizmu: Console
        Device console = deviceRepository.findById(TestDataInitializer.CONSOLE_ID);
        assertInstanceOf(Console.class, console);
        assertEquals(Console.ConsoleType.PlayStation, ((Console) console).getConsoleType());

        assertEquals(3, deviceRepository.size());
    }

    @Test
    public void testEmployeesAndActiveServicesCount() {
        Employee employee1 = employeeRepository.findById(TestDataInitializer.EMPLOYEE_1_ID);
        assertEquals("Janusz", employee1.getFirstName());
        // Pracownik 1 ma 1 aktywną usługę zainicjalizowaną w bazie
        assertEquals(1, serviceRepository.countActiveByEmployeeId(employee1.getId()));

        Employee employee2 = employeeRepository.findById(TestDataInitializer.EMPLOYEE_2_ID);
        assertEquals("Piotr", employee2.getFirstName());
        // Pracownik 2 ma 0 aktywnych usług (jego usługa jest zakończona)
        assertEquals(0, serviceRepository.countActiveByEmployeeId(employee2.getId()));
    }

    @Test
    public void testServicesAndHistory() {
        Service activeService = serviceRepository.findById(TestDataInitializer.ACTIVE_SERVICE_ID);
        assertFalse(activeService.isArchive());
        assertEquals(200.0, activeService.getBasePrice());
        assertEquals(500.0, activeService.getFinalPrice());
        assertEquals("Jan", activeService.getClient().getFirstName());

        Service finishedService = serviceRepository.findById(TestDataInitializer.FINISHED_SERVICE_ID);
        assertTrue(finishedService.isArchive());

        List<Service> client1Services = serviceRepository.findByClientId(TestDataInitializer.CLIENT_1_ID);
        assertEquals(1, client1Services.size());
        assertEquals("Wymiana dysku SSD", client1Services.get(0).getFaultDescription());
    }

    @Test
    public void testBusinessRuleWithDatabaseArchiving() {
        // Klient 1 ma aktywne zlecenie, więc próba archiwizacji rzuci błąd
        assertThrows(IllegalStateException.class, () -> clientRepository.archive(TestDataInitializer.CLIENT_1_ID));

        // Zamykamy zlecenie klienta 1
        serviceRepository.closeService(TestDataInitializer.ACTIVE_SERVICE_ID);

        // Teraz archiwizacja klienta 1 zakończy się sukcesem
        assertDoesNotThrow(() -> clientRepository.archive(TestDataInitializer.CLIENT_1_ID));

        Client archived = clientRepository.findById(TestDataInitializer.CLIENT_1_ID);
        assertTrue(archived.isArchive());
    }
}
