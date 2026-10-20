package pobi.infrastructure.jpa.mapper;

import pobi.infrastructure.jpa.entity.ServiceOfferingEnt;
import pobi.model.ServiceOffering;

public final class ServiceOfferingMapper {

    private ServiceOfferingMapper() {
    }

    public static ServiceOfferingEnt toEntity(ServiceOffering offering) {
        if (offering == null) {
            return null;
        }

        return new ServiceOfferingEnt(offering.getId(), offering.getBasePrice(), offering.getName());
    }

    public static ServiceOffering toDomain(ServiceOfferingEnt entity) {
        if (entity == null) {
            return null;
        }

        return new ServiceOffering(entity.getId(), entity.getBasePrice(), entity.getName());
    }
}
