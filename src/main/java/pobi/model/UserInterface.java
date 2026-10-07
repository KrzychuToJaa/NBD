package pobi.model;

import java.util.List;
import java.util.Scanner;

import pobi.managers.ClientManager;
import pobi.managers.DeviceManager;
import pobi.managers.ServiceManager;

public class UserInterface {
    private ClientManager clientManager;
    private ServiceManager serviceManager;
    private DeviceManager deviceManager;
    private Scanner scanner;

    /**
     * Konstruktor obiektu klasy UserInterface
     * @param clientManager menedżer klasy client
     * @param serviceManager menedżer klasy service
     * @param deviceManager menedżer klasy device
     */
    public UserInterface(ClientManager clientManager, ServiceManager serviceManager, DeviceManager deviceManager) {
        this.clientManager = clientManager;
        this.serviceManager = serviceManager;
        this.deviceManager = deviceManager;
        this.scanner = new Scanner(System.in);
    }

    /**
     * metoda czyszcząca konsolę
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * metoda pokazująca menu klientów
     */
    private void clientMenu() {
        clearScreen();
        while (true) {
            System.out.println("Zarządzanie klientami");
            System.out.println("1. Zarejestruj klienta");
            System.out.println("2. Wyświetl klienta");
            System.out.println("3. Wyświetl wszystkich klientów");
            System.out.println("4. Usuń klienta");
            System.out.println("5. Zmień archiwizację klienta");
            System.out.println("6. Zapisz klientów do pliku");
            System.out.println("7. Wczytaj klientów z pliku");
            System.out.println("8. Zmień maksymalną liczbę usług klienta");
            System.out.println("0. Powrót do menu głównego");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Konsumuje znak nowej linii po int
            
            switch (choice) {
                case 1: {
                    clearScreen();
                    System.out.println("Rejestracja klienta");
                    System.out.print("Podaj imię: ");
                    String firstName = scanner.next();
                    System.out.print("Podaj nazwisko: ");
                    String lastName = scanner.next();
                    System.out.print("Podaj numer telefonu: ");
                    String phoneNumber = scanner.next();
                    scanner.nextLine();
                    
                    System.out.print("Podaj miasto: ");
                    String city = scanner.nextLine();
                    System.out.print("Podaj ulicę: ");
                    String street = scanner.nextLine();
                    System.out.print("Podaj numer domu: ");
                    String number = scanner.next();
                    
                    try {
                        clientManager.registerClient(firstName, lastName, phoneNumber, city, street, number);
                        clearScreen();
                        System.out.println("Klient zarejestrowany");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 2: {
                    clearScreen();
                    System.out.println("Wyświetlanie klienta");
                    System.out.print("Podaj numer telefonu klienta: ");
                    String phoneNumber = scanner.next();
                    try {
                        Client client = clientManager.getClient(phoneNumber);
                        if (client != null) {
                            System.out.println(client.getInfo());
                        } else {
                            System.out.println("Nie znaleziono klienta.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 3: {
                    clearScreen();
                    System.out.println("Wyświetlanie wszystkich klientów");
                    try {
                        List<Client> clients = clientManager.findAllClients();
                        for (Client client : clients) {
                            System.out.println(client.getInfo());
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 4: {
                    clearScreen();
                    System.out.println("Usuwanie klienta");
                    System.out.print("Podaj numer telefonu klienta: ");
                    String phoneNumber = scanner.next();
                    try {
                        clientManager.unregisterClient(phoneNumber);
                        System.out.println("Klient usunięty");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 5: {
                    clearScreen();
                    System.out.print("Czy klient ma być archiwalny? (tak/nie): ");
                    String archiveChoice = scanner.next();
                    boolean archive;
                    if (archiveChoice.equalsIgnoreCase("tak")) {
                        archive = true;
                    } else if (archiveChoice.equalsIgnoreCase("nie")) {
                        archive = false;
                    } else {
                        System.out.println("Zły wybór. Spróbuj ponownie");
                        break;
                    }
                    System.out.print("Podaj numer telefonu klienta: ");
                    String phoneNumber = scanner.next();
                    clearScreen();
                    Client client = clientManager.getClient(phoneNumber);
                    if (client != null) {
                        client.setArchive(archive);
                        System.out.println("Archiwizacja klienta zaktualizowana");
                    } else {
                        System.out.println("Klient nie został znaleziony");
                    }
                    break;
                }
                case 6: {
                    clearScreen();
                    System.out.print("Podaj nazwę pliku do zapisu klientów: ");
                    String filename = scanner.next();
                    clearScreen();
                    clientManager.saveToFile(filename);
                    System.out.println("Klienci zapisani do pliku");
                    break;
                }
                case 7: {
                    clearScreen();
                    System.out.print("Uwaga: Wczytanie klientów z pliku spowoduje usunięcie wszystkich aktualnych klientów w pamięci programu. Czy chcesz kontynuować? (tak/nie): ");
                    String loadChoice = scanner.next();
                    if (loadChoice.equalsIgnoreCase("tak")) {
                        System.out.print("Podaj nazwę pliku do wczytania klientów: ");
                        String filename = scanner.next();
                        clientManager.loadFromFile(filename);
                        clearScreen();
                        System.out.println("Klienci wczytani z pliku");
                    } else {
                        System.out.println("Operacja anulowana");
                    }               
                    break;
                }
                case 8: {
                    clearScreen();
                    System.out.println("Zmiana maksymalnej liczby usług klienta");
                    System.out.print("Podaj numer telefonu klienta: ");
                    String phoneNumber = scanner.next();
                    System.out.print("Podaj nową maksymalną liczbę usług: ");
                    int maxServices = scanner.nextInt();
                    clearScreen();
                    Client client = clientManager.getClient(phoneNumber);
                    if (client != null) {
                        try {
                            client.setMaxServices(maxServices);
                            System.out.println("Maksymalna liczba usług klienta zaktualizowana");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Klient nie został znaleziony");
                    }
                    break;
                }
                case 0:
                    clearScreen();
                    return;
                default:
                    clearScreen();
                    System.out.println("Zły wybór. Spróbuj ponownie");
            }
        }
    }

    /**
     * metoda pokazująca menu urządzeń
     */
    private void deviceMenu() {
        clearScreen();
        while (true) {
            System.out.println("Zarządzanie urządzeniami");
            System.out.println("1. Zarejestruj urządzenie");
            System.out.println("2. Wyświetl urządzenie");
            System.out.println("3. Wyświetl wszystkie urządzenia");
            System.out.println("4. Zmień cenę urządzenia");
            System.out.println("5. Usuń urządzenie");
            System.out.println("6. Zapisz urządzenia do pliku");
            System.out.println("7. Wczytaj urządzenia z pliku");
            System.out.println("0. Powrót do menu głównego");
            
            int mainChoice = scanner.nextInt();
            scanner.nextLine();
            
            switch (mainChoice) {
                case 1: {
                    clearScreen();
                    String type = "";
                    System.out.println("Rejestracja urządzenia");
                    System.out.println("Jakie urządzenie chcesz zarejestrować?");
                    System.out.println("1. PC");
                    System.out.println("2. Laptop");
                    System.out.println("3. Konsola");
                    
                    int typeChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (typeChoice) {
                        case 1: type = "PC"; break;
                        case 2: type = "Laptop"; break;
                        case 3: type = "Console"; break;
                        default:
                            System.out.println("Zły wybór. Spróbuj ponownie");
                            continue;
                    }
                    System.out.print("Podaj ID: ");
                    String id = scanner.next();
                    System.out.print("Podaj markę: ");
                    String brand = scanner.next();
                    scanner.nextLine();
                    
                    System.out.print("Podaj model: ");
                    String model = scanner.nextLine();
                    System.out.print("Podaj akcesoria: ");
                    String accessories = scanner.nextLine();
                    System.out.print("Podaj cenę bazową: ");
                    double basePrice = scanner.nextDouble();
                    scanner.nextLine();
                    
                    try {
                        if (type.equals("PC")) {
                            System.out.print("Podaj producenta procesora (Intel, AMD): ");
                            String cpuManufacturer = scanner.next();
                            System.out.print("Czy jest chłodzenie wodne? (tak/nie): ");
                            String czyWaterCooling = scanner.next();
                            boolean waterCooling = czyWaterCooling.equalsIgnoreCase("tak");
                            clearScreen();
                            deviceManager.registerPC(id, type, model, brand, accessories, basePrice, cpuManufacturer, waterCooling);
                            System.out.println("Urządzenie zarejestrowane");
                        } else if (type.equals("Laptop")) {
                            System.out.print("Podaj producenta procesora (Intel, AMD): ");
                            String cpuManufacturer = scanner.next();
                            clearScreen();
                            deviceManager.registerLaptop(id, type, model, brand, accessories, basePrice, cpuManufacturer);
                            System.out.println("Urządzenie zarejestrowane");
                        } else if (type.equals("Console")) {
                            System.out.println("Wybierz typ konsoli:");
                            System.out.println("1. Nintendo");
                            System.out.println("2. PlayStation");
                            System.out.println("3. Xbox");
                            System.out.println("4. Nieznany");
                            int consoleChoice = scanner.nextInt();
                            Console.ConsoleType consoleType;
                            switch (consoleChoice) {
                                case 1: consoleType = Console.ConsoleType.Nintendo; break;
                                case 2: consoleType = Console.ConsoleType.PlayStation; break;
                                case 3: consoleType = Console.ConsoleType.Xbox; break;
                                case 4: consoleType = Console.ConsoleType.Unknown; break;
                                default:
                                    System.out.println("Zły wybór. Spróbuj ponownie");
                                    continue;
                            }
                            clearScreen();
                            deviceManager.registerConsole(id, type, model, brand, accessories, basePrice, consoleType);
                            System.out.println("Urządzenie zarejestrowane");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 2: {
                    clearScreen();
                    System.out.println("Wyświetlanie urządzenia");
                    System.out.print("Podaj numer seryjny urządzenia: ");
                    String id = scanner.next();
                    try {
                        Device device = deviceManager.getDevice(id);
                        clearScreen();
                        if(device != null) {
                            System.out.println(device.getInfo());
                        } else {
                            System.out.println("Nie odnaleziono sprzętu.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 3: {
                    clearScreen();
                    System.out.println("Wyświetlanie wszystkich urządzeń");
                    try{
                        List<Device> devices = deviceManager.findAllDevices();
                        for (Device device : devices) {
                            System.out.println(device.getInfo());
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 4: {
                    clearScreen();
                    System.out.println("Zmiana ceny urządzenia");
                    System.out.print("Podaj numer seryjny urządzenia: ");
                    String id = scanner.next();
                    System.out.print("Podaj nową cenę: ");
                    double newPrice = scanner.nextDouble();
                    try {
                        Device device = deviceManager.getDevice(id);
                        if(device != null){
                            device.setBasePrice(newPrice);
                            clearScreen();
                            System.out.println("Cena urządzenia zaktualizowana");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 5: {
                    clearScreen();
                    System.out.println("Usuwanie urządzenia");
                    System.out.print("Podaj numer seryjny urządzenia: ");
                    String id = scanner.next();
                    try {
                        deviceManager.unregisterDevice(id);
                        clearScreen();
                        System.out.println("Urządzenie usunięte");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 6: {
                    clearScreen();
                    System.out.print("Podaj nazwę pliku do zapisu urządzeń: ");
                    String filename = scanner.next();
                    deviceManager.saveToFile(filename);
                    clearScreen();
                    System.out.println("Urządzenia zapisane do pliku");
                    break;
                }
                case 7: {
                    clearScreen();
                    System.out.print("Uwaga: Wczytanie urządzeń z pliku spowoduje usunięcie wszystkich aktualnych urządzeń w pamięci programu. Czy chcesz kontynuować? (tak/nie): ");
                    String loadChoice = scanner.next();
                    if (loadChoice.equalsIgnoreCase("tak")) {
                        System.out.print("Podaj nazwę pliku do wczytania urządzeń: ");
                        String filename = scanner.next();
                        deviceManager.loadFromFile(filename);
                        clearScreen();
                        System.out.println("Urządzenia wczytane z pliku");
                    } else {
                        clearScreen();
                        System.out.println("Operacja anulowana");
                    }
                    break;
                }
                case 0:
                    clearScreen();
                    return;
                default:
                    clearScreen();
                    System.out.println("Zły wybór. Spróbuj ponownie");
            }
        }
    }

    /**
     * metoda pokazująca menu usług
     */
    private void serviceMenu() {
        clearScreen();
        while (true) {
            System.out.println("Zarządzanie usługami");
            System.out.println("1. Zarejestruj usługę");
            System.out.println("2. Wyświetl usługę");
            System.out.println("3. Wyświetl wszystkie usługi");
            System.out.println("4. Zmień opis usterki");
            System.out.println("5. Usuń usługę");
            System.out.println("6. Zapisz usługi do pliku");
            System.out.println("7. Wczytaj usługi z pliku");
            System.out.println("0. Powrót do menu głównego");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: {
                    clearScreen();
                    System.out.println("Rejestracja usługi");
                    System.out.print("Podaj ID: ");
                    String id = scanner.next();
                    System.out.print("Podaj numer telefonu klienta: ");
                    String clientPhone = scanner.next();
                    System.out.print("Podaj ID urządzenia: ");
                    String deviceId = scanner.next();
                    scanner.nextLine();
                    
                    System.out.print("Podaj opis usterki: ");
                    String faultDescription = scanner.nextLine();
                    
                    try {
                        Device device = deviceManager.getDevice(deviceId);
                        Client client = clientManager.getClient(clientPhone);
                        if (device != null && client != null) {
                            double basePrice = device.getBasePrice();
                            serviceManager.registerService(id, basePrice, client, device, faultDescription);
                            clearScreen();
                            System.out.println("Usługa zarejestrowana");
                        } else {
                            System.out.println("Nie odnaleziono klienta lub urządzenia.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 2: {
                    clearScreen();
                    System.out.println("Wyświetlanie usługi");
                    System.out.print("Podaj ID usługi: ");
                    String id = scanner.next();
                    try {
                        Service service = serviceManager.getService(id);
                        clearScreen();
                        if (service != null) {
                            System.out.println(service.getInfo());
                        } else {
                            System.out.println("Usługa nie została znaleziona.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 3: {
                    clearScreen();
                    System.out.println("Wyświetlanie wszystkich usług");
                    try {
                        List<Service> services = serviceManager.findAllServices();
                        for (Service service : services) {
                            System.out.println(service.getInfo());
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 4: {
                    clearScreen();
                    System.out.println("Zmiana opisu usterki");
                    System.out.print("Podaj ID usługi: ");
                    String id = scanner.next();
                    scanner.nextLine();
                    System.out.print("Podaj nowy opis usterki: ");
                    String newDescription = scanner.nextLine();
                    try {
                        Service service = serviceManager.getService(id);
                        if (service != null) {
                            service.setFaultDescription(newDescription);
                            clearScreen();
                            System.out.println("Opis usterki zaktualizowany");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 5: {
                    clearScreen();
                    System.out.println("Usuwanie usługi");
                    System.out.print("Podaj ID usługi: ");
                    String id = scanner.next();
                    try {
                        serviceManager.unregisterService(id);
                        clearScreen();
                        System.out.println("Usługa usunięta");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 6: {
                    clearScreen();
                    System.out.print("Podaj nazwę pliku do zapisu usług: ");
                    String filename = scanner.next();
                    serviceManager.saveToFile(filename);
                    clearScreen();
                    System.out.println("Usługi zapisane do pliku");
                    break;
                }
                case 7: {
                    clearScreen();
                    System.out.print("Uwaga: Wczytanie usług z pliku spowoduje usunięcie wszystkich aktualnych usług w pamięci programu. Czy chcesz kontynuować? (tak/nie): ");
                    String loadChoice = scanner.next();
                    if (loadChoice.equalsIgnoreCase("tak")) {          
                        System.out.print("Podaj nazwę pliku do wczytania usług: ");
                        String filename = scanner.next();
                        serviceManager.loadFromFile(filename, clientManager.getClientRepository(), deviceManager.getDeviceRepository());
                        clearScreen();
                        System.out.println("Usługi wczytane z pliku");
                    } else {
                        clearScreen();
                        System.out.println("Operacja anulowana");
                    }
                    break;
                }
                case 0:
                    clearScreen();
                    return;
                default:
                    clearScreen();
                    System.out.println("Zły wybór. Spróbuj ponownie");
            }
        }
    }

    /**
     * metoda pokazująca menu startowe
     */
    private void showMenu() {
        clearScreen();
        while (true) {
            System.out.println("\nMenu główne");
            System.out.println("1. Zarządzanie klientami");
            System.out.println("2. Zarządzanie urządzeniami");
            System.out.println("3. Zarządzanie usługami");
            System.out.println("0. Wyjście");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    clearScreen();
                    clientMenu();
                    break;
                case 2:
                    clearScreen();
                    deviceMenu();
                    break;
                case 3:
                    clearScreen();
                    serviceMenu();
                    break;
                case 0:
                    System.out.println("Zamknięto program");
                    return;
                default:
                    clearScreen();
                    System.out.println("Zły wybór. Spróbuj ponownie");
            }
        }
    }

    /**
     * metoda uruchamiająca program
     */
    public void start() {
        showMenu();
    }
}