package pobi.managers;

import pobi.model.Employee;
import pobi.repositories.EmployeeRepository;
import java.util.List;

public class EmployeeManager {
    private final EmployeeRepository employeeRepository;

    public EmployeeManager(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void registerEmployee(String id, String firstName, String lastName) {

        Employee employee = new Employee(id, firstName, lastName);

        employeeRepository.add(employee);
    }

    public Employee getEmployee(String id) {
        return employeeRepository.findByID(id);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
}
