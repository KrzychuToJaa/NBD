package pobi.repositories;

import pobi.model.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    private List<Client> clients;

    public ClientRepository() {
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
        if (clients.isEmpty()) {
            throw new IllegalArgumentException("Brak zarejestrowanych klientów");
        }
        return clients;
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

    /**
     * Zapisuje dane klientów do pliku
     * @param filename nazwa pliku
     */
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Client client : clients) {
                writer.write(client.getFirstName() + "," + client.getLastName() + "," +
                             client.getPhoneNumber() + "," + client.getCity() + "," +
                             client.getStreet() + "," + client.getNumber() + "," +
                             (client.isArchive() ? "1" : "0"));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wczytuje dane klientów z pliku
     * @param filename nazwa pliku
     */
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            this.clients.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    Client newClient = new Client(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                    if ("1".equals(parts[6])) {
                        newClient.setArchive(true);
                    }
                    add(newClient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}