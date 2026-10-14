package pobi.managers;

import pobi.model.Employee;

import java.util.List;

import memory.MemoryEmployeeRepository;

public class EmployeeManager {
    private final MemoryEmployeeRepository employeeRepository;

    public EmployeeManager(MemoryEmployeeRepository employeeRepository) {
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
