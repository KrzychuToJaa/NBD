package pobi.model;

public class Service {
    private final String id;
    private Client client;
    private Employee employee;
    private ServiceOffering offering;
    private Device device;
    private double basePrice;
    private String description;
    private boolean archive = false;

    public Service(String id, Client client, Employee employee, ServiceOffering offering, Device device, String description) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID zlecenia nie może być puste");
        }

        if (client == null || employee == null
                || offering == null || device == null) {
            throw new IllegalArgumentException("Zlecenie wymaga klienta, pracownika, rodzaju usługi i urządzenia");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Opis zlecenia nie może być pusty");
        }

        this.id = id;
        this.client = client;
        this.employee = employee;
        this.offering = offering;
        this.device = device;
        this.description = description;

        this.basePrice = offering.getBasePrice();
    }

    public String getID() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ServiceOffering getOffering() {
        return offering;
    }

    public Device getDevice() {
        return device;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getFinalPrice() {
        return device.getActualPrice(basePrice);
    }

    public String getFaultDescription() {
        return description;
    }

    public void setFaultDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                "Opis zlecenia nie może być pusty"
            );
        }

        this.description = description;
    }

    public boolean isArchive() {
        return archive;
    }

    public boolean isActive() {
        return !archive;
    }

    public void finishService() {
        archive = true;
    }

    public String getInfo() {
        return "ID: " + id + ", Klient: " + client.getFirstName() + " " + client.getLastName() + ", Pracownik: " + employee.getFirstName() + " " + employee.getLastName() + ", Usługa: " + offering.getName() + ", Urządzenie: " + device.getBrand() + " " + device.getModel() + ", Cena końcowa: " + getFinalPrice() + ", Opis usterki: " + description + ", Status: " + (archive ? "zakończona" : "aktywna");
    }
}