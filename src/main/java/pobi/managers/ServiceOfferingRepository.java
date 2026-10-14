package pobi.managers;

import java.util.ArrayList;
import java.util.List;
import pobi.model.ServiceOffering;

public class ServiceOfferingRepository {
    private final List<ServiceOffering> offerings = new ArrayList<>();

    public void add(ServiceOffering offering) {
        if (offering == null) {
            throw new IllegalArgumentException(
                "Pozycja katalogu nie może być null"
            );
        }

        for (ServiceOffering existing : offerings) {
            if (existing.getId().equals(offering.getId())) {
                throw new IllegalArgumentException(
                    "Pozycja katalogu o podanym ID już istnieje"
                );
            }
        }

        offerings.add(offering);
    }

    public ServiceOffering findByID(String id) {
        for (ServiceOffering offering : offerings) {
            if (offering.getId().equals(id)) {
                return offering;
            }
        }

        throw new IllegalArgumentException(
            "Pozycja katalogu o podanym ID nie istnieje."
        );
    }

    public List<ServiceOffering> findAll() {
        return List.copyOf(offerings);
    }
}
