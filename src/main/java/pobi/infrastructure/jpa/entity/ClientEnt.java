package pobi.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class ClientEnt {

    @Id
    @Column(
        name = "phone_number",
        length = 9,
        nullable = false,
        updatable = false
    )
    private long phoneNumber;

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

    //musiało być bo JPA się wywalał
    protected ClientEnt() {
    }

    public ClientEnt(String firstName, String lastName, long phoneNumber, String city, String street, String number, boolean archive)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.number = number;
        this.archive = archive;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getPhoneNumber() {
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

    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}