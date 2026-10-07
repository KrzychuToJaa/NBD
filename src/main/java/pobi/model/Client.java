package pobi.model;

public class Client {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String street;
    private String number;
    private int maxServices = 3;
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
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty() ||
            phoneNumber == null || phoneNumber.isEmpty() || city == null || city.isEmpty() ||
            street == null || street.isEmpty() || number == null || number.isEmpty()) {
            throw new IllegalArgumentException("Wszystkie pola muszą być wypełnione.");
        }
        if (phoneNumber.length() != 9) {
            throw new IllegalArgumentException("Numer telefonu musi mieć dokładnie 9 cyfr.");
        }
        if (number.length() < 1 || number.length() > 3) {
            throw new IllegalArgumentException("Numer domu musi mieć maksymalnie 3 znaki.");
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
     * Pobiera maksymalną liczbę usług, które klient może zarejestrować
     * @return maksymalna liczba usług
     */
    public int getMaxServices() {
        return maxServices;
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
     * Zmienia maksymalną liczbę usług, które klient może zarejestrować
     * @param maxServices nowa maksymalna liczba usług, które klient może zarejestrować
     */
    public void setMaxServices(int maxServices) {
        if (maxServices < 1) {
            throw new IllegalArgumentException("Maksymalna liczba usług musi być większa niż 0.");
        }
        this.maxServices = maxServices;
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