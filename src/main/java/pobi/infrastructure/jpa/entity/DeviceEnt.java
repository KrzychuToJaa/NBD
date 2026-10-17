package pobi.infrastructure.jpa.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "devices")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "device_type")
@Access(AccessType.FIELD)
public abstract class DeviceEnt {

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE)
    @Column(
        name = "id",
        nullable = false,
        updatable = false,
        unique = true
    )
    private long id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "accessories", nullable = false)
    private String accessories;

    @Version
    @Column(name = "version")
    private Long version;

    protected DeviceEnt() {
    }

    public DeviceEnt(long id, String model, String brand, String accessories) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.accessories = accessories;
    }

    public long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getAccessories() {
        return accessories;
    }

    public Long getVersion() {
        return version;
    }
}
