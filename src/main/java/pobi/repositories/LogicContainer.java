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
            "Maciej", "Mamrot", 123456789L,
            "Łęczno", "Łysa", "12"
        );

        clientManager.registerClient(
            "Michał", "Woźniak", 987654321L,
            "Ręczno", "Piotrkowska", "3"
        );

        deviceManager.registerPC(
            1L, "OptiPlex", "Dell",
            "Mysz i klawiatura", "Intel", true
        );

        deviceManager.registerLaptop(
            2L, "ThinkPad", "Lenovo",
            "Zasilacz", "Intel"
        );

        deviceManager.registerLaptop(
            3L, "Jabko Pro", "Apple",
            "Zasilacz", "Apple"
        );

        deviceManager.registerConsole(
            4L, "Switch", "Nintendo",
            "JoyCon", Console.ConsoleType.Nintendo
        );

        employeeRepository.add(
            new Employee(1L, "Maciej", "Mamrot")
        );

        employeeRepository.add(
            new Employee(2L, "Michał", "Woźniak")
        );

        offeringRepository.add(
            new ServiceOffering(
                1L, 200.0, "Wymiana dysku"
            )
        );

        offeringRepository.add(
            new ServiceOffering(
                2L, 100.0, "Aktualizacja systemu"
            )
        );

        offeringRepository.add(
            new ServiceOffering(
                3L, 150.0, "Czyszczenie sprzętu"
            )
        );

        serviceManager.registerService(
            1L,
            123456789L,
            1L,
            1L,
            1L,
            "Wymiana uszkodzonego dysku"
        );

        serviceManager.registerService(
            2L,
            123456789L,
            1L,
            2L,
            1L,
            "Aktualizacja systemu po wymianie dysku"
        );

        serviceManager.registerService(
            3L,
            987654321L,
            2L,
            3L,
            2L,
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