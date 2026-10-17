package pobi.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public abstract class ComputerEnt extends DeviceEnt {

    @Column(name = "cpu_manufacturer")
    private String cpuManufacturer;

    protected ComputerEnt() {
        super();
    }

    public ComputerEnt(long id, String model, String brand, String accessories, String cpuManufacturer) {
        super(id, model, brand, accessories);
        this.cpuManufacturer = cpuManufacturer;
    }

    public String getCpuManufacturer() {
        return cpuManufacturer;
    }
}
