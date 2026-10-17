package pobi.infrastructure.jpa.mapper;

import pobi.infrastructure.jpa.entity.EmployeeEnt;
import pobi.model.Employee;

public final class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static EmployeeEnt toEntity(Employee employee) {
        if (employee == null) {
            return null;
        }

        return new EmployeeEnt(
            employee.getId(),
            employee.getFirstName(),
            employee.getLastName()
        );
    }

    public static Employee toDomain(EmployeeEnt entity) {
        if (entity == null) {
            return null;
        }

        return new Employee(
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName()
        );
    }
}
