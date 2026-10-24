package pobi.repositories;

import pobi.model.ServiceOffering;
import java.util.List;

public interface ServiceOfferingRepository {
    ServiceOffering add(ServiceOffering offering);

    ServiceOffering findById(long id);

    List<ServiceOffering> findAll();
}
