package pobi.model;

public class Service {
    private final long id;
    private final Client client;
    private final Employee employee;
    private final ServiceOffering offering;
    private final Device device;
    private final double basePrice;
    private final double actualPrice;
    private String description;
    private boolean archive = false;

    public Service(long id, Client client, Employee employee, ServiceOffering offering, Device device, String description) {
        this(id, client, employee, offering, device, description,
                offering != null ? offering.getBasePrice() : 0.0,
                (offering != null && device != null) ? device.getActualPrice(offering.getBasePrice()) : 0.0,
                false);
    }

    public Service(long id, Client client, Employee employee, ServiceOffering offering, Device device,
                   String description, double basePrice, double actualPrice, boolean archive) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID zlecenia musi być większe od zera");
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
        this.basePrice = basePrice;
        this.actualPrice = actualPrice;
        this.archive = archive;
    }

    public long getID() {
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

    public double getActualPrice() {
        return actualPrice;
    }

    public double getFinalPrice() {
        return actualPrice;
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
        return "ID: " + id + ", Klient: " + client.getFirstName() + " " + client.getLastName() + ", Pracownik: " + employee.getFirstName() + " " + employee.getLastName() + ", Usługa: " + offering.getName() + ", Urządzenie: " + device.getBrand() + " " + device.getModel() + ", Cena bazowa: " + basePrice + ", Cena aktualna: " + actualPrice + ", Opis usterki: " + description + ", Status: " + (archive ? "zakończona" : "aktywna");
    }
}