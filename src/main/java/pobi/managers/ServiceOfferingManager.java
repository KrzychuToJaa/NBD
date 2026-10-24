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

    public ServiceOffering registerOffering(double basePrice, String name) {

        ServiceOffering offering = new ServiceOffering(0, basePrice, name);

        return offeringRepository.add(offering);
    }

    public ServiceOffering getOffering(long id) {
        return offeringRepository.findById(id);
    }

    public List<ServiceOffering> findAllOfferings() {
        return offeringRepository.findAll();
    }
}
