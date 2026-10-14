package pobi.repositories;

import pobi.model.Service;
import java.util.List;

public interface ServiceRepository {
    void add(Service service);

    Service findByID(String id);

    List<Service> findAll();

    List<Service> findByEmployeeId(String employeeId);

    long countActiveByEmployeeId(String employeeId);

    void closeService(String id);

    void changeDescription(String id, String description);

    int size();
}