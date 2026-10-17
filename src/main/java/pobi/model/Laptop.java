package pobi.model;

public class Laptop extends Computer {

    /**
     * Konstruktor obiektów klasy Laptop
     * @param id ID laptopa
     * @param model nazwa modelu laptopa
     * @param brand nazwa producena laptopa
     * @param accessories akcesoria które zostały z laptopem w serwisie
     * @param cpuManufacturer producent jednostki przetwarzającej
     */
    public Laptop(long id, String model, String brand, String accessories, String cpuManufacturer) {
        super(id, model, brand, accessories, cpuManufacturer);
        if (id <= 0 || 
            model == null || model.isBlank() || brand == null || brand.isBlank() || 
            accessories == null || accessories.isBlank() || cpuManufacturer == null || cpuManufacturer.isBlank()) {
            throw new IllegalArgumentException("Wszystkie pola muszą być wypełnione");
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