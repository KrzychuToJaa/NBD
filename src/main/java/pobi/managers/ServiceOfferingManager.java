package pobi.managers;

import pobi.model.ServiceOffering;

import java.util.List;
import java.util.Objects;

import pobi.repositories.ServiceOfferingRepository;

public class ServiceOfferingManager {
    private final ServiceOfferingRepository offeringRepository;

    public ServiceOfferingManager(ServiceOfferingRepository offeringRepository) {

        this.offeringRepository =Objects.requireNonNull(offeringRepository);
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
