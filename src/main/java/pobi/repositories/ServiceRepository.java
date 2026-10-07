package pobi.repositories;

import pobi.model.Service;

import pobi.model.Client;
import pobi.model.Device;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class ServiceRepository {
    private List<Service> services;

    public ServiceRepository() {
        this.services = new ArrayList<>();
    }

    /**
     * Znajduje usługę po ID i zwraca ją
     * @param id ID usługi
     * @return referencja na usługę
     */
    public Service findByID(String id) {
        for (Service service : services) {
            if (service.getID().equals(id)) {
                return service;
            }
        }
        throw new IllegalArgumentException("Usługa o podanym ID nie została znaleziona");
    }

    /**
     * Dodanie usługi do repozytorium
     * @param service usługa do dodania
     */
    public void add(Service service) {
        if (service == null) return;
        for (Service s : services) {
            if (s.getID().equals(service.getID())) {
                throw new IllegalArgumentException("Usługa o podanym ID już istnieje");
            }
        }
        services.add(service);
    }

    /**
     * Usunięcie usługi z repozytorium
     * @param service usuługa do usunięcia
     */
    public void remove(Service service) {
        if (service != null) {
            services.remove(service);
        }
    }

    /**
     * Zwraca liczbę usług w repozytorium
     * @return liczba usług
     */
    public int size() {
        return services.size();
    }

    /**
     * Zwraca listę wszystkich usług
     * @return lista usług
     */
    public List<Service> findAll() {
        return services;
    }

    /**
     * Zapisuje dane usług do pliku
     * @param filename nazwa pliku
     */
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Service service : services) {
                writer.write(service.getID() + "," + service.getBasePrice() + "," +
                             (service.isArchive() ? "1" : "0") + "," + service.getFaultDescription() + "," +
                             service.getClient().getPhoneNumber() + "," + service.getDevice().getID());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Załaduje dane usług z pliku
     * @param filename nazwa pliku
     * @param clientRepo repozytorium klientów
     * @param deviceRepo repozytorium urządzeń
     */
    public void loadFromFile(String filename, ClientRepository clientRepo, DeviceRepository deviceRepo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            this.services.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String id = parts[0];
                    double basePrice = Double.parseDouble(parts[1]);
                    String archiveStr = parts[2];
                    String faultDescription = parts[3];
                    String clientPhone = parts[4];
                    String deviceId = parts[5];

                    Client client = clientRepo.findByPhoneNumber(clientPhone);
                    Device device = deviceRepo.findByID(deviceId);

                    if (client != null && device != null) {
                        Service newService = new Service(id, basePrice, client, device, faultDescription);
                        if (archiveStr.equals("1")) {
                            newService.setArchive(true);
                        }
                        add(newService);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}