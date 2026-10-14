package memory;

import pobi.model.Client;


import java.util.ArrayList;
import java.util.List;
import pobi.repositories.ClientRepository;

public class MemoryClientRepository 
    implements pobi.repositories.ClientRepository {
    private List<Client> clients;

    public MemoryClientRepository() {
        this.clients = new ArrayList<>();
    }

    /**
     * Dodanie klienta do repozytorium
     * @param client klient do dodania
     */
    public void add(Client client) {
        if (client == null) return;
        for (Client c : clients) {
            if (c.getPhoneNumber().equals(client.getPhoneNumber())) {
                throw new IllegalArgumentException("Klient o podanym numerze telefonu już istnieje");
            }
        }
        clients.add(client);
    }

    /**
     * Usunięcie klienta z repozytorium
     * @param client klient do usunięcia
     */
    public void remove(Client client) {
        if (client != null) {
            clients.remove(client);
        }
    }

    /**
     * Zwraca liczbę klientów w repozytorium
     * @return liczba klientów
     */
    public int size() {
        return clients.size();
    }

    /**
     * Zwraca listę wszystkich klientów
     * @return lista klientów
     */
    public List<Client> findAll() {
        return List.copyOf(clients);
    }

    /**
     * Znalezienie klienta po numerze telefonu, a następnie zwrócenie go
     * @param phoneNumber numer telefonu klienta
     * @return Klient o podanym numerze telefonu
     */
    public Client findByPhoneNumber(String phoneNumber) {
        for (Client client : clients) {
            if (client.getPhoneNumber().equals(phoneNumber)) {
                return client;
            }
        }
        throw new IllegalArgumentException("Klient o podanym numerze telefonu nie został znaleziony");
    }

}
