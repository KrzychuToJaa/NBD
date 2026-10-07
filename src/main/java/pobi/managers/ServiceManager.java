package pobi.managers;

import pobi.model.Service;
import pobi.model.Client;
import pobi.model.Device;

import pobi.repositories.ClientRepository;
import pobi.repositories.DeviceRepository;
import pobi.repositories.ServiceRepository;

import java.util.List;

public class ServiceManager {
    private ServiceRepository serviceRepository;

    public ServiceManager() {
        this.serviceRepository = new ServiceRepository();
    }

    /**
     * Zapisuje nową usługę do repozytorium, wraz z podanymi przez użytkownika danymi
     * @param id ID usługi
     * @param basePrice Cena podstawowa usługi
     * @param client Referencja do klienta, dla którego jest rejestrowana usługa
     * @param device Referencja do urządzenia, dla którego jest rejestrowana usługa
     * @param faultDescription Opis usterki urządzenia
     */
    public void registerService(String id, double basePrice, Client client, Device device, String faultDescription) {
        if (client == null || device == null) {
            throw new IllegalArgumentException("Klient lub urządzenie nie może być null");
        }

        if (client.isArchive()) {
            throw new IllegalStateException("Klient jest zarchiwizowany");
        }

        int activeServices = 0;
        for (Service service : serviceRepository.findAll()) {
            if (service.getClient().getPhoneNumber().equals(client.getPhoneNumber()) && !service.isArchive()) {
                activeServices++;
            }
        }

        if (activeServices >= client.getMaxServices()) {
            throw new IllegalStateException("Klient osiagnął maksymalny limit aktywnych uslug");
        }

        Service service = new Service(id, basePrice, client, device, faultDescription);
        serviceRepository.add(service);
    }
   
    /**
     * Zwraca referencję do usługi o podanym ID
     * @param id ID usługi
     * @return Usługa o podanym ID lub null, gdy nie istnieje lub jest zarchiwizowana
     */
    public Service getService(String id) {
        Service service = serviceRepository.findByID(id);
        if (service == null || service.isArchive()) {
            return null;
        }
        return service;
    }

    /**
     * Usuwa usługę o podanym ID z repozytorium
     * @param id ID usługi
     */
    public void unregisterService(String id) {
        serviceRepository.remove(serviceRepository.findByID(id));
    }

    /**
     * Zwraca informacje o wszystkich usługach
     * @return Lista usług
     */
    public List<Service> findAllServices() {
        return serviceRepository.findAll();
    }

    /**
     * Zapisuje dane usług do pliku
     * @param filename nazwa pliku
     */
    public void saveToFile(String filename) {
        serviceRepository.saveToFile(filename);
    }

    /**
     * Wczytuje dane usług z pliku
     * @param filename nazwa pliku
     * @param clientRepo referencja do repozytorium klientów
     * @param deviceRepo referencja do repozytorium urządzeń
     */
    public void loadFromFile(String filename, ClientRepository clientRepo, DeviceRepository deviceRepo) {
        serviceRepository.loadFromFile(filename, clientRepo, deviceRepo);
    }

    /**
     * Zwraca referencję do repozytorium usług
     * @return Repozytorium usług
     */
    public ServiceRepository getServiceRepository() {
        return serviceRepository;
    }
}