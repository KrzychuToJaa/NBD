package pobi.managers;

import pobi.model.Client;
import pobi.model.Device;
import pobi.model.Employee;
import pobi.model.ServiceOffering;
import pobi.model.Service;

import java.util.List;
import java.util.Objects;

import pobi.repositories.ClientRepository;
import pobi.repositories.DeviceRepository;
import pobi.repositories.EmployeeRepository;
import pobi.repositories.ServiceOfferingRepository;
import pobi.repositories.ServiceRepository;

public class ServiceManager {
    private final ServiceRepository serviceRepository;
    private final ClientRepository clientRepository;
    private final DeviceRepository deviceRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceOfferingRepository offeringRepository;

    public ServiceManager(
        ServiceRepository serviceRepository,
        ClientRepository clientRepository,
        DeviceRepository deviceRepository,
        EmployeeRepository employeeRepository,
        ServiceOfferingRepository offeringRepository
    )
    {
        this.serviceRepository = Objects.requireNonNull(serviceRepository);
        this.clientRepository = Objects.requireNonNull(clientRepository);
        this.deviceRepository = Objects.requireNonNull(deviceRepository);
        this.employeeRepository = Objects.requireNonNull(employeeRepository);
        this.offeringRepository = Objects.requireNonNull(offeringRepository);
    }

    public Service registerService (long clientId, long employeeId, long offeringId, long deviceId, String description) {
        Client client = clientRepository.findById(clientId);
        Employee employee = employeeRepository.findById(employeeId);
        ServiceOffering offering = offeringRepository.findById(offeringId);
        Device device = deviceRepository.findById(deviceId);

        if  (client.isArchive()) {
            throw new IllegalStateException("Nie można zarejestrować usługi dla klienta zarchiwizowanego");
        }

        if (serviceRepository.countActiveByEmployeeId(employeeId) >= employee.getMaxServices()) {
            throw new IllegalStateException("Nie można zarejestrować usługi dla pracownika ponad limit aktywnych usług");
        }

        Service service = new Service(0, client, employee, offering, device, description);
        return serviceRepository.add(service);
    }

    public List<Service> getClientServices(long clientId) {
        clientRepository.findById(clientId);
        return serviceRepository.findByClientId(clientId);
    }

    public Service getService(long id) {
        return serviceRepository.findById(id);
    }

    public void closeService(long id) {
        serviceRepository.closeService(id);
    }

    public void changeServiceDescription(long id, String description) {
        serviceRepository.changeDescription(id, description);
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<ServiceOffering> getAllServiceOfferings() {
        return offeringRepository.findAll();
    }

    public ServiceRepository getServiceRepository() {
        return serviceRepository;
    }
}
