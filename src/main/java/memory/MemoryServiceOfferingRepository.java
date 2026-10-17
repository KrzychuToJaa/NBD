package memory;

import java.util.ArrayList;
import java.util.List;
import pobi.model.ServiceOffering;
import pobi.repositories.ServiceOfferingRepository;

public class MemoryServiceOfferingRepository
implements ServiceOfferingRepository {
    private final List<ServiceOffering> offerings = new ArrayList<>();

    @Override
    public void add(ServiceOffering offering) {
        if (offering == null) {
            throw new IllegalArgumentException(
                "Pozycja katalogu nie może być null"
            );
        }

        for (ServiceOffering existing : offerings) {
            if (existing.getId() == offering.getId()) {
                throw new IllegalArgumentException(
                    "Pozycja katalogu o podanym ID już istnieje"
                );
            }
        }

        offerings.add(offering);
    }

    @Override
    public ServiceOffering findByID(long id) {
        for (ServiceOffering offering : offerings) {
            if (offering.getId() == id) {
                return offering;
            }
        }

        throw new IllegalArgumentException(
            "Pozycja katalogu o podanym ID nie istnieje."
        );
    }

    @Override
    public List<ServiceOffering> findAll() {
        return List.copyOf(offerings);
    }
}
