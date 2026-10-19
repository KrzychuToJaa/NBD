package pobi.infrastructure.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pobi.infrastructure.jpa.entity.ServiceOfferingEnt;

import java.util.List;
import java.util.Objects;

public class JpaServiceOfferingRepository {
    private final EntityManager entityManager;

    public JpaServiceOfferingRepository(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager);
    }

    public ServiceOfferingEnt add(ServiceOfferingEnt offering) {
        if (offering == null) {
            throw new IllegalArgumentException("Pozycja katalogu nie może być null");
        }

        if (offering.getId() != 0) {
            throw new IllegalArgumentException("Nowa pozycja katalogu nie może mieć nadanego ID");
        }
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(offering);
            transaction.commit();
            return offering;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public ServiceOfferingEnt findById(long id) {
        ServiceOfferingEnt offering = entityManager.find(ServiceOfferingEnt.class, id);

        if (offering == null) {
            throw new IllegalArgumentException("Pozycja katalogu o ID " + id + " nie istnieje");
        }

        return offering;
    }

    public List<ServiceOfferingEnt> findAll() {
        return entityManager.createQuery("select o from ServiceOfferingEnt o order by o.id", ServiceOfferingEnt.class).getResultList();
    }
}