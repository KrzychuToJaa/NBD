package pobi.infrastructure.jpa.adapter;

import pobi.infrastructure.jpa.entity.EmployeeEnt;
import pobi.infrastructure.jpa.mapper.EmployeeMapper;
import pobi.infrastructure.jpa.repository.JpaEmployeeRepository;
import pobi.model.Employee;
import pobi.repositories.EmployeeRepository;

import java.util.List;
import java.util.Objects;

public class JpaEmployeeRepositoryAdapter implements EmployeeRepository {
    private final JpaEmployeeRepository repository;

    public JpaEmployeeRepositoryAdapter(JpaEmployeeRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Employee add(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Pracownik nie może być null");
        }

        EmployeeEnt entity = EmployeeMapper.toEntity(employee);
        EmployeeEnt saved = repository.add(entity);

        return EmployeeMapper.toDomain(saved);
    }

    @Override
    public Employee findById(long id) {
        return EmployeeMapper.toDomain(repository.findById(id));
    }

    @Override
    public List<Employee> findAll() {
        return EmployeeMapper.toDomainList(repository.findAll());
    }
}