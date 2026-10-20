package pobi.infrastructure.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pobi.infrastructure.jpa.entity.*;
import pobi.model.Console;

/**
 * Klasa inicjalizująca bazę danych predefiniowanym zestawem danych testowych.
 */
public final class TestDataInitializer {

    public static long CLIENT_1_ID;
    public static long CLIENT_2_ID;
    public static long PC_ID;
    public static long LAPTOP_ID;
    public static long CONSOLE_ID;
    public static long EMPLOYEE_1_ID;
    public static long EMPLOYEE_2_ID;
    public static long OFFERING_DISK_ID;
    public static long OFFERING_CLEAN_ID;
    public static long ACTIVE_SERVICE_ID;
    public static long FINISHED_SERVICE_ID;

    private TestDataInitializer() {
    }

    public static void initializeTestData(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.createQuery("delete from ServiceEnt").executeUpdate();
            em.createQuery("delete from ClientEnt").executeUpdate();
            em.createQuery("delete from DeviceEnt").executeUpdate();
            em.createQuery("delete from EmployeeEnt").executeUpdate();
            em.createQuery("delete from ServiceOfferingEnt").executeUpdate();

            ClientEnt client1 = new ClientEnt(0, "Jan", "Kowalski", "111222333", "Warszawa", "Marszałkowska", "10", false);
            ClientEnt client2 = new ClientEnt(0, "Anna", "Nowak", "444555666", "Kraków", "Floriańska", "5", false);
            em.persist(client1);
            em.persist(client2);

            PCEnt pc = new PCEnt(0, "OptiPlex", "Dell", "Mysz i klawiatura", "Intel", true);
            LaptopEnt laptop = new LaptopEnt(0, "ThinkPad", "Lenovo", "Zasilacz", "AMD");
            ConsoleEnt console = new ConsoleEnt(0, "PlayStation 5", "Sony", "Pad", Console.ConsoleType.PlayStation);
            em.persist(pc);
            em.persist(laptop);
            em.persist(console);

            // PC z procesorem Intel i chłodzeniem wodnym: 200 * 1.5 + 200 = 500.0
            EmployeeEnt employee1 = new EmployeeEnt(0, "Janusz", "Serwisant");
            EmployeeEnt employee2 = new EmployeeEnt(0, "Piotr", "Informatyk");
            em.persist(employee1);
            em.persist(employee2);

            ServiceOfferingEnt disk = new ServiceOfferingEnt(0, 200.0, "Wymiana dysku");
            ServiceOfferingEnt clean = new ServiceOfferingEnt(0, 100.0, "Czyszczenie");
            em.persist(disk);
            em.persist(clean);

            // PC z procesorem Intel i chłodzeniem wodnym: 200 * 1.5 + 200 = 500.0
            ServiceEnt activeService = new ServiceEnt(0, client1, employee1, disk, pc, 200.0, 500.0, "Wymiana dysku SSD", false);
            // Laptop AMD: 100.0
            ServiceEnt finishedService = new ServiceEnt(0, client2, employee2, clean, laptop, 100.0, 100.0, "Czyszczenie chłodzenia", true);
            em.persist(activeService);
            em.persist(finishedService);

            client1.addService(activeService);
            client2.addService(finishedService);
            employee1.addService(activeService);
            employee2.addService(finishedService);

            tx.commit();

            CLIENT_1_ID = client1.getClientId();
            CLIENT_2_ID = client2.getClientId();
            PC_ID = pc.getId();
            LAPTOP_ID = laptop.getId();
            CONSOLE_ID = console.getId();
            EMPLOYEE_1_ID = employee1.getId();
            EMPLOYEE_2_ID = employee2.getId();
            OFFERING_DISK_ID = disk.getId();
            OFFERING_CLEAN_ID = clean.getId();
            ACTIVE_SERVICE_ID = activeService.getId();
            FINISHED_SERVICE_ID = finishedService.getId();

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }
}
