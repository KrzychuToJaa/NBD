package pobi.repositories;

import pobi.model.Employee;
import java.util.List;

public interface EmployeeRepository {
    Employee add(Employee employee);

    Employee findById(long id);

    List<Employee> findAll();
}
