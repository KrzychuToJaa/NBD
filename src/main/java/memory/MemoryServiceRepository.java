package memory;

import pobi.model.Service;



import java.util.ArrayList;
import java.util.List;
import pobi.repositories.ServiceRepository;

public class MemoryServiceRepository
implements pobi.repositories.ServiceRepository {
    private List<Service> services;

    public MemoryServiceRepository() {
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
        if (service == null) {
            throw new IllegalArgumentException(
                "Zlecenie nie może być null"
            );
        }

        for (Service existing : services) {
            if (existing.getID().equals(service.getID())) {
                throw new IllegalArgumentException(
                    "Zlecenie o podanym ID już istnieje"
                );
            }
        }

        if (service.isActive() && service.getClient().isArchive()) {
            throw new IllegalStateException(
                "Nie można przyjąć zlecenia dla zarchiwizowanego klienta"
            );
        }

        service.getEmployee().addService(service);
        services.add(service);
    }

    public List<Service> findAll() {
        return List.copyOf(services);
    }

    public int size() {
        return services.size();
    }
}
