package pobi.model;

public abstract class Computer extends Device {
    private String cpuManufacturer;

    /**
     * Konstruktor obiektu klasy computer
     * @param id ID komputera
     * @param model nazwa modelu komputera
     * @param brand nazwa producenta komputera
     * @param accessories akcesoria zostawione wraz z urządzeniem w serwisie
     * @param cpuManufacturer nazwa producenta jednostki przetwarzającej
     */
    public Computer(long id, String model, String brand, String accessories, String cpuManufacturer) {
        super(id, model, brand, accessories);
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