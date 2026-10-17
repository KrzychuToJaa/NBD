package pobi.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PC")
public class PCEnt extends ComputerEnt {

    @Column(name = "water_cooling")
    private boolean waterCooling;
    public boolean isWaterCooling() {
        return waterCooling;
    }
    public PCEnt(long id, String model, String brand, String accessories,String cpuManufacturer,boolean waterCooling) {
        super(id, model, brand, accessories, cpuManufacturer);
        this.waterCooling = waterCooling;
    }
    protected PCEnt() {
        super();
    }

}
