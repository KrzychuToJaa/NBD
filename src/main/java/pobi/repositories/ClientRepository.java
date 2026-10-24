package pobi.repositories;

import pobi.model.Client;
import java.util.List;

public interface ClientRepository {
    Client add(Client client);
    Client findById(long clientId);
    Client findByPhoneNumber(String phoneNumber);
    List<Client> findAll();
    void changePhoneNumber(long clientId, String phoneNumber);
    void archive(long clientId);
    int size();
}
