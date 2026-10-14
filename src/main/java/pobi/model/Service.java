package pobi.model;

public class Service {
    private final String id;
    private double basePrice;
    private boolean archive = false;
    private final Client client;
    private final Device device;
    private String description;

    /**
     * Konstruktor obiektu klasy Service
     * @param id ID usługi
     * @param basePrice podstawowa cena za usługę
     * @param client klient przypisywany do usługi
     * @param device urządzenie które klient oddaje do serwisu
     * @param description opis usługi lub usterki
     */
    public Service(String id, double basePrice, Client client, Device device, String description) {
        if (id == null || id.isBlank() || client == null || device == null) {
            throw new IllegalArgumentException("Wszystkie pola muszą być wypełnione");
        }
        if (!Double.isFinite(basePrice) || basePrice < 0) {
            throw new IllegalArgumentException("Cena bazowa nie może być ujemna");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Opis usługi nie może być pusty.");
        }
        this.id = id;
        this.basePrice = basePrice;
        this.client = client;
        this.device = device;
        this.description = description;
    }

    /**
     * metoda pobierająca ID usługi
     * @return ID usługi
     */
    public String getID() {
        return id;
    }
   
    /**
     * metoda pobierająca podstawową cenę za usługę
     * @return podstawowa cena za usługę
     */
    public double getBasePrice() {
        return basePrice;
    }
    
    /**
     * metoda pobierająca obiekt klienta
     * @return zwraca obiekt klienta
     */
    public Client getClient() {
        return client;
    }
    
    /**
     * metoda pobierająca obiekt urządzenia
     * @return zwraca obiekt urządzenia
     */
    public Device getDevice() {
        return device;
    }
    
    /**
     * metoda pobierająca opis usterki urządzenia
     * @return opis usterki urządzenia
     */
    public String getFaultDescription() {
        return description;
    }
    
    /**
     * metoda ustawiająca opis usterki
     * @param description usterka urządzenia
     */
    public void setFaultDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Opis usługi nie może być pusty.");
        }
        this.description = description;
    }

    /**
     * metoda zwracająca informacje o tym czy usługa jest zarchiwizowana
     * @return informacja o zarchiwizowaniu urządzenia
     */
    public boolean isArchive() {
        return archive;
    }

    public boolean isActive() {
        return !isArchive();
    }

    
    /**
     * metoda ustawiająca informacje o zarchiwizowaniu urządzenia
     * @param archive true/false odnośnie archiwizacji
     */
    public void setArchive(boolean archive) {
        this.archive = archive;
    }
    
    /**
     * metoda pobierająca informacje o usłudze
     * @return informacje o usłudze, kliencie i urządzeniu
     */
    public String getInfo() {
        return "ID: " + id + "\n" + 
               "Cena bazowa: " + basePrice + "\n" + 
               "Cena końcowa: " + device.getActualPrice(basePrice) + "\n" + 
               "Archiwalne: " + (archive ? "Tak" : "Nie") + "\n" + 
               "Opis usługi: " + description + "\n" + 
               "Informacje o kliencie:\n" + client.getInfo() + 
               "Informacje o urządzeniu:\n" + device.getInfo();
    }
}
