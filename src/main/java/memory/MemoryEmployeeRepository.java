package memory;

import java.util.ArrayList;
import java.util.List;
import pobi.model.Employee;
import pobi.repositories.EmployeeRepository;

public class MemoryEmployeeRepository
        implements EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public void add(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException(
                "Pracownik nie może być null"
            );
        }

        for (Employee existing : employees) {
            if (existing.getId().equals(employee.getId())) {
                throw new IllegalArgumentException(
                    "Pracownik o podanym ID już istnieje"
                );
            }
        }

        employees.add(employee);
    }

    public Employee findByID(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }

        throw new IllegalArgumentException(
            "Pracownik o podanym ID nie istnieje"
        );
    }

    public List<Employee> findAll() {
        return List.copyOf(employees);
    }
}
