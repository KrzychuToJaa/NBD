package pobi.model;

public class Laptop extends Computer {

    /**
     * Konstruktor obiektów klasy Laptop
     * @param id ID laptopa
     * @param type typ urządzenia
     * @param model nazwa modelu laptopa
     * @param brand nazwa producena laptopa
     * @param accessories akcesoria które zostały z laptopem w serwisie
     * @param basePrice bazowa cena 
     * @param cpuManufacturer producent jednostki przetwarzającej
     */
    public Laptop(String id, String type, String model, String brand, String accessories, double basePrice, String cpuManufacturer) {
        super(id, type, model, brand, accessories, basePrice, cpuManufacturer);
        if (id == null || id.isEmpty() || type == null || type.isEmpty() || 
            model == null || model.isEmpty() || brand == null || brand.isEmpty() || 
            accessories == null || accessories.isEmpty() || cpuManufacturer == null || cpuManufacturer.isEmpty()) {
            throw new IllegalArgumentException("Wszystkie pola muszą być wypełnione");
        }
        if (basePrice < 0) {
            throw new IllegalArgumentException("Cena bazowa nie może być ujemna");
        }
    }
    
    /**
     * metoda pobierająca informacje o laptopie
     * @return informacje o laptopie
     */
    @Override
    public String getInfo() {
        return super.getInfo();
    }
    
    /**
     * metoda obliczająca faktyczną cenę, cena zależy od producenta jednostki przetwarzającej
     * @param price cena którą przekazujemy do obliczeń
     * @return faktyczna kwota po obliczeniach
     */
    @Override
    public double getActualPrice(double price) {
        if ("Intel".equals(getCpuManufacturer())) {
            price *= 1.5;
        }
        return price;
    }
    
    /**
     * pobiera nazwę producenta procesora
     * @return nazwa producenta procesora
     */
    @Override
    public String getSpecialInfo() {
        return getCpuManufacturer();
    }
}