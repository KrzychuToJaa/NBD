package pobi.infrastructure.jpa.adapter;

import pobi.infrastructure.jpa.entity.ServiceEnt;
import pobi.infrastructure.jpa.mapper.ServiceMapper;
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
        if (repository.findById(service.getId()) != null) {
            throw new IllegalArgumentException(
                    "Zlecenie o podanym ID już istnieje"
            );
        }
        ServiceEnt serviceEnt = ServiceMapper.toEntity(service);
        ServiceEnt saved = repository.add(serviceEnt);

        return ServiceMapper.toDomain(saved);
    }

    @Override
    public Service findById(long id) {
        ServiceEnt service =  repository.findById(id);
        return ServiceMapper.toDomain(service);
    }

    @Override
    public List<Service> findAll() {
        return repository.findAll()
                .stream()
                .map(ServiceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Service> findByEmployeeId(long employeeId) {
        return repository.findByEmployeeId(employeeId)
                .stream()
                .map(ServiceMapper::toDomain)
                .toList();
    }

    @Override
    public long countActiveByEmployeeId(long employeeId) {
        return repository.countActiveByEmployeeId(employeeId);
    }

    @Override
    public void closeService(long id) {
        repository.closeService(id);
    }

    @Override
    public void changeDescription(long id, String description) {
        repository.changeDescription(id, description);
    }

    @Override
    public List<Service> findByClientId(long clientId) {
        return repository.findByClientId(clientId)
                .stream()
                .map(ServiceMapper::toDomain)
                .toList();
    }

    @Override
    public int size() {
        return repository.count();
    }
}
