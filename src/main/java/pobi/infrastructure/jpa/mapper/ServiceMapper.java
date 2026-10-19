package pobi.infrastructure.jpa.mapper;

import pobi.infrastructure.jpa.entity.ServiceEnt;
import pobi.model.Service;
import java.util.List;

public final class ServiceMapper {
    private ServiceMapper() {}

    public static ServiceEnt toEntity(Service service) {
        if (service == null) { return null; }
        return new ServiceEnt(service.getId(), ClientMapper.toEntity(service.getClient()),
                EmployeeMapper.toEntity(service.getEmployee()), ServiceOfferingMapper.toEntity(service.getOffering()),
                DeviceMapper.toEntity(service.getDevice()), service.getBasePrice(), service.getFinalPrice(),
                service.getFaultDescription(), service.isArchive());
    }

    public static Service toDomain(ServiceEnt entity) {
        return toDomain(entity, new DomainMappingContext());
    }

    public static List<Service> toDomainList(List<ServiceEnt> entities) {
        DomainMappingContext context = new DomainMappingContext();
        return entities.stream().map(entity -> toDomain(entity, context)).toList();
    }

    static Service toDomain(ServiceEnt entity, DomainMappingContext context) {
        if (entity == null) { return null; }
        Service existing = context.services.get(entity.getId());
        if (existing != null) { return existing; }
        Service service = new Service(entity.getId(), context.client(entity.getClient()),
                context.employee(entity.getEmployee()), ServiceOfferingMapper.toDomain(entity.getOffering()),
                DeviceMapper.toDomain(entity.getDevice()), entity.getDescription(),
                entity.getBasePrice(), entity.getFinalPrice(), entity.isArchive());
        // Zapamiętujemy przed mapowaniem relacji: kolejne odwiedziny zwrócą ten sam obiekt.
        context.services.put(entity.getId(), service);
        service.getClient().addService(service);
        service.getEmployee().addService(service);
        ClientMapper.toDomain(entity.getClient(), context);
        EmployeeMapper.toDomain(entity.getEmployee(), context);
        return service;
    }
}