package pobi.model;

public class ServiceOffering {
    private final long id;
    private double basePrice;
    private final String name;

    public ServiceOffering(long id, double basePrice, String name) {
        if (id < 0 || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nie można utworzyć oferty z pustymi polami");
        }
        if (!Double.isFinite(basePrice) || basePrice < 0) {
            throw new IllegalArgumentException("Cena podstawowa nie może być ujemna");
        }
        this.id = id;
        this.basePrice = basePrice;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        if (!Double.isFinite(basePrice) || basePrice < 0) {
            throw new IllegalArgumentException("Cena podstawowa nie może być ujemna");
        }
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return "ID: " + id + ", Cena podstawowa: " + basePrice + ", Nazwa: " + name;
    }
}
