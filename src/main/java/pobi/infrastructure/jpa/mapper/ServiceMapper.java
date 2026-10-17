package pobi.infrastructure.jpa.mapper;

import pobi.infrastructure.jpa.entity.ServiceEnt;
import pobi.model.Service;

public final class ServiceMapper {

    private ServiceMapper() {
    }

    public static ServiceEnt toEntity(Service service) {
        if (service == null) {
            return null;
        }

        return new ServiceEnt(
            service.getID(),
            ClientMapper.toEntity(service.getClient()),
            EmployeeMapper.toEntity(service.getEmployee()),
            ServiceOfferingMapper.toEntity(service.getOffering()),
            DeviceMapper.toEntity(service.getDevice()),
            service.getBasePrice(),
            service.getActualPrice(),
            service.getFaultDescription(),
            service.isArchive()
        );
    }

    public static Service toDomain(ServiceEnt entity) {
        if (entity == null) {
            return null;
        }

        return new Service(
            entity.getId(),
            ClientMapper.toDomain(entity.getClient()),
            EmployeeMapper.toDomain(entity.getEmployee()),
            ServiceOfferingMapper.toDomain(entity.getOffering()),
            DeviceMapper.toDomain(entity.getDevice()),
            entity.getDescription(),
            entity.getBasePrice(),
            entity.getActualPrice(),
            entity.isArchive()
        );
    }
}
