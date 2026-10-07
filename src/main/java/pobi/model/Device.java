package pobi.model;

public abstract class Device {
    private String id;
    private String type;
    private String model;
    private String brand;
    private String accessories;
    private double basePrice;

    /**
     * Konstruktor obiektu klasy device
     * @param id ID urządzenia
     * @param type typ urządzenia
     * @param model nazwa modelu urządzenia
     * @param brand nazwa marki urządzenia
     * @param accessories akcesoria
     * @param basePrice cena bazowa
     */
    public Device(String id, String type, String model, String brand, String accessories, double basePrice) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.brand = brand;
        this.accessories = accessories;
        this.basePrice = basePrice;
    }
    
    /**
     * metoda pobierająca ID urządzenia
     * @return zwraca ID urządzenia
     */
    public String getID() {
        return id;
    }
    
    /**
     * metoda pobierająca typ urządzenia
     * @return zwraca typ urządzenia
     */
    public String getType() {
        return type;
    }
    
    /**
     * metoda pobierająca nazwę modelu urządzenia
     * @return zwraca nazwę modelu urządzenia
     */
    public String getModel() {
        return model;
    }
    
    /**
     * metoda pobierająca nazwę marki urządzenia
     * @return zwraca nazwę marki urządzenia
     */
    public String getBrand() {
        return brand;
    }
    
    /**
     * metoda pobierąca akcesoria urządzenia
     * @return zwraca akcesoria które zostawił klient z urządzeniem
     */
    public String getAccessories() {
        return accessories;
    }
    
    /**
     * metoda pobierająca cenę bazową urządzenia
     * @return zwraca cenę bazową urządzenia
     */
    public double getBasePrice() {
        return basePrice;
    }
    
    /**
     * metoda ustawiająca cenę bazową urządzenia
     * @param price cena którą chcemy ustawić
     */
    public void setBasePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Cena bazowa nie może być ujemna.");
        }
        this.basePrice = price;
    }

    /**
     * metoda pobierająca informacje o urządzeniu
     * @return zwraca informację o urządzeniu
     */
    public String getInfo() {
        return "ID: " + id + "\nTyp: " + type + "\nModel: " + model + "\nMarka: " + brand + 
               "\nAkcesoria: " + accessories + "\nCena bazowa: " + basePrice + "\n";
    }
    
    /**
     * metoda obliczająca faktyczną cenę
     * @param price cena którą przekazujemy do obliczeń
     * @return cena po obliczeniach
     */
    public abstract double getActualPrice(double price);
    
    /**
     * abstrakcyjna metoda getSpecialInfo
     * @return dodatkowe informacje specyficzne dla urządzenia
     */
    public abstract String getSpecialInfo();
}