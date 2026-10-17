package pobi.infrastructure.jpa.entity;

import jakarta.persistence.*;

@Entity
@Access(AccessType.FIELD)
@Table(name = "services")
public class ServiceEnt {

    @Id
    @Column(name = "service_id", nullable = false, updatable = false)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEnt client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEnt employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceEnt device;

    @ManyToOne(optional = false)
    @JoinColumn(name = "offering_id", nullable = false)
    private ServiceOfferingEnt offering;

    @Column(name = "base_price", nullable = false)
    private double basePrice;

    @Column(name = "actual_price", nullable = false)
    private double actualPrice;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "archived", nullable = false)
    private boolean archive;

    @Version
    @Column(name = "version")
    private Long version;

    protected ServiceEnt() {
    }

    public ServiceEnt(long id,
                      ClientEnt client,
                      EmployeeEnt employee,
                      ServiceOfferingEnt offering,
                      DeviceEnt device,
                      double basePrice,
                      double actualPrice,
                      String description,
                      boolean archive) {
        this.id = id;
        this.client = client;
        this.employee = employee;
        this.offering = offering;
        this.device = device;
        this.basePrice = basePrice;
        this.actualPrice = actualPrice;
        this.description = description;
        this.archive = archive;
    }

    public long getId() {
        return id;
    }

    public ClientEnt getClient() {
        return client;
    }

    public EmployeeEnt getEmployee() {
        return employee;
    }

    public DeviceEnt getDevice() {
        return device;
    }

    public ServiceOfferingEnt getOffering() {
        return offering;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}