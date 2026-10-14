package pobi.repositories;

import pobi.model.Employee;
import java.util.List;

public interface EmployeeRepository {
    void add(Employee employee);

    Employee findByID(String id);

    List<Employee> findAll();
}