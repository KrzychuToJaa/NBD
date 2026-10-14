package pobi.model;


public class Client {
    private String firstName;
    private String lastName;
    private final String phoneNumber;
    private String city;
    private String street;
    private String number;
    private boolean archive = false;

    /**
     * Konstruktor klasy Client
     * @param firstName imię klienta
     * @param lastName nazwisko klienta
     * @param phoneNumber numer telefonu klienta
     * @param city miasto klienta
     * @param street ulica klienta
     * @param number numer domu klienta
     */
    public Client(String firstName, String lastName, String phoneNumber, String city, String street, String number) {
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank() ||
            phoneNumber == null || phoneNumber.isBlank() || city == null || city.isBlank() ||
            street == null || street.isBlank() || number == null || number.isBlank()) {
            throw new IllegalArgumentException("Nie można utworzyć klienta z pustymi polami");
        }
        if (phoneNumber == null || !phoneNumber.matches("[0-9]{9}")) {
            throw new IllegalArgumentException("Numer telefonu musi składać się z dokładnie 9 cyfr");
        }
        if (number.length() < 1 || number.length() > 3) {
            throw new IllegalArgumentException("Numer domu musi mieć max 3 znaki");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.number = number;
    }
    
    /**
     * Pobiera imię klienta
     * @return imię klienta
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Pobiera nazwisko klienta
     * @return nazwisko klienta
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Pobiera numer telefonu klienta
     * @return numer telefonu klienta
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Pobiera miasto klienta
     * @return miasto klienta
     */
    public String getCity() {
        return city;
    }

    /**
     * Pobiera ulicę klienta
     * @return ulica klienta
     */
    public String getStreet() {
        return street;
    }

    /**
     * Pobiera numer domu klienta
     * @return numer domu klienta
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sprawdza, czy klient jest zarchiwizowany
     * @return true jeśli klient jest zarchiwizowany, false w przeciwnym przypadku 
     */
    public boolean isArchive() {
        return archive;
    }

    /**
     * Ustawia informację o tym, czy klient jest zarchiwizowany
     * @param archive informacja o tym, czy klient będzie zarchiwizowany
     */
    public void setArchive(boolean archive) {
        this.archive = archive;
    }


    /**
     * Pobiera informacje o kliencie
     * @return informacje o kliencie
     */
    public String getInfo() {
        return "Imie: " + firstName + "\nNazwisko: " + lastName + "\nNumer telefonu: " + phoneNumber + 
               "\nMiasto: " + city + "\nUlica: " + street + "\nNumer: " + number + "\n" + 
               "Archiwalny: " + (archive ? "Tak" : "Nie") + "\n";
    }
}
