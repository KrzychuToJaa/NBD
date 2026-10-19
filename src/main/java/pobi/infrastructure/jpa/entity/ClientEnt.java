package pobi.infrastructure.jpa.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "clients")
public class ClientEnt {

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE)
    @Column(name = "client_id", nullable = false, updatable = false)
    private long clientId;

    @Column(name = "phone_number", length = 9, nullable = false, unique = true)
    private String phoneNumber;

    @Column(
        name = "first_name",
        nullable = false,
        columnDefinition = "text"
    )
    private String firstName;

    @Column(
        name = "last_name",
        nullable = false,
        columnDefinition = "text"
    )
    private String lastName;

    @Column(name = "city", nullable = false, columnDefinition = "text")
    private String city;

    @Column(name = "street", nullable = false, columnDefinition = "text")
    private String street;

    @Column(name = "number", nullable = false, length = 3)
    private String number;

    @Column(name = "archived", nullable = false)
    private boolean archive;

    @OneToMany(mappedBy = "client")
    private List<ServiceEnt> services = new ArrayList<>();

    //musiało być bo JPA się wywalał
    protected ClientEnt() {
    }

    public ClientEnt(long clientId, String firstName, String lastName, String phoneNumber, String city, String street, String number, boolean archive)
    {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.number = number;
        this.archive = archive;
    }
    public void changePhoneNumber(String phoneNumber) {
        if (archive) {
            throw new IllegalStateException(
                "Zarchiwizowanego klienta nie można modyfikować"
            );
        }

        this.phoneNumber = phoneNumber;
    }

    public void archive() {
        if (archive) {
            throw new IllegalStateException(
                "Klient jest już zarchiwizowany"
            );
        }

        archive = true;
    }
        

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getClientId() { return clientId; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public boolean isArchive() {
        return archive;
    }

    /** Utrzymuje obie strony relacji także dla kolekcji już załadowanej przez JPA. */
    public void addService(ServiceEnt service) {
        if (service == null || service.getClient() != this) {
            throw new IllegalArgumentException("Zlecenie wskazuje innego właściciela relacji");
        }
        if (!services.contains(service)) {
            services.add(service);
        }
    }
    public List<ServiceEnt> getServices() {
        return services;
    }

}