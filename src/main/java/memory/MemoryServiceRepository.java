package memory;

import pobi.model.Service;



import java.util.ArrayList;
import java.util.List;
import pobi.repositories.ServiceRepository;

public class MemoryServiceRepository
implements ServiceRepository {
    private List<Service> services;

    public MemoryServiceRepository() {
        this.services = new ArrayList<>();
    }

    /**
     * Znajduje usługę po ID i zwraca ją
     * @param id ID usługi
     * @return referencja na usługę
     */
    @Override
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
    @Override
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

    @Override
    public List<Service> findAll() {
        return List.copyOf(services);
    }

    @Override
    public List<Service> findByEmployeeId(String employeeId) {
        List<Service> result = new ArrayList<>();
        for (Service service : services) {
            if (service.getEmployee().getId().equals(employeeId)) {
                result.add(service);
            }
        }
        return List.copyOf(result);
    }

    @Override
    public long countActiveByEmployeeId(String employeeId) {
        long count = 0;
        for (Service service : services) {
            if (service.getEmployee().getId().equals(employeeId) && service.isActive()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void closeService(String id) {
        findByID(id).finishService();
    }

    @Override
    public void changeDescription(String id, String description) {
        findByID(id).setFaultDescription(description);
    }

    @Override
    public int size() {
        return services.size();
    }
}
