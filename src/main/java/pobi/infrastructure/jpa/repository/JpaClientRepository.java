package pobi.infrastructure.jpa.repository;

import jakarta.persistence.EntityManager;
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
            throw new IllegalArgumentException(
                "Nowy klient nie może mieć nadanego ID"
            );
        }

        entityManager.persist(client);
        return client;
    }

    public ClientEnt findById(long clientId) {
        ClientEnt client = entityManager.find(ClientEnt.class, clientId);

        if (client == null) {
            throw new IllegalArgumentException(
                "Klient o ID " + clientId + " nie istnieje"
            );
        }

        return client;
    }

    public ClientEnt findByPhoneNumber(String phoneNumber) {
        return entityManager.createQuery("select c from ClientEnt c where c.phoneNumber = :phone",ClientEnt.class)
            .setParameter("phone", phoneNumber)
            .getResultStream()
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Klient o podanym numerze telefonu nie istnieje"));
    }

    public List<ClientEnt> findAll() {
        return entityManager.createQuery(
                "select c from ClientEnt c order by c.clientId",
                ClientEnt.class
            )
            .getResultList();
    }

    public long count() {
        return entityManager.createQuery(
                "select count(c) from ClientEnt c",
                Long.class
            )
            .getSingleResult();
    }

    public boolean phoneNumberExists (String phoneNumber, long excludedClientId) {

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
        ClientEnt entity = findById(clientId);
        entity.changePhoneNumber(phoneNumber);
    }

    public void archive(long clientId) {
        ClientEnt entity = findById(clientId);
        entity.archive();
    }
}