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
import pobi.model.Employee;
import pobi.model.ServiceOffering;

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
        clientManager.registerClient(
            "Maciej", "Mamrot", "123456789",
            "Łęczno", "Łysa", "12"
        );

        clientManager.registerClient(
            "Michał", "Woźniak", "987654321",
            "Ręczno", "Piotrkowska", "3"
        );

        deviceManager.registerPC(
            "PC1", "PC", "OptiPlex", "Dell",
            "Mysz i klawiatura", "Intel", true
        );

        deviceManager.registerLaptop(
            "Laptop1", "Laptop", "ThinkPad", "Lenovo",
            "Zasilacz", "Intel"
        );

        deviceManager.registerLaptop(
            "Laptop2", "Laptop", "Jabko Pro", "Apple",
            "Zasilacz", "Apple"
        );

        deviceManager.registerConsole(
            "Console1", "Console", "Switch", "Nintendo",
            "JoyCon", Console.ConsoleType.Nintendo
        );

        employeeRepository.add(
            new Employee("EMP1", "Maciej", "Mamrot")
        );

        employeeRepository.add(
            new Employee("EMP2", "Michał", "Woźniak")
        );

        offeringRepository.add(
            new ServiceOffering(
                "DISK", 200.0, "Wymiana dysku"
            )
        );

        offeringRepository.add(
            new ServiceOffering(
                "UPDATE", 100.0, "Aktualizacja systemu"
            )
        );

        offeringRepository.add(
            new ServiceOffering(
                "CLEAN", 150.0, "Czyszczenie sprzętu"
            )
        );

        serviceManager.registerService(
            "Service1",
            "123456789",
            "EMP1",
            "DISK",
            "PC1",
            "Wymiana uszkodzonego dysku"
        );

        serviceManager.registerService(
            "Service2",
            "123456789",
            "EMP1",
            "UPDATE",
            "PC1",
            "Aktualizacja systemu po wymianie dysku"
        );

        serviceManager.registerService(
            "Service3",
            "987654321",
            "EMP2",
            "CLEAN",
            "Laptop1",
            "Czyszczenie układu chłodzenia"
        );
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