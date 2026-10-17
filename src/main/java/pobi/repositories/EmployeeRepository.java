package pobi.repositories;

import pobi.model.Employee;
import java.util.List;

/** Brak wyniku: IllegalArgumentException. add nadaje ID obiektom z ID 0 i zwraca zapisany obiekt. */
public interface EmployeeRepository {
    Employee add(Employee employee);

    Employee findById(long id);

    List<Employee> findAll();
}