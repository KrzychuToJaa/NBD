package pobi.infrastructure.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import pobi.infrastructure.jpa.entity.*;
import pobi.model.ServiceOffering;

import java.util.List;
import java.util.Objects;

public class JpaServiceRepository {
    private final EntityManager em;

    public JpaServiceRepository(EntityManager entityManager) {
        this.em = Objects.requireNonNull(entityManager);
    }

    ServiceEnt add(ServiceEnt entity) {
        if(entity == null){
            throw new IllegalArgumentException("Zlecenie nie może być null");
        }
        if (entity.getId() != 0){
            throw new IllegalArgumentException("Nowe zlecenie nie może mieć nadanego ID");
        }
        ClientEnt client = em.find(ClientEnt.class, entity.getClient().getClientId());
        EmployeeEnt employee = em.find(EmployeeEnt.class, entity.getEmployee().getId());
        ServiceOfferingEnt offering = em.find(ServiceOfferingEnt.class, entity.getOffering().getId());
        DeviceEnt device = em.find(DeviceEnt.class, entity.getDevice().getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        if (client == null ||  employee == null || device==null || offering==null){
            throw new IllegalArgumentException("Powiązany obiekt (klient/pracownik/oferta/urządzenie) nie istnieje w bazie");
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
        return service;
    }

    ServiceEnt findById(long id) {
        return null;
    }
    List<ServiceEnt> findAll() {
        return null;
    }
    long count() {
        return 0;
    }
    List<ServiceEnt> findByEmployeeId(long id) {
        return null;
    }
    long countActiveByEmployeeId(long id) {
        return 0;
    }
    List<ServiceEnt> findByClientId(long id) {
        return null;
    }
    void closeService() {
    }
    void changeDescription(long id, String description) {
    }


}
