package pobi.repositories;

import pobi.model.ServiceOffering;
import java.util.List;

/** Brak wyniku: IllegalArgumentException. add nadaje ID obiektom z ID 0 i zwraca zapisany obiekt. */
public interface ServiceOfferingRepository {
    ServiceOffering add(ServiceOffering offering);

    ServiceOffering findById(long id);

    List<ServiceOffering> findAll();
}