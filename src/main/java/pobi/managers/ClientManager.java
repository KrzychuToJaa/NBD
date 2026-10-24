package pobi.managers;

import pobi.model.Client;
import pobi.repositories.ClientRepository;
import java.util.List;
import java.util.Objects;

public class ClientManager {
    private final ClientRepository clientRepository;
    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = Objects.requireNonNull(clientRepository);
    }
    public Client registerClient(String firstName, String lastName, String phoneNumber, String city, String street, String number) {
        return clientRepository.add(new Client(0, firstName, lastName, phoneNumber, city, street, number));
    }
    public Client getClient(long clientId) { return clientRepository.findById(clientId); }
    public Client getClientByPhoneNumber(String phoneNumber) { return clientRepository.findByPhoneNumber(phoneNumber); }
    public void changePhoneNumber(long clientId, String phoneNumber) {
        clientRepository.changePhoneNumber(clientId, phoneNumber);
    }
    public void archiveClient(long clientId) { clientRepository.archive(clientId); }
    public List<Client> findAllClients() { return clientRepository.findAll(); }
    public ClientRepository getClientRepository() { return clientRepository; }
}
