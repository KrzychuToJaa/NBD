package pobi.infrastructure.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import pobi.infrastructure.jpa.entity.ClientEnt;

import java.util.List;
import java.util.Objects;

public class JpaClientRepository {
    private final EntityManager entityManager;

    public JpaClientRepository(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager);
    }

    public ClientEnt add(ClientEnt client) {
        if (client == null) {
            throw new IllegalArgumentException("Klient nie może być null");
        }

        if (client.getClientId() != 0) {
            throw new IllegalArgumentException("Nowy klient nie może mieć nadanego ID");
        }
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(client);
            transaction.commit();
            return client;
        } catch (RuntimeException ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public ClientEnt findById(long clientId) {
        ClientEnt client = entityManager.find(ClientEnt.class, clientId);

        if (client == null) {
            throw new IllegalArgumentException("Klient o ID " + clientId + " nie istnieje");
        }

        return client;
    }

    public ClientEnt findByPhoneNumber(String phoneNumber) {
        return entityManager.createQuery("select c from ClientEnt c where c.phoneNumber = :phone", ClientEnt.class)
                .setParameter("phone", phoneNumber)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Klient o podanym numerze telefonu nie istnieje"));
    }

    public List<ClientEnt> findAll() {
        return entityManager.createQuery("select c from ClientEnt c order by c.clientId", ClientEnt.class)
                .getResultList();
    }

    public long count() {
        return entityManager.createQuery("select count(c) from ClientEnt c", Long.class)
                .getSingleResult();
    }

    public boolean phoneNumberExists(String phoneNumber, long excludedClientId) {

        long count = entityManager.createQuery("select count(c) from ClientEnt c where c.phoneNumber = :phone and c.clientId <> :excludedId", Long.class)
                .setParameter("phone", phoneNumber)
                .setParameter("excludedId", excludedClientId)
                .getSingleResult();

        return count > 0;
    }

    public boolean hasActiveServices(long clientId) {
        long count = entityManager.createQuery("select count(s) from ServiceEnt s where s.client.clientId = :clientId and s.archive = false", Long.class)
                .setParameter("clientId", clientId)
                .getSingleResult();

        return count > 0;
    }

    public void changePhoneNumber(long clientId, String phoneNumber) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ClientEnt entity = findById(clientId);
            entity.changePhoneNumber(phoneNumber);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public void archive(long clientId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ClientEnt entity = entityManager.find(ClientEnt.class, clientId, LockModeType.PESSIMISTIC_WRITE);
            if (entity == null) {
                throw new IllegalArgumentException("Klient o ID " + clientId + " nie istnieje");
            }
            entityManager.refresh(entity, LockModeType.PESSIMISTIC_WRITE);
            if (hasActiveServices(clientId)) {
                throw new IllegalStateException("Klient ma aktywne zlecenia; najpierw je zakończ");
            }
            entity.archive();
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }
}