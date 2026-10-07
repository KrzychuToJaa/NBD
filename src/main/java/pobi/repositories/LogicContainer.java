package pobi.repositories;

import pobi.managers.ClientManager;
import pobi.managers.DeviceManager;
import pobi.managers.ServiceManager;
import pobi.model.Console;

public class LogicContainer {
    private ClientManager clientManager;
    private DeviceManager deviceManager;
    private ServiceManager serviceManager;

    /**
     * Konstruktor klasy LogicContainer (ułatwia testowanie i wypełnianie danymi)
     */
    public LogicContainer() {
        this.clientManager = new ClientManager();
        this.deviceManager = new DeviceManager();
        this.serviceManager = new ServiceManager();
    }

    /**
     * Wypełnia repozytoria przykładowymi danymi
     */
    public void fillData() {
        clientManager.registerClient("LeBron", "James", "123456789", "Los Angeles", "Brentwood", "23");
        clientManager.registerClient("Sebastian", "Alvarez", "987654321", "Berlin", "Fide Strase", "3");
        clientManager.registerClient("Mateusz", "Kisielski", "555555555", "Ostrow", "Gruba", "10");

        deviceManager.registerPC("PC1", "PC", "Genesis-2", "Dell", "Myszka i klawiatura", 800.0, "Intel", true);
        deviceManager.registerLaptop("Laptop1", "Laptop", "MacBook Pro M1", "Apple", "Kabel zasilający", 1500.0, "Apple");
        deviceManager.registerConsole("Console1", "Console", "Switch", "Nintendo", "JoyCon", 500.0, Console.ConsoleType.Nintendo);

        serviceManager.registerService("Service1", 800.0, clientManager.getClient("123456789"), deviceManager.getDevice("PC1"), "Uszkodzony dysk");
        serviceManager.registerService("Service2", 1500.0, clientManager.getClient("987654321"), deviceManager.getDevice("Laptop1"), "Problem z baterią");
        serviceManager.registerService("Service3", 500.0, clientManager.getClient("555555555"), deviceManager.getDevice("Console1"), "Problem z ekranem");
    }

    /**
     * Zwraca referencję do menedżera klientów
     * @return Referencja do menedżera klientów
     */
    public ClientManager getClientManager() {
        return clientManager;
    }

    /**
     * Zwraca referencję do menedżera urządzeń
     * @return Referencja do menedżera urządzeń
     */
    public DeviceManager getDeviceManager() {
        return deviceManager;
    }

    /**
     * Zwraca referencję do menedżera usług
     * @return Referencja do menedżera usług
     */
    public ServiceManager getServiceManager() {
        return serviceManager;
    }
}