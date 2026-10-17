package pobi.infrastructure.jpa.adapter;

import pobi.infrastructure.jpa.entity.ServiceOfferingEnt;
import pobi.infrastructure.jpa.mapper.ServiceOfferingMapper;
import pobi.infrastructure.jpa.repository.JpaServiceOfferingRepository;
import pobi.model.ServiceOffering;
import pobi.repositories.ServiceOfferingRepository;

import java.util.List;
import java.util.Objects;

public class JpaServiceOfferingRepositoryAdapter implements ServiceOfferingRepository {
    private final JpaServiceOfferingRepository repository;

    public JpaServiceOfferingRepositoryAdapter(JpaServiceOfferingRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public ServiceOffering add(ServiceOffering offering) {
        if (offering == null) {
            throw new IllegalArgumentException("Pozycja katalogu nie może być null");
        }

        ServiceOfferingEnt entity = ServiceOfferingMapper.toEntity(offering);
        ServiceOfferingEnt saved = repository.add(entity);

        return ServiceOfferingMapper.toDomain(saved);
    }

    @Override
    public ServiceOffering findById(long id) {
        return ServiceOfferingMapper.toDomain(repository.findById(id));
    }

    @Override
    public List<ServiceOffering> findAll() {
        return repository.findAll()
            .stream()
            .map(ServiceOfferingMapper::toDomain)
            .toList();
    }
}