package pobi.repositories;

import pobi.model.Client;
import java.util.List;

/** Brak wyniku wyszukiwania: IllegalArgumentException. ID 0 przy dodaniu nadaje repozytorium. */
public interface ClientRepository {
    Client add(Client client);
    Client findById(long clientId);
    Client findByPhoneNumber(String phoneNumber);
    List<Client> findAll();
    void changePhoneNumber(long clientId, String phoneNumber);
    void archive(long clientId);
    int size();
}
