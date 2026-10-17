package pobi.infrastructure.jpa.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LAPTOP")
public class LaptopEnt extends ComputerEnt {

    protected LaptopEnt() {
        super();
    }

    public LaptopEnt(long id, String model, String brand, String accessories, String cpuManufacturer) {
        super(id, model, brand, accessories, cpuManufacturer);
    }
}
