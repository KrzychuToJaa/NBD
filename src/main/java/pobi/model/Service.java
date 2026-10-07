package pobi.model;

public class Service {
    private String id;
    private double basePrice;
    private boolean archive = false;
    private Client client;
    private Device device;
    private String faultDescription;

    /**
     * Konstruktor obiektu klasy Service
     * @param id ID usługi
     * @param basePrice podstawowa cena za usługę
     * @param client klient przypisywany do usługi
     * @param device urządzenie które klient oddaje do serwisu
     * @param faultDescription opis usterki urządzenia
     */
    public Service(String id, double basePrice, Client client, Device device, String faultDescription) {
        if (id == null || id.isEmpty() || client == null || device == null || faultDescription == null || faultDescription.isEmpty()) {
            throw new IllegalArgumentException("Wszystkie pola muszą być wypełnione");
        }
        if (basePrice < 0) {
            throw new IllegalArgumentException("Cena bazowa nie może być ujemna");
        }
        this.id = id;
        this.basePrice = basePrice;
        this.client = client;
        this.device = device;
        this.faultDescription = faultDescription;
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
        return faultDescription;
    }
    
    /**
     * metoda ustawiająca opis usterki
     * @param description usterka urządzenia
     */
    public void setFaultDescription(String description) {
        this.faultDescription = description;
    }

    /**
     * metoda zwracająca informacje o tym czy usługa jest zarchiwizowana
     * @return informacja o zarchiwizowaniu urządzenia
     */
    public boolean isArchive() {
        return archive;
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
               "Opis awarii: " + faultDescription + "\n" + 
               "Informacje o kliencie:\n" + client.getInfo() + 
               "Informacje o urządzeniu:\n" + device.getInfo();
    }
}