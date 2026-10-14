package pobi.managers;

import pobi.model.Employee;

import java.util.List;
import java.util.Objects;

import pobi.repositories.EmployeeRepository;

public class EmployeeManager {
    private final EmployeeRepository employeeRepository;

    public EmployeeManager(EmployeeRepository employeeRepository) {
        this.employeeRepository = Objects.requireNonNull(employeeRepository);
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
