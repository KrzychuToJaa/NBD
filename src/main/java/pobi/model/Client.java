package pobi.model;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final long clientId;
    private final String firstName;
    private final String lastName;
    private String phoneNumber;
    private final String city;
    private final String street;
    private final String number;
    private boolean archive;
    private final List<Service> services = new ArrayList<>();

    public Client(long clientId, String firstName, String lastName, String phoneNumber, String city, String street, String number) {
        this(clientId, firstName, lastName, phoneNumber, city, street, number, false);
    }

    /** ID 0 oznacza nowego klienta; dodatnie ID służy odtworzeniu zapisanego klienta. */
    public Client(long clientId, String firstName, String lastName, String phoneNumber, String city, String street, String number, boolean archive) {
        if (clientId < 0 || firstName == null || firstName.isBlank()
                || lastName == null || lastName.isBlank() || city == null || city.isBlank()
                || street == null || street.isBlank() || number == null || number.isBlank()) {
            throw new IllegalArgumentException("Nie można utworzyć klienta z pustymi polami lub ujemnym ID");
        }
        validatePhoneNumber(phoneNumber);
        if (number.length() > 3) {
            throw new IllegalArgumentException("Numer domu musi mieć max 3 znaki");
        }
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.number = number;
        this.archive = archive;
    }

    public static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("[0-9]{9}")) {
            throw new IllegalArgumentException("Numer telefonu musi składać się z dokładnie 9 cyfr");
        }
    }

    public long getClientId() { return clientId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCity() { return city; }
    public String getStreet() { return street; }
    public String getNumber() { return number; }
    public boolean isArchive() { return archive; }
    public List<Service> getServices() { return List.copyOf(services); }

    /** Unikalność telefonu dla wszystkich klientów dodatkowo sprawdza repozytorium. */
    public void changePhoneNumber(String phoneNumber) {
        requireActive();
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public void archive() {
        requireActive();
        if (services.stream().anyMatch(Service::isActive)) {
            throw new IllegalStateException("Klient ma aktywne zlecenia; najpierw je zakończ");
        }
        archive = true;
    }

    public void addService(Service service) {
        if (service == null || service.getClient() != this) {
            throw new IllegalArgumentException("Zlecenie musi wskazywać tego klienta");
        }
        if (service.isActive()) { requireActive(); }
        if (services.stream().anyMatch(existing -> existing.getId() == service.getId())) {
            throw new IllegalArgumentException("Zlecenie jest już przypisane do klienta");
        }
        services.add(service);
    }

    private void requireActive() {
        if (archive) {
            throw new IllegalStateException("Zarchiwizowanego klienta nie można modyfikować");
        }
    }

    public String getInfo() {
        return "ID klienta: " + clientId + "\nImię: " + firstName + "\nNazwisko: " + lastName
            + "\nNumer telefonu: " + phoneNumber + "\nMiasto: " + city + "\nUlica: " + street
            + "\nNumer: " + number + "\nStatus: " + (archive ? "zarchiwizowany" : "aktywny") + "\n";
    }
}
