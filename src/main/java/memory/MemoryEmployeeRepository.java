package memory;

import java.util.ArrayList;
import java.util.List;
import pobi.model.Employee;
import pobi.repositories.EmployeeRepository;

public class MemoryEmployeeRepository
        implements EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    @Override
    public void add(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException(
                "Pracownik nie może być null"
            );
        }

        for (Employee existing : employees) {
            if (existing.getId() == employee.getId()) {
                throw new IllegalArgumentException(
                    "Pracownik o podanym ID już istnieje"
                );
            }
        }

        employees.add(employee);
    }

    @Override
    public Employee findByID(long id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }

        throw new IllegalArgumentException(
            "Pracownik o podanym ID nie istnieje"
        );
    }

    @Override
    public List<Employee> findAll() {
        return List.copyOf(employees);
    }
}
