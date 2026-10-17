package pobi.infrastructure.jpa.repository;

import jakarta.persistence.EntityManager;
import pobi.infrastructure.jpa.entity.DeviceEnt;

import java.util.List;
import java.util.Objects;

public class JpaDeviceRepository {
    private final EntityManager entityManager;

    public JpaDeviceRepository(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager);
    }

    public DeviceEnt add(DeviceEnt device) {
        if (device == null) {
            throw new IllegalArgumentException("Urządzenie nie może być null");
        }

        if (device.getId() != 0) {
            throw new IllegalArgumentException("Nowe urządzenie nie może mieć nadanego ID");
        }

        entityManager.persist(device);
        return device;
    }

    public DeviceEnt findById(long id) {
        DeviceEnt device = entityManager.find(DeviceEnt.class, id);

        if (device == null) {
            throw new IllegalArgumentException("Urządzenie o ID " + id + " nie istnieje");
        }

        return device;
    }

    public List<DeviceEnt> findAll() {
        return entityManager.createQuery("select d from DeviceEnt d order by d.id", DeviceEnt.class)
            .getResultList();
    }

    public void remove(long id) {
        DeviceEnt device = findById(id);

        long count = entityManager.createQuery("select count(s) from ServiceEnt s where s.device.id = :deviceId", Long.class)
            .setParameter("deviceId", id)
            .getSingleResult();

        if (count > 0) {
            throw new IllegalStateException("Nie można usunąć urządzenia powiązanego ze zleceniem");
        }

        entityManager.remove(device);
    }

    public long count() {
        return entityManager.createQuery("select count(d) from DeviceEnt d", Long.class)
            .getSingleResult();
    }
}