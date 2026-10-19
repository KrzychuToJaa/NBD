package pobi.infrastructure.jpa.mapper;

import pobi.infrastructure.jpa.entity.EmployeeEnt;
import pobi.model.Employee;
import java.util.List;

public final class EmployeeMapper {
    private EmployeeMapper() {}

    public static EmployeeEnt toEntity(Employee employee) {
        if (employee == null) { return null; }
        return new EmployeeEnt(employee.getId(), employee.getFirstName(), employee.getLastName());
    }

    public static Employee toDomainWithoutServices(EmployeeEnt entity) {
        if (entity == null) { return null; }
        return new Employee(entity.getId(), entity.getFirstName(), entity.getLastName());
    }

    public static Employee toDomain(EmployeeEnt entity) {
        return toDomain(entity, new DomainMappingContext());
    }

    public static List<Employee> toDomainList(List<EmployeeEnt> entities) {
        DomainMappingContext context = new DomainMappingContext();
        return entities.stream().map(entity -> toDomain(entity, context)).toList();
    }

    static Employee toDomain(EmployeeEnt entity, DomainMappingContext context) {
        if (entity == null) { return null; }
        Employee employee = context.employee(entity);
        if (context.expandedEmployees.add(entity.getId())) {
            for (var service : entity.getServices()) {
                ServiceMapper.toDomain(service, context);
            }
        }
        return employee;
    }
}