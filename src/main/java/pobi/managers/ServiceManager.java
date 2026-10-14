package pobi.managers;

import pobi.model.Client;
import pobi.model.Device;
import pobi.model.Employee;
import pobi.model.ServiceOffering;
import pobi.model.Service;
import pobi.repositories.ClientRepository;
import pobi.repositories.DeviceRepository;
import pobi.repositories.EmployeeRepository;
import pobi.repositories.ServiceOfferingRepository;
import pobi.repositories.ServiceRepository;
import java.util.List;

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
        this.serviceRepository = serviceRepository;
        this.clientRepository = clientRepository;
        this.deviceRepository = deviceRepository;
        this.employeeRepository = employeeRepository;
        this.offeringRepository = offeringRepository;
    }

    public void registerService (String id, String clientPhone, String employeeId, String offeringId, String deviceId, String description) {
        Client client = clientRepository.findByPhoneNumber(clientPhone);
        Employee employee = employeeRepository.findByID(employeeId);
        ServiceOffering offering = offeringRepository.findByID(offeringId);
        Device device = deviceRepository.findByID(deviceId);

        if  (client.isArchive()) {
            throw new IllegalArgumentException("Nie można zarejestrować usługi dla klienta zarchiwizowanego");
        }

        if (!employee.canTakeMoreServices()) {
            throw new IllegalArgumentException("Nie można zarejestrować usługi dla pracownika ponad limit aktywnych usług");
        }

        Service service = new Service(id, client, employee, offering, device, description);
        serviceRepository.add(service);
    }

    public Service getService(String id) {
        return serviceRepository.findByID(id);
    }

    public void closeService(String id) {
        Service service = serviceRepository.findByID(id);
        service.finishService();
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
