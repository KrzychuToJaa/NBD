package pobi.infrastructure.jpa.adapter;

import pobi.infrastructure.jpa.repository.JpaServiceRepository;
import pobi.model.Service;
import pobi.repositories.ServiceRepository;

import java.util.List;
import java.util.Objects;

public class JpaServiceRepositoryAdapter implements ServiceRepository {
    private final JpaServiceRepository repository;

    public JpaServiceRepositoryAdapter(JpaServiceRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Service add(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Serwis nie może być null");
        }
    }

    @Override
    public Service findById(long id) {
        return null;
    }

    @Override
    public List<Service> findAll() {
        return List.of();
    }

    @Override
    public List<Service> findByEmployeeId(long employeeId) {
        return List.of();
    }

    @Override
    public long countActiveByEmployeeId(long employeeId) {
        return 0;
    }

    @Override
    public void closeService(long id) {

    }

    @Override
    public void changeDescription(long id, String description) {

    }

    @Override
    public List<Service> findByClientId(long clientId) {
        return List.of();
    }

    @Override
    public int size() {
        return 0;
    }
}
