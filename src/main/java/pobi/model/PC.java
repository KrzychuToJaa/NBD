package pobi.model;

public class PC extends Computer {
    private boolean waterCooling;

    /**
     * Konstruktor klasy PC
     * @param id ID komputera
     * @param type typ urządzenia
     * @param model nazwa modelu komputera
     * @param brand nazwa producenta komputera
     * @param accessories akcesoria które zostały z komputerem w serwisie
     * @param basePrice cena bazowa
     * @param cpuManufacturer nazwa producenta jednostki przetwarzającej
     * @param waterCooling czy jest chłodzenie wodne
     */
    public PC(String id, String type, String model, String brand, String accessories, double basePrice, String cpuManufacturer, boolean waterCooling) {
        super(id, type, model, brand, accessories, basePrice, cpuManufacturer);
        if (id == null || id.isEmpty() || type == null || type.isEmpty() || 
            model == null || model.isEmpty() || brand == null || brand.isEmpty() || 
            accessories == null || accessories.isEmpty() || cpuManufacturer == null || cpuManufacturer.isEmpty()) {
            throw new IllegalArgumentException("Wszystkie pola muszą być wypełnione");
        }
        if (basePrice < 0) {
            throw new IllegalArgumentException("Cena bazowa nie może być ujemna");
        }
        this.waterCooling = waterCooling;
    }
    
    /**
     * Zwraca informacje o komputerze rozszerzone o chłodzenie wodne
     * @return informacje o PC
     */
    @Override
    public String getInfo() {
        return super.getInfo() + getWaterCoolingInfo();
    }
    
    /**
     * Zwraca informację tekstową o obecności chłodzenia wodnego
     * @return informacja o chłodzeniu wodnym
     */
    public String getWaterCoolingInfo() {
        return "Chłodzenie wodne: " + (waterCooling ? "tak" : "nie") + "\n";
    }
    
    /**
     * metoda obliczająca faktyczną cenę, cena zależy od producenta jednostki przetwarzającej oraz od tego czy jest obecne chłodzenie wodne
     * @param price cena którą przekazujemy do obliczeń
     * @return faktyczna cena po obliczeniach
     */
    @Override
    public double getActualPrice(double price) {
        if ("Intel".equals(getCpuManufacturer())) {
            price *= 1.5;
        }
        if (waterCooling) {
            price += 200;
        }
        return price;
    }
    
    /**
     * metoda pobierająca informacje o producencie jednostki przetwarzającej oraz informacje o obecności chłodzenia wodnego 
     * @return informacje o jednostce i chłodzeniu
     */
    @Override
    public String getSpecialInfo() {
        return getCpuManufacturer() + "," + (waterCooling ? "tak" : "nie");
    }
}