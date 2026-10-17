package pobi.infrastructure.jpa.repository;

import jakarta.persistence.EntityManager;
import pobi.infrastructure.jpa.entity.EmployeeEnt;

import java.util.List;
import java.util.Objects;

public class JpaEmployeeRepository {
    private final EntityManager entityManager;

    public JpaEmployeeRepository(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager);
    }

    public EmployeeEnt add(EmployeeEnt employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Pracownik nie może być null");
        }

        if (employee.getId() != 0) {
            throw new IllegalArgumentException("Nowy pracownik nie może mieć nadanego ID");
        }

        entityManager.persist(employee);
        return employee;
    }

    public EmployeeEnt findById(long id) {
        EmployeeEnt employee = entityManager.find(EmployeeEnt.class, id);

        if (employee == null) {
            throw new IllegalArgumentException("Pracownik o ID " + id + " nie istnieje");
        }

        return employee;
    }

    public List<EmployeeEnt> findAll() {
        return entityManager.createQuery("select e from EmployeeEnt e order by e.id", EmployeeEnt.class)
            .getResultList();
    }
}