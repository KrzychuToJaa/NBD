package pobi.managers;

import pobi.model.Client;
import pobi.repositories.ClientRepository;
import java.util.List;

public class ClientManager {
    private ClientRepository clientRepository;

    public ClientManager() {
        this.clientRepository = new ClientRepository();
    }

    /**
     * Rejestracja nowego klienta, wraz z podanymi przez użytkownika danymi
     * @param firstName imię klienta
     * @param lastName nazwisko klienta
     * @param phoneNumber numer telefonu klienta
     * @param city miasto klienta
     * @param street ulica klienta
     * @param number numer domu klienta
     */
    public void registerClient(String firstName, String lastName, String phoneNumber, String city, String street, String number) {
        Client newClient = new Client(firstName, lastName, phoneNumber, city, street, number);
        clientRepository.add(newClient);
    }
    
    /**
     * Znalezienie klienta po numerze telefonu, a następnie zwrócenie go
     * @param phoneNumber numer telefonu klienta
     * @return Klient o podanym numerze telefonu lub null, jeśli nie istnieje lub jest zarchiwizowany
     */
    public Client getClient(String phoneNumber) {
        Client client = clientRepository.findByPhoneNumber(phoneNumber);
        if (client == null || client.isArchive()) {
            return null;
        }
        return client;    
    }
    
    /**
     * Usunięcie klienta o podanym numerze telefonu z repozytorium
     * @param phoneNumber numer telefonu klienta
     */
    public void unregisterClient(String phoneNumber) {
        clientRepository.remove(clientRepository.findByPhoneNumber(phoneNumber));
    }
    
    /**
     * Zwraca informacje o wszystkich klientach
     * @return Wektor (Lista) referencji do klientów
     */
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }
   
    /**
     * Zapisuje dane klientów do pliku
     * @param filename nazwa pliku
     */
    public void saveToFile(String filename) {
        clientRepository.saveToFile(filename);
    }
   
    /**
     * Wczytuje dane klientów z pliku
     * @param filename nazwa pliku
     */
    public void loadFromFile(String filename) {
        clientRepository.loadFromFile(filename);
    }
   
    /**
     * Zwraca referencję do repozytorium klientów
     * @return Referencja do repozytorium klientów
     */
    public ClientRepository getClientRepository() {
        return clientRepository;
    }
}