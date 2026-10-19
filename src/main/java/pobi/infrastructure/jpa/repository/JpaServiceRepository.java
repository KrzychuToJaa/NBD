package pobi.infrastructure.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import pobi.infrastructure.jpa.entity.*;
import pobi.model.Employee;

import java.util.List;
import java.util.Objects;

public class JpaServiceRepository {
    private final EntityManager em;

    public JpaServiceRepository(EntityManager entityManager) {
        this.em = Objects.requireNonNull(entityManager);
    }

    public ServiceEnt add(ServiceEnt entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Zlecenie nie może być null");
        }
        if (entity.getId() != 0) {
            throw new IllegalArgumentException("Nowe zlecenie nie może mieć nadanego ID");
        }
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            ClientEnt client = em.find(ClientEnt.class, entity.getClient().getClientId());
            EmployeeEnt employee = em.find(EmployeeEnt.class, entity.getEmployee().getId(), LockModeType.PESSIMISTIC_WRITE);
            ServiceOfferingEnt offering = em.find(ServiceOfferingEnt.class, entity.getOffering().getId());
            DeviceEnt device = em.find(DeviceEnt.class, entity.getDevice().getId());

            if (client == null || employee == null || device == null || offering == null) {
                throw new IllegalArgumentException("Powiązany obiekt (klient/pracownik/oferta/urządzenie) nie istnieje w bazie");
            }
            if (!entity.isArchive() && countActiveByEmployeeId(employee.getId()) >= Employee.maxServices) {
                throw new IllegalStateException("Pracownik osiągnął limit aktywnych usług");
            }
            ServiceEnt service = new ServiceEnt(
                    0,
                    client,
                    employee,
                    offering,
                    device,
                    entity.getBasePrice(),
                    entity.getFinalPrice(),
                    entity.getDescription(),
                    entity.isArchive()
            );
            em.persist(service);
            client.addService(service);
            employee.addService(service);
            tx.commit();
            return service;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public ServiceEnt findById(long id) {
        return em.createQuery("select s from ServiceEnt s where s.id = :id", ServiceEnt.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Taka usługa nie istnieje."));
    }

    public List<ServiceEnt> findAll() {
        return em.createQuery("select s from ServiceEnt s order by s.id", ServiceEnt.class)
                .getResultList();
    }

    public int count() {
        return Math.toIntExact(em.createQuery("select count(s) from ServiceEnt s", Long.class)
                .getSingleResult());
    }

    public List<ServiceEnt> findByEmployeeId(long id) {
        return em.createQuery("select s from ServiceEnt s where s.employee.id = :id", ServiceEnt.class)
                .setParameter("id", id)
                .getResultList();
    }

    public long countActiveByEmployeeId(long id) {
        return em.createQuery("select count(s) from ServiceEnt s where s.employee.id = :id and s.archive = false", Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<ServiceEnt> findByClientId(long id) {
        return em.createQuery("select s from ServiceEnt s where s.client.clientId = :id", ServiceEnt.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void closeService(long id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ServiceEnt service = findById(id);
            service.finishService();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void changeDescription(long id, String description) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ServiceEnt service = findById(id);
            service.setDescription(description);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

}
