package pobi.model;

public abstract class Device {
    private final String id;
    private String type;
    private String model;
    private String brand;
    private String accessories;

    /**
     * Konstruktor obiektu klasy device
     * @param id ID urządzenia
     * @param type typ urządzenia
     * @param model nazwa modelu urządzenia
     * @param brand nazwa marki urządzenia
     * @param accessories akcesoria
     */
    public Device(String id, String type, String model, String brand, String accessories) {
        if (id == null || id.isBlank() || type == null || type.isBlank()
                || model == null || model.isBlank() || brand == null || brand.isBlank()
                || accessories == null || accessories.isBlank()) {
            throw new IllegalArgumentException("Wszystkie pola urządzenia muszą być wypełnione.");
        }
        this.id = id;
        this.type = type;
        this.model = model;
        this.brand = brand;
        this.accessories = accessories;
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
     * metoda pobierająca informacje o urządzeniu
     * @return zwraca informację o urządzeniu
     */
    public String getInfo() {
        return "ID: " + id + "\nTyp: " + type + "\nModel: " + model + "\nMarka: " + brand + 
               "\nAkcesoria: " + accessories + "\n";
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