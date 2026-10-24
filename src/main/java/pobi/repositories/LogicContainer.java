package pobi.repositories;

import memory.MemoryClientRepository;
import memory.MemoryDeviceRepository;
import memory.MemoryEmployeeRepository;
import memory.MemoryServiceOfferingRepository;
import memory.MemoryServiceRepository;
import pobi.managers.ClientManager;
import pobi.managers.DeviceManager;
import pobi.managers.EmployeeManager;
import pobi.managers.ServiceOfferingManager;
import pobi.managers.ServiceManager;
import pobi.model.Client;
import pobi.model.Device;
import pobi.model.Employee;
import pobi.model.ServiceOffering;

public class LogicContainer {
    private final ClientManager clientManager;
    private final DeviceManager deviceManager;
    private final EmployeeManager employeeManager;
    private final ServiceOfferingManager offeringManager;
    private final ServiceManager serviceManager;
    private final EmployeeRepository employeeRepository;
    private final ServiceOfferingRepository offeringRepository;

    public LogicContainer() {
        this(new MemoryClientRepository(), new MemoryDeviceRepository(), new MemoryEmployeeRepository(), new MemoryServiceOfferingRepository(), new MemoryServiceRepository());
    }

    public LogicContainer(ClientRepository clients, DeviceRepository devices, EmployeeRepository employees, ServiceOfferingRepository offerings, ServiceRepository services) {
        clientManager = new ClientManager(clients);
        deviceManager = new DeviceManager(devices);
        employeeManager = new EmployeeManager(employees);
        offeringManager = new ServiceOfferingManager(offerings);
        serviceManager = new ServiceManager(services, clients, devices, employees, offerings);
        employeeRepository = employees;
        offeringRepository = offerings;
    }

    public void fillData() {
        if (!clientManager.findAllClients().isEmpty() || !deviceManager.findAllDevices().isEmpty() || !employeeManager.findAllEmployees().isEmpty() || !offeringManager.findAllOfferings().isEmpty() || !serviceManager.getAllServices().isEmpty()) {
            return;
        }
        Client firstClient = clientManager.registerClient("Maciej", "Mamrot", "123456789", "Łęczno", "Łysa", "12");
        Client secondClient = clientManager.registerClient("Michał", "Woźniak", "987654321", "Ręczno", "Piotrkowska", "3");
        Employee firstEmployee = employeeManager.registerEmployee("Krzysztof", "Kata");
        Employee secondEmployee = employeeManager.registerEmployee("Michał", "Woźniak");
        Device firstDevice = deviceManager.registerDevice("ThinkPad", "Lenovo", "zasilacz, myszka");
        Device secondDevice = deviceManager.registerDevice("jabko pro", "Apple", "dysk zewnętrzny, zasilacz");
        ServiceOffering disk = offeringManager.registerOffering(200.0, "Wymiana dysku");
        ServiceOffering system = offeringManager.registerOffering(100.0, "Aktualizacja systemu");
        serviceManager.registerService(firstClient.getClientId(), firstEmployee.getId(), disk.getId(), firstDevice.getId(), "Wymiana uszkodzonego dysku");
        serviceManager.registerService(firstClient.getClientId(), firstEmployee.getId(), system.getId(), firstDevice.getId(), "Aktualizacja systemu");
        serviceManager.registerService(secondClient.getClientId(), secondEmployee.getId(), system.getId(), secondDevice.getId(), "Instalacja aktualizacji");
    }

    public ClientManager getClientManager() { return clientManager; }
    public DeviceManager getDeviceManager() { return deviceManager; }
    public EmployeeManager getEmployeeManager() { return employeeManager; }
    public ServiceOfferingManager getOfferingManager() { return offeringManager; }
    public ServiceManager getServiceManager() { return serviceManager; }
    public EmployeeRepository getEmployeeRepository() { return employeeRepository; }
    public ServiceOfferingRepository getOfferingRepository() { return offeringRepository; }
}
