package pobi.model;

public class Console extends Device {
    
    public enum ConsoleType {
        Nintendo(1),
        PlayStation(2),
        Xbox(3),
        Unknown(4);

        private final int value;

        ConsoleType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private ConsoleType consoleType;

    /**
     * Konstruktor obiektu klasy Console
     * @param id ID konsoli
     * @param type typ urządzenia
     * @param model nazwa modelu konsoli
     * @param brand nazwa producenta konsoli
     * @param accessories akcesoria które zostały w serwisie z urządzeniem
     * @param basePrice cena bazowa
     * @param consoleType typ konsoli
     */
    public Console(String id, String type, String model, String brand, String accessories, double basePrice, ConsoleType consoleType) {
        super(id, type, model, brand, accessories, basePrice);
        if (id == null || id.isEmpty() || type == null || type.isEmpty() || 
            model == null || model.isEmpty() || brand == null || brand.isEmpty() || 
            accessories == null || accessories.isEmpty()) {
            throw new IllegalArgumentException("Wszystkie pola muszą być wypełnione");
        }
        if (basePrice < 0) {
            throw new IllegalArgumentException("Cena bazowa nie może być ujemna");
        }
        this.consoleType = consoleType;
    }

    /**
     * pobiera typ konsoli
     * @return typ konsoli
     */
    public ConsoleType getConsoleType() {
        return consoleType;
    }

    /**
     * pobiera nazwę typu konsoli
     * @return nazwa typu konsoli
     */
    public String getConsoleTypeName() {
        switch (consoleType) {
            case Nintendo:
                return "Nintendo";
            case PlayStation:
                return "PlayStation";
            case Xbox:
                return "Xbox";
            case Unknown:
                return "Unknown";
            default:
                return "Other";
        }
    }
    
    /**
     * pobiera informacje o konsoli
     * @return informacje o konsoli
     */
    @Override
    public String getInfo() {
        return super.getInfo() + "Typ konsoli: " + getConsoleTypeName() + "\n";
    }
    
    /**
     * pobiera cenę po obliczeniach, cena zależy od producenta konsoli
     * @param price cena którą przekazujemy do obliczeń
     * @return cena po obliczeniach
     */
    @Override
    public double getActualPrice(double price) {
        switch (consoleType) {
            case PlayStation:
                return price * 1.5;
            case Xbox:
                return price * 2.0;
            case Nintendo:
                return price * 0.9;
            case Unknown:
            default:
                return price;
        }
    }
    
    /**
     * pobiera informację o nazwie typu konsoli
     * @return zwraca nazwę typu konsoli
     */
    @Override
    public String getSpecialInfo() {
        return getConsoleTypeName();
    }
}