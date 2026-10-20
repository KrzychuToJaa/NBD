package pobi.repositories;

import pobi.model.Service;
import java.util.List;


public interface ServiceRepository {
    Service add(Service service);

    Service findById(long id);

    List<Service> findAll();

    List<Service> findByEmployeeId(long employeeId);

    long countActiveByEmployeeId(long employeeId);

    void closeService(long id);

    void changeDescription(long id, String description);

    List<Service> findByClientId(long clientId);

    int size();
}