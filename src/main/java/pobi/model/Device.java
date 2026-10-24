package pobi.model;

public class Device {
    private final long id;
    private final String model;
    private final String brand;
    private final String accessories;

    public Device(long id, String model, String brand, String accessories) {
        if (id < 0 || model == null || model.isBlank() || brand == null || brand.isBlank() || accessories == null || accessories.isBlank()) {
            throw new IllegalArgumentException("Wszystkie pola urządzenia muszą być wypełnione, a ID nie może być ujemne");
        }
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.accessories = accessories;
    }

    public long getId() { return id; }
    public String getModel() { return model; }
    public String getBrand() { return brand; }
    public String getAccessories() { return accessories; }

    public String getInfo() {
        return "ID: " + id + "\nModel: " + model + "\nMarka: " + brand + "\nAkcesoria: " + accessories + "\n";
    }
}
