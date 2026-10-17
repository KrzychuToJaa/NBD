package pobi.repositories;

import memory.MemoryDeviceRepository;

import memory.MemoryClientRepository;

import memory.MemoryEmployeeRepository;
import memory.MemoryServiceOfferingRepository;
import memory.MemoryServiceRepository;
import pobi.managers.ClientManager;
import pobi.managers.DeviceManager;
import pobi.managers.ServiceManager;
import pobi.managers.EmployeeManager;
import pobi.managers.ServiceOfferingManager;
import pobi.model.Console;

public class LogicContainer {
    private final ClientManager clientManager;
    private final DeviceManager deviceManager;
    private final ServiceManager serviceManager;
    private final EmployeeManager employeeManager;
    private final ServiceOfferingManager offeringManager;

    private final EmployeeRepository employeeRepository;
    private final ServiceOfferingRepository offeringRepository;

    public LogicContainer() {
        ClientRepository clientRepository = new MemoryClientRepository();
        DeviceRepository deviceRepository = new MemoryDeviceRepository();
        employeeRepository = new MemoryEmployeeRepository();
        offeringRepository = new MemoryServiceOfferingRepository();
        ServiceRepository serviceRepository = new MemoryServiceRepository();

        clientManager = new ClientManager(clientRepository);
        deviceManager = new DeviceManager(deviceRepository);
        employeeManager = new EmployeeManager(employeeRepository);
        offeringManager = new ServiceOfferingManager(offeringRepository);
        serviceManager = new ServiceManager(
            serviceRepository,
            clientRepository,
            deviceRepository,
            employeeRepository,
            offeringRepository
        );
    }

    public void fillData() {
        var client1 = clientManager.registerClient("Maciej", "Mamrot", "123456789", "Łęczno", "Łysa", "12");
        var client2 = clientManager.registerClient("Michał", "Woźniak", "987654321", "Ręczno", "Piotrkowska", "3");
        var pc = deviceManager.registerPC("OptiPlex", "Dell", "Mysz i klawiatura", "Intel", true);
        var laptop = deviceManager.registerLaptop("ThinkPad", "Lenovo", "Zasilacz", "Intel");
        deviceManager.registerLaptop("Jabko Pro", "Apple", "Zasilacz", "Apple");
        deviceManager.registerConsole("Switch", "Nintendo", "JoyCon", Console.ConsoleType.Nintendo);
        var employee1 = employeeManager.registerEmployee("Maciej", "Mamrot");
        var employee2 = employeeManager.registerEmployee("Michał", "Woźniak");
        var disk = offeringManager.registerOffering(200.0, "Wymiana dysku");
        var update = offeringManager.registerOffering(100.0, "Aktualizacja systemu");
        var clean = offeringManager.registerOffering(150.0, "Czyszczenie sprzętu");
        serviceManager.registerService(client1.getClientId(), employee1.getId(), disk.getId(), pc.getId(), "Wymiana uszkodzonego dysku");
        serviceManager.registerService(client1.getClientId(), employee1.getId(), update.getId(), pc.getId(), "Aktualizacja systemu po wymianie dysku");
        serviceManager.registerService(client2.getClientId(), employee2.getId(), clean.getId(), laptop.getId(), "Czyszczenie układu chłodzenia");
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public DeviceManager getDeviceManager() {
        return deviceManager;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public ServiceOfferingRepository getOfferingRepository() {
        return offeringRepository;
    }

    public EmployeeManager getEmployeeManager() {
        return employeeManager;
    }

    public ServiceOfferingManager getOfferingManager() {
        return offeringManager;
    }
}