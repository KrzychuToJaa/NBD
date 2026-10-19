package pobi.infrastructure.jpa.mapper;

import pobi.infrastructure.jpa.entity.ClientEnt;
import pobi.infrastructure.jpa.entity.EmployeeEnt;
import pobi.model.Client;
import pobi.model.Employee;
import pobi.model.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class DomainMappingContext {
    final Map<Long, Client> clients = new HashMap<>();
    final Map<Long, Employee> employees = new HashMap<>();
    final Map<Long, Service> services = new HashMap<>();
    final Set<Long> expandedClients = new HashSet<>();
    final Set<Long> expandedEmployees = new HashSet<>();

    Client client(ClientEnt entity) {
        return clients.computeIfAbsent(entity.getClientId(), ignored -> ClientMapper.toDomainWithoutServices(entity));
    }

    Employee employee(EmployeeEnt entity) {
        return employees.computeIfAbsent(entity.getId(), ignored -> EmployeeMapper.toDomainWithoutServices(entity));
    }
}