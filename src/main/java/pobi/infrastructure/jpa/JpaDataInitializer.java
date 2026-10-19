package pobi.infrastructure.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pobi.infrastructure.jpa.entity.*;
import pobi.infrastructure.jpa.mapper.ClientMapper;
import pobi.infrastructure.jpa.mapper.DeviceMapper;
import pobi.infrastructure.jpa.mapper.EmployeeMapper;
import pobi.infrastructure.jpa.mapper.ServiceOfferingMapper;
import pobi.model.Service;

public final class JpaDataInitializer {
    private JpaDataInitializer() {
    }

    public static void initialize(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Blokada PostgreSQL zapobiega podwójnej inicjalizacji przy równoczesnym starcie
            em.createNativeQuery("select 1 from pg_advisory_xact_lock(723601019)").getSingleResult();

            if (hasAnyData(em)) {
                tx.commit();
                return;
            }

            ClientEnt client1 = new ClientEnt(0, "Maciej", "Mamrot", "123456789", "Łęczno", "Łysa", "12", false);
            ClientEnt client2 = new ClientEnt(0, "Michał", "Woźniak", "987654321", "Ręczno", "Piotrkowska", "3", false);
            PCEnt pc = new PCEnt(0, "OptiPlex", "Dell", "Mysz i klawiatura", "Intel", true);
            LaptopEnt laptop = new LaptopEnt(0, "ThinkPad", "Lenovo", "Zasilacz", "Intel");
            EmployeeEnt employee1 = new EmployeeEnt(0, "Krzysztof", "Kata");
            EmployeeEnt employee2 = new EmployeeEnt(0, "Piotr", "Matuszczyk");
            ServiceOfferingEnt disk = new ServiceOfferingEnt(0, 200.0, "Wymiana dysku");
            ServiceOfferingEnt update = new ServiceOfferingEnt(0, 100.0, "Aktualizacja systemu");
            ServiceOfferingEnt clean = new ServiceOfferingEnt(0, 150.0, "Czyszczenie sprzętu");

            em.persist(client1);
            em.persist(client2);
            em.persist(pc);
            em.persist(laptop);
            em.persist(employee1);
            em.persist(employee2);
            em.persist(disk);
            em.persist(update);
            em.persist(clean);

            persistService(em, client1, employee1, disk, pc, "Wymiana uszkodzonego dysku");
            persistService(em, client1, employee1, update, pc, "Aktualizacja systemu po wymianie dysku");
            persistService(em, client2, employee2, clean, laptop, "Czyszczenie");
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            em.clear();
            throw e;
        }
    }

    private static boolean hasAnyData(EntityManager em) {
        for (String entity : new String[]{"ClientEnt", "DeviceEnt", "EmployeeEnt", "ServiceOfferingEnt", "ServiceEnt"}) {
            if (em.createQuery("select count(e) from " + entity + " e", Long.class).getSingleResult() > 0) {
                return true;
            }
        }
        return false;
    }

    private static void persistService(EntityManager em, ClientEnt client, EmployeeEnt employee, ServiceOfferingEnt offering, DeviceEnt device, String description) {
        Service service = new Service(0, ClientMapper.toDomainWithoutServices(client), EmployeeMapper.toDomainWithoutServices(employee), ServiceOfferingMapper.toDomain(offering), DeviceMapper.toDomain(device), description);
        ServiceEnt entity = new ServiceEnt(0, client, employee, offering, device, service.getBasePrice(), service.getFinalPrice(), description, false);
        em.persist(entity);
        client.addService(entity);
        employee.addService(entity);
    }
}