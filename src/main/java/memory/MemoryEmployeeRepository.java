package memory;

import java.util.ArrayList;
import java.util.List;
import pobi.model.Employee;
import pobi.repositories.EmployeeRepository;

public class MemoryEmployeeRepository
        implements EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    private long nextId = 1;

    @Override
    public synchronized Employee add(Employee employee) {
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

        long id = employee.getId() == 0 ? nextId : employee.getId();
        Employee saved = employee.getId() == 0 ? new Employee(id, employee.getFirstName(), employee.getLastName()) : employee;
        employees.add(saved);
        nextId = Math.max(nextId, id + 1);
        return saved;
    }

    @Override
    public Employee findById(long id) {
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
