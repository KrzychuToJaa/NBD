package memory;

import pobi.model.Client;
import pobi.repositories.ClientRepository;
import java.util.ArrayList;
import java.util.List;

public class MemoryClientRepository implements ClientRepository {
    private final List<Client> clients = new ArrayList<>();
    private long nextId = 1;

    @Override
    public synchronized Client add(Client client) {
        if (client == null) { throw new IllegalArgumentException("Klient nie może być null"); }
        requireUniquePhone(client.getPhoneNumber(), -1);
        long id = client.getClientId() == 0 ? nextId : client.getClientId();
        if (clients.stream().anyMatch(c -> c.getClientId() == id)) {
            throw new IllegalArgumentException("Klient o podanym ID już istnieje");
        }
        Client saved = client.getClientId() == 0
            ? new Client(id, client.getFirstName(), client.getLastName(), client.getPhoneNumber(), client.getCity(), client.getStreet(), client.getNumber(), client.isArchive()) : client;
        clients.add(saved);
        nextId = Math.max(nextId, id + 1);
        return saved;
    }

    @Override
    public Client findById(long clientId) {
        return clients.stream().filter(c -> c.getClientId() == clientId).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Klient o podanym ID nie został znaleziony"));
    }

    @Override
    public Client findByPhoneNumber(String phoneNumber) {
        return clients.stream().filter(c -> c.getPhoneNumber().equals(phoneNumber)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Klient o podanym numerze telefonu nie został znaleziony"));
    }

    @Override
    public synchronized void changePhoneNumber(long clientId, String phoneNumber) {
        Client client = findById(clientId);
        requireUniquePhone(phoneNumber, clientId);
        client.changePhoneNumber(phoneNumber);
    }

    private void requireUniquePhone(String phoneNumber, long exceptClientId) {
        Client.validatePhoneNumber(phoneNumber);
        if (clients.stream().anyMatch(c -> c.getClientId() != exceptClientId && c.getPhoneNumber().equals(phoneNumber))) {
            throw new IllegalArgumentException("Klient o podanym numerze telefonu już istnieje");
        }
    }

    @Override public void archive(long clientId) { findById(clientId).archive(); }
    @Override public List<Client> findAll() { return List.copyOf(clients); }
    @Override public int size() { return clients.size(); }
}
