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

    public Employee registerEmployee(String firstName, String lastName) {

        Employee employee = new Employee(0, firstName, lastName);

        return employeeRepository.add(employee);
    }

    public Employee getEmployee(long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
}
