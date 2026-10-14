package pobi.repositories;

import pobi.model.Client;
import java.util.List;

public interface ClientRepository {
    void add(Client client);

    Client findByPhoneNumber(String phoneNumber);

    List<Client> findAll();

    void remove(Client client);

    int size();
}