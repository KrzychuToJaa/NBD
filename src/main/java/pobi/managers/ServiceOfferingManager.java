package pobi.managers;

import pobi.model.ServiceOffering;
import pobi.repositories.ServiceOfferingRepository;
import java.util.List;

public class ServiceOfferingManager {
    private final ServiceOfferingRepository offeringRepository;

    public ServiceOfferingManager(ServiceOfferingRepository offeringRepository) {

        this.offeringRepository =offeringRepository;
    }

    public void registerOffering(String id, double basePrice, String name) {

        ServiceOffering offering = new ServiceOffering(id, basePrice, name);

        offeringRepository.add(offering);
    }

    public ServiceOffering getOffering(String id) {
        return offeringRepository.findByID(id);
    }

    public List<ServiceOffering> findAllOfferings() {
        return offeringRepository.findAll();
    }
}
