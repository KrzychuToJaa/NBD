package pobi.model;

public class ServiceOffering {
    private final String id;
    private final double basePrice;
    private final String name;

    public ServiceOffering(String id, double basePrice, String name) {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nie można utworzyć oferty z pustymi polami");
        }
        if (basePrice < 0) {
            throw new IllegalArgumentException("Cena podstawowa nie może być ujemna");
        }
        this.id = id;
        this.basePrice = basePrice;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return "ID: " + id + ", Cena podstawowa: " + basePrice + ", Nazwa: " + name;
    }
}
