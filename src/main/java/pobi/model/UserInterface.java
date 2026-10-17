package pobi.model;

import pobi.managers.ClientManager;
import pobi.managers.DeviceManager;
import pobi.managers.EmployeeManager;
import pobi.managers.ServiceManager;
import pobi.managers.ServiceOfferingManager;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class UserInterface {
    private final ClientManager clientManager;
    private final DeviceManager deviceManager;
    private final EmployeeManager employeeManager;
    private final ServiceOfferingManager offeringManager;
    private final ServiceManager serviceManager;

    private final Scanner scanner = new Scanner(System.in);

    public UserInterface(
            ClientManager clientManager,
            DeviceManager deviceManager,
            EmployeeManager employeeManager,
            ServiceOfferingManager offeringManager,
            ServiceManager serviceManager) {

        this.clientManager =
            Objects.requireNonNull(clientManager);
        this.deviceManager =
            Objects.requireNonNull(deviceManager);
        this.employeeManager =
            Objects.requireNonNull(employeeManager);
        this.offeringManager =
            Objects.requireNonNull(offeringManager);
        this.serviceManager =
            Objects.requireNonNull(serviceManager);
    }

    public void start() {
        try {
            while (true) {
                System.out.println("\n=== SERWIS KOMPUTEROWY ===");
                System.out.println("1. Klienci");
                System.out.println("2. Urządzenia");
                System.out.println("3. Pracownicy");
                System.out.println("4. Katalog usług");
                System.out.println("5. Zlecenia");
                System.out.println("0. Zakończ program");

                int choice = readInt("Wybierz opcję: ");

                switch (choice) {
                    case 1 -> clientMenu();
                    case 2 -> deviceMenu();
                    case 3 -> employeeMenu();
                    case 4 -> offeringMenu();
                    case 5 -> serviceMenu();
                    case 0 -> {
                        System.out.println("Zakończono program.");
                        return;
                    }
                    default ->
                        System.out.println("Nie ma takiej opcji.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nZakończono odczyt danych.");
        }
    }

    private void clientMenu() {
        while (true) {
            System.out.println("\nKLIENCI");
            System.out.println("1. Dodaj klienta");
            System.out.println("2. Wyświetl klienta");
            System.out.println("3. Wyświetl wszystkich klientów");
            System.out.println("0. Powrót");

            int choice = readInt("Wybierz opcję: ");

            try {
                switch (choice) {
                    case 1 -> registerClient();
                    case 2 -> showClient();
                    case 3 -> showAllClients();
                    case 0 -> {
                        return;
                    }
                    default ->
                        System.out.println("Nie ma takiej opcji");
                }
            } catch (IllegalArgumentException
                     | IllegalStateException e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }

    private void registerClient() {
        String firstName = readText("Imię: ");
        String lastName = readText("Nazwisko: ");
        long phone = readLong("Telefon — 9 cyfr: ");
        String city = readText("Miasto: ");
        String street = readText("Ulica: ");
        String number = readText("Numer domu: ");

        clientManager.registerClient(firstName, lastName, phone, city, street, number);

        System.out.println("Klient został dodany");
    }

    private void showClient() {
        long phone = readLong("Telefon klienta: ");
        Client client = clientManager.getClient(phone);

        if (client == null) {
            System.out.println("Klient jest niedostępny");
            return;
        }

        System.out.println(client.getInfo());
    }

    private void showAllClients() {
        List<Client> clients = clientManager.findAllClients();

        if (clients.isEmpty()) {
            System.out.println("Brak klientów");
            return;
        }

        for (Client client : clients) {
            System.out.println(client.getInfo());
        }
    }

    private void deviceMenu() {
        while (true) {
            System.out.println("\nURZĄDZENIA");
            System.out.println("1. Dodaj urządzenie");
            System.out.println("2. Wyświetl urządzenie");
            System.out.println("3. Wyświetl wszystkie urządzenia");
            System.out.println("0. Powrót");

            int choice = readInt("Wybierz opcję: ");

            try {
                switch (choice) {
                    case 1 -> registerDevice();
                    case 2 -> {
                        long id = readLong("ID urządzenia: ");
                        Device device = deviceManager.getDevice(id);
                        System.out.println(device.getInfo());
                    }
                    case 3 -> showAllDevices();
                    case 0 -> {
                        return;
                    }
                    default ->
                        System.out.println("Nie ma takiej opcji");
                }
            } catch (IllegalArgumentException
                     | IllegalStateException e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }

    private void registerDevice() {
        System.out.println("1. PC");
        System.out.println("2. Laptop");
        System.out.println("3. Konsola");

        int type = readInt("Typ urządzenia: ");

        if (type < 1 || type > 3) {
            throw new IllegalArgumentException(
                "Nieprawidłowy typ urządzenia"
            );
        }

        long id = readLong("ID urządzenia: ");
        String brand = readText("Marka: ");
        String model = readText("Model: ");
        String accessories = readText("Akcesoria: ");

        switch (type) {
            case 1 -> {
                String cpu = readText("Producent procesora: ");
                boolean cooling = readBoolean(
                    "Chłodzenie wodne? (tak/nie): "
                );

                deviceManager.registerPC(id, model, brand, accessories, cpu, cooling);
            }
            case 2 -> {
                String cpu = readText("Producent procesora: ");

                deviceManager.registerLaptop(id, model, brand, accessories, cpu);
            }
            case 3 -> {
                Console.ConsoleType consoleType =
                    readConsoleType();

                deviceManager.registerConsole(id, model, brand, accessories, consoleType);
            }
        }

        System.out.println("Urządzenie zostało dodane");
    }

    private Console.ConsoleType readConsoleType() {
        System.out.println("1. Nintendo");
        System.out.println("2. PlayStation");
        System.out.println("3. Xbox");
        System.out.println("4. Nieznany");

        int choice = readInt("Rodzaj konsoli: ");

        return switch (choice) {
            case 1 -> Console.ConsoleType.Nintendo;
            case 2 -> Console.ConsoleType.PlayStation;
            case 3 -> Console.ConsoleType.Xbox;
            case 4 -> Console.ConsoleType.Unknown;
            default -> throw new IllegalArgumentException(
                "Nieprawidłowy rodzaj konsoli"
            );
        };
    }

    private void showAllDevices() {
        List<Device> devices = deviceManager.findAllDevices();

        if (devices.isEmpty()) {
            System.out.println("Brak urządzeń");
            return;
        }

        for (Device device : devices) {
            System.out.println(device.getInfo());
        }
    }

    private void employeeMenu() {
        while (true) {
            System.out.println("\nPRACOWNICY");
            System.out.println("1. Dodaj pracownika");
            System.out.println("2. Wyświetl pracownika");
            System.out.println("3. Wyświetl wszystkich pracowników");
            System.out.println("4. Wyświetl zlecenia pracownika");
            System.out.println("0. Powrót");

            int choice = readInt("Wybierz opcję: ");

            try {
                switch (choice) {
                    case 1 -> {
                        long id = readLong("ID pracownika: ");
                        String firstName = readText("Imię: ");
                        String lastName = readText("Nazwisko: ");

                        employeeManager.registerEmployee(id, firstName, lastName);

                        System.out.println("Pracownik został dodany");
                    }
                    case 2 -> {
                        long id = readLong("ID pracownika: ");
                        Employee employee =
                            employeeManager.getEmployee(id);

                        System.out.println(employee.getInfo());
                    }
                    case 3 -> showAllEmployees();
                    case 4 -> showEmployeeServices();
                    case 0 -> {
                        return;
                    }
                    default ->
                        System.out.println("Nie ma takiej opcji");
                }
            } catch (IllegalArgumentException
                     | IllegalStateException e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }

    private void showAllEmployees() {
        List<Employee> employees = employeeManager.findAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("Brak pracowników.");
            return;
        }

        for (Employee employee : employees) {
            System.out.println(employee.getInfo());
        }
    }

    private void showEmployeeServices() {
        long id = readLong("ID pracownika: ");
        Employee employee = employeeManager.getEmployee(id);

        List<Service> services = employee.getServices();

        if (services.isEmpty()) {
            System.out.println("Pracownik nie ma zleceń.");
            return;
        }

        for (Service service : services) {
            System.out.println(service.getInfo());
            System.out.println(
                "Status: "
                + (service.isActive() ? "aktywna" : "zakończona")
            );
        }
    }

    private void offeringMenu() {
        while (true) {
            System.out.println("\n=== KATALOG USŁUG ===");
            System.out.println("1. Dodaj rodzaj usługi");
            System.out.println("2. Wyświetl pozycję katalogu");
            System.out.println("3. Wyświetl cały katalog");
            System.out.println("0. Powrót");

            int choice = readInt("Wybierz opcję: ");

            try {
                switch (choice) {
                    case 1 -> {
                        long id = readLong("ID pozycji katalogu: ");
                        String name = readText("Nazwa usługi: ");
                        double price = readDouble("Cena bazowa: ");

                        offeringManager.registerOffering( id, price, name);

                        System.out.println(
                            "Rodzaj usługi został dodany do katalogu"
                        );
                    }
                    case 2 -> {
                        long id = readLong("ID pozycji katalogu: ");
                        ServiceOffering offering = offeringManager.getOffering(id);
                        System.out.println(offering.getInfo());
                    }
                    case 3 -> showAllOfferings();
                    case 0 -> {
                        return;
                    }
                    default ->
                        System.out.println("Nie ma takiej opcji");
                }
            } catch (IllegalArgumentException
                     | IllegalStateException e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }

    private void showAllOfferings() {
        List<ServiceOffering> offerings =
            offeringManager.findAllOfferings();

        if (offerings.isEmpty()) {
            System.out.println("Katalog jest pusty.");
            return;
        }

        for (ServiceOffering offering : offerings) {
            System.out.println(offering.getInfo());
        }
    }

    private void serviceMenu() {
        while (true) {
            System.out.println("\n=== ZLECENIA ===");
            System.out.println("1. Przyjmij zlecenie");
            System.out.println("2. Wyświetl zlecenie");
            System.out.println("3. Wyświetl wszystkie zlecenia");
            System.out.println("4. Zmień opis zlecenia");
            System.out.println("5. Zakończ zlecenie");
            System.out.println("0. Powrót");

            int choice = readInt("Wybierz opcję: ");

            try {
                switch (choice) {
                    case 1 -> registerService();
                    case 2 -> {
                        long id = readLong("ID zlecenia: ");
                        Service service = serviceManager.getService(id);

                        System.out.println(service.getInfo());
                        System.out.println("Status: "+ (service.isActive() ? "aktywna" : "zakończona") );
                    }
                    case 3 -> showAllServices();
                    case 4 -> {
                        long id = readLong("ID zlecenia: ");
                        String description = readText("Nowy opis: ");

                        serviceManager.changeServiceDescription(id, description);

                        System.out.println("Opis został zmieniony.");
                    }
                    case 5 -> {
                        long id = readLong("ID zlecenia: ");
                        serviceManager.closeService(id);

                        System.out.println( "Zlecenie zakończone " + "Historia została zachowana" );
                    }
                    case 0 -> {
                        return;
                    }
                    default ->
                        System.out.println("Nie ma takiej opcji");
                }
            } catch (IllegalArgumentException
                     | IllegalStateException e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }

    private void registerService() {
        System.out.println("\nPracownicy:");
        showAllEmployees();

        System.out.println("\nKatalog usług:");
        showAllOfferings();

        long id = readLong("\nID nowego zlecenia: ");
        long phone = readLong("Telefon klienta: ");
        long employeeId = readLong("ID pracownika: ");
        long offeringId = readLong("ID pozycji katalogu: ");
        long deviceId = readLong("ID urządzenia: ");
        String description = readText("Opis zlecenia: ");

        serviceManager.registerService(id, phone, employeeId, offeringId, deviceId, description );

        System.out.println("Zlecenie zostało przyjęte");
    }

    private void showAllServices() {
        List<Service> services =
            serviceManager.getAllServices();

        if (services.isEmpty()) {
            System.out.println("Brak zleceń");
            return;
        }

        for (Service service : services) {
            System.out.println(service.getInfo());
            System.out.println(
                "Status: "
                + (service.isActive() ? "aktywna" : "zakończona")
            );
        }
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            String value = readText(prompt);

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Podaj liczbę całkowitą");
            }
        }
    }

    private long readLong(String prompt) {
        while (true) {
            String value = readText(prompt);

            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                System.out.println("Podaj poprawny numer");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            String value = readText(prompt).replace(',', '.');

            try {
                double number = Double.parseDouble(value);

                if (!Double.isFinite(number) || number < 0) {
                    System.out.println(
                        "Podaj skończoną, nieujemną cenę."
                    );
                    continue;
                }

                return number;
            } catch (NumberFormatException e) {
                System.out.println(
                    "Podaj liczbę"
                );
            }
        }
    }

    private boolean readBoolean(String prompt) {
        while (true) {
            String value = readText(prompt);

            if (value.equalsIgnoreCase("tak")) {
                return true;
            }

            if (value.equalsIgnoreCase("nie")) {
                return false;
            }

            System.out.println("Wpisz tak albo nie");
        }
    }
}