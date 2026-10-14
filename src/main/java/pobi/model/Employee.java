package pobi.model;

import java.util.ArrayList;
import java.util.List;


public class Employee {
    private final String id;
    private final String firstName;
    private final String lastName;

    public static final int maxServices = 5;

    private final List<Service> services = new ArrayList<>();

public Employee(String id, String firstName, String lastName) {
        if (id == null || id.isBlank() || firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Nie można utworzyć pracownika z pustymi polami");
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Service> getServices() {
        return List.copyOf(services);
    }

    public int getMaxServices() {
        return maxServices;
    }

    public int getActiveServices() {
        int count = 0;
        for (Service service: services) {
            if (service.isActive()) {
                count++;
            }
        }
        return count;
    }

    public boolean canTakeMoreServices() {
        return getActiveServices() < maxServices;
    }

    public void addService(Service service) {
        if (service == null) {
            throw new IllegalArgumentException(
                "Nie można dodać pustej usługi"
            );
        }

        if (service.getEmployee() != this) {
            throw new IllegalArgumentException(
                "Usługa wskazuje innego pracownika"
            );
        }

        for (Service existing : services) {
            if (existing.getID().equals(service.getID())) {
                throw new IllegalArgumentException(
                    "Usługa jest już przypisana do pracownika"
                );
            }
        }

        if (service.isActive() && !canTakeMoreServices()) {
            throw new IllegalStateException(
                "Pracownik osiągnął limit aktywnych usług"
            );
        }

        services.add(service);
    }

    public String getInfo() {
        return "Pracownik: " + firstName + " " + lastName + ", ID: " + id + ", Liczba aktywnych usług: " + getActiveServices() + "/" + maxServices;
    }
}
