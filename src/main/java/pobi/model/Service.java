package pobi.model;

public class Service {
    private final long id;
    private final Client client;
    private final Employee employee;
    private final ServiceOffering offering;
    private final Device device;
    private final double basePrice;
    private final double finalPrice;
    private String description;
    private boolean archive = false;

    public Service(long id, Client client, Employee employee, ServiceOffering offering, Device device, String description) {
        this(id, client, employee, offering, device, description, offering != null ? offering.getBasePrice() : 0.0, offering != null ? offering.getBasePrice() : 0.0, false);
    }

    public Service(long id, Client client, Employee employee, ServiceOffering offering, Device device, String description, double basePrice, double finalPrice, boolean archive) {

        if (id < 0) {
            throw new IllegalArgumentException("ID zlecenia nie może być ujemne");
        }

        if (client == null || employee == null
                || offering == null || device == null) {
            throw new IllegalArgumentException("Zlecenie wymaga klienta, pracownika, rodzaju usługi i urządzenia");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Opis zlecenia nie może być pusty");
        }

        if (!Double.isFinite(basePrice) || basePrice < 0
                || !Double.isFinite(finalPrice) || finalPrice < 0) {
            throw new IllegalArgumentException("Ceny muszą być skończone i nieujemne");
        }
        if (!archive && client.isArchive()) {
            throw new IllegalStateException("Nie można przyjąć zlecenia dla zarchiwizowanego klienta");
        }
        this.id = id;
        this.client = client;
        this.employee = employee;
        this.offering = offering;
        this.device = device;
        this.description = description;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice;
        this.archive = archive;
    }

    public long getId() {
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
        return finalPrice;
    }

    public String getFaultDescription() {
        return description;
    }

    public void setFaultDescription(String description) {
        requireActive();
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Opis zlecenia nie może być pusty");
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
        requireActive();
        archive = true;
    }

    private void requireActive() {
        if (archive) {
            throw new IllegalStateException("Zakończonego zlecenia nie można zmieniać ani ponownie zakończyć");
        }
    }

    public String getInfo() {
        return "ID: " + id + ", Klient: " + client.getFirstName() + " " + client.getLastName() + ", Pracownik: " + employee.getFirstName() + " " + employee.getLastName() + ", Usługa: " + offering.getName() + ", Urządzenie: " + device.getBrand() + " " + device.getModel() + ", Cena bazowa: " + basePrice + ", Cena końcowa: " + finalPrice + ", Opis usterki: " + description + ", Status: " + (archive ? "zakończona" : "aktywna");
    }
}
