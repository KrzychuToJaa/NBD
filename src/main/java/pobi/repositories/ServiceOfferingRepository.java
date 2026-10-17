package pobi.repositories;

import pobi.model.ServiceOffering;
import java.util.List;

public interface ServiceOfferingRepository {
    void add(ServiceOffering offering);

    ServiceOffering findByID(long id);

    List<ServiceOffering> findAll();
}