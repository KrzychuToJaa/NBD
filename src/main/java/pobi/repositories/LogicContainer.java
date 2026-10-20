package pobi.repositories;

import jakarta.persistence.EntityManager;
import pobi.infrastructure.jpa.JpaDataInitializer;
import pobi.infrastructure.jpa.adapter.*;
import pobi.infrastructure.jpa.repository.*;
import pobi.managers.ClientManager;
import pobi.managers.DeviceManager;
import pobi.managers.ServiceManager;
import pobi.managers.EmployeeManager;
import pobi.managers.ServiceOfferingManager;
import java.util.Objects;

public class LogicContainer {
    private final EntityManager entityManager;
    private final ClientManager clientManager;
    private final DeviceManager deviceManager;
    private final ServiceManager serviceManager;
    private final EmployeeManager employeeManager;
    private final ServiceOfferingManager offeringManager;

    private final EmployeeRepository employeeRepository;
    private final ServiceOfferingRepository offeringRepository;

    public LogicContainer(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager);
        ClientRepository clientRepository = new JpaClientRepositoryAdapter(new JpaClientRepository(entityManager));
        DeviceRepository deviceRepository = new JpaDeviceRepositoryAdapter(new JpaDeviceRepository(entityManager));
        employeeRepository = new JpaEmployeeRepositoryAdapter(new JpaEmployeeRepository(entityManager));
        offeringRepository = new JpaServiceOfferingRepositoryAdapter(new JpaServiceOfferingRepository(entityManager));
        ServiceRepository serviceRepository = new JpaServiceRepositoryAdapter(new JpaServiceRepository(entityManager));

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
        JpaDataInitializer.initialize(entityManager);
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
