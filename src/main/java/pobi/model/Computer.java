package pobi.model;

public abstract class Computer extends Device {
    private String cpuManufacturer;

    /**
     * Konstruktor obiektu klasy computer
     * @param id ID komputera
     * @param type typ urządzenia
     * @param model nazwa modelu komputera
     * @param brand nazwa producenta komputera
     * @param accessories akcesoria zostawione wraz z urządzeniem w serwisie
     * @param basePrice cena podstawowa
     * @param cpuManufacturer nazwa producenta jednostki przetwarzającej
     */
    public Computer(String id, String type, String model, String brand, String accessories, double basePrice, String cpuManufacturer) {
        super(id, type, model, brand, accessories, basePrice);
        this.cpuManufacturer = cpuManufacturer;
    }

    /**
     * Metoda zwracająca nazwę producenta jednostki przetwarzającej
     * @return nazwa producenta
     */
    public String getCpuManufacturer() {
        return cpuManufacturer;
    }

    /**
     * Metoda zwracająca informacje o komputerze
     * @return informacje o komputerze
     */
    @Override
    public String getInfo() {
        return super.getInfo() + "CPU Manufacturer: " + cpuManufacturer + "\n";
    }
}