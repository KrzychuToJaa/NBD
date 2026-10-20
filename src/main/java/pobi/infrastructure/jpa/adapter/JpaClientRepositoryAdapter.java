package pobi.infrastructure.jpa.adapter;

import pobi.infrastructure.jpa.entity.ClientEnt;
import pobi.infrastructure.jpa.mapper.ClientMapper;
import pobi.infrastructure.jpa.repository.JpaClientRepository;
import pobi.model.Client;
import pobi.repositories.ClientRepository;

import java.util.List;
import java.util.Objects;

public class JpaClientRepositoryAdapter implements ClientRepository {
    private final JpaClientRepository repository;

    public JpaClientRepositoryAdapter(JpaClientRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Client add(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Klient nie może być null");
        }

        if (repository.phoneNumberExists(client.getPhoneNumber(), 0)) {
            throw new IllegalArgumentException("Klient o podanym numerze telefonu już istnieje" );
        }

        ClientEnt entity = ClientMapper.toEntity(client);
        ClientEnt saved = repository.add(entity);

        return ClientMapper.toDomain(saved);
    }

    @Override
    public Client findById(long clientId) {
        ClientEnt entity = repository.findById(clientId);
        return ClientMapper.toDomain(entity);
    }

    @Override
    public Client findByPhoneNumber(String phoneNumber) {
        ClientEnt entity = repository.findByPhoneNumber(phoneNumber);
        return ClientMapper.toDomain(entity);
    }

    @Override
    public List<Client> findAll() {
        return ClientMapper.toDomainList(repository.findAll());
    }

    @Override
    public void changePhoneNumber(long clientId, String phoneNumber) {
        Client client = findById(clientId);

        client.changePhoneNumber(phoneNumber);

        if (repository.phoneNumberExists(phoneNumber, clientId)) {
            throw new IllegalArgumentException("Klient o podanym numerze telefonu już istnieje");
        }

        repository.changePhoneNumber(clientId, client.getPhoneNumber());
    }

    @Override
    public void archive(long clientId) {
        repository.archive(clientId);
    }

    @Override
    public int size() {
        return Math.toIntExact(repository.count());
    }
}