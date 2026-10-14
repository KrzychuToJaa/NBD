package pobi.repositories;

import pobi.model.Service;



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

}
