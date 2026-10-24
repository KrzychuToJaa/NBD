package memory;

import pobi.model.Service;

import java.util.ArrayList;
import java.util.List;
import pobi.repositories.ServiceRepository;

public class MemoryServiceRepository
implements ServiceRepository {
    private List<Service> services;
    private long nextId = 1;

    public MemoryServiceRepository() {
        this.services = new ArrayList<>();
    }

    @Override
    public Service findById(long id) {
        for (Service service : services) {
            if (service.getId() == id) {
                return service;
            }
        }
        throw new IllegalArgumentException("Usługa o podanym ID nie została znaleziona");
    }

    @Override
    public synchronized Service add(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Zlecenie nie może być null");
        }

        for (Service existing : services) {
            if (existing.getId() == service.getId()) {
                throw new IllegalArgumentException("Zlecenie o podanym ID już istnieje");
            }
        }

        if (service.isActive() && service.getClient().isArchive()) {
            throw new IllegalStateException("Nie można przyjąć zlecenia dla zarchiwizowanego klienta");
        }

        long id = service.getId() == 0 ? nextId : service.getId();
        Service saved = service.getId() == 0 ? new Service(id, service.getClient(), service.getEmployee(), service.getOffering(), service.getDevice(), service.getFaultDescription(), service.getBasePrice(), service.getFinalPrice(), service.isArchive()) : service;
        saved.getEmployee().addService(saved);
        saved.getClient().addService(saved);
        services.add(saved);
        nextId = Math.max(nextId, id + 1);
        return saved;
    }

    @Override
    public List<Service> findAll() {
        return List.copyOf(services);
    }

    @Override
    public List<Service> findByEmployeeId(long employeeId) {
        List<Service> result = new ArrayList<>();
        for (Service service : services) {
            if (service.getEmployee().getId() == employeeId) {
                result.add(service);
            }
        }
        return List.copyOf(result);
    }

    @Override
    public long countActiveByEmployeeId(long employeeId) {
        long count = 0;
        for (Service service : services) {
            if (service.getEmployee().getId() == employeeId && service.isActive()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void closeService(long id) {
        findById(id).finishService();
    }

    @Override
    public void changeDescription(long id, String description) {
        findById(id).setFaultDescription(description);
    }

    @Override
    public List<Service> findByClientId(long clientId) {
        return services.stream().filter(s -> s.getClient().getClientId() == clientId).toList();
    }

    @Override
    public int size() {
        return services.size();
    }
}
