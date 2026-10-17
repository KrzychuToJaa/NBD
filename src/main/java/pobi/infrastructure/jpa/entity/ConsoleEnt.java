package pobi.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import pobi.model.Console.ConsoleType;

@Entity
@DiscriminatorValue("CONSOLE")
public class ConsoleEnt extends DeviceEnt {

    @Enumerated(EnumType.STRING)
    @Column(name = "console_type")
    private ConsoleType consoleType;

    protected ConsoleEnt() {
        super();
    }

    public ConsoleEnt(long id, String model, String brand, String accessories, ConsoleType consoleType) {
        super(id, model, brand, accessories);
        this.consoleType = consoleType;
    }

    public ConsoleType getConsoleType() {
        return consoleType;
    }
}
