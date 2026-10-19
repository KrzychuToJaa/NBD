package pobi.infrastructure.jpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name = "employees")
public class EmployeeEnt {

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE)
    @Column(
        name = "employee_id",
        nullable = false,
        updatable = false
    )
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "employee")
    private List<ServiceEnt> services;

    protected EmployeeEnt() {
    }

    public EmployeeEnt(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<ServiceEnt> getServices() {
        return services;
    }
}