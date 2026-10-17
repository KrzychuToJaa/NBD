package pobi.infrastructure.jpa.mapper;

import pobi.infrastructure.jpa.entity.ConsoleEnt;
import pobi.infrastructure.jpa.entity.DeviceEnt;
import pobi.infrastructure.jpa.entity.LaptopEnt;
import pobi.infrastructure.jpa.entity.PCEnt;
import pobi.model.Console;
import pobi.model.Device;
import pobi.model.Laptop;
import pobi.model.PC;

public final class DeviceMapper {

    private DeviceMapper() {
    }

    public static DeviceEnt toEntity(Device device) {
        if (device == null) {
            return null;
        }

        if (device instanceof PC pc) {
            return new PCEnt(
                pc.getId(),
                pc.getModel(),
                pc.getBrand(),
                pc.getAccessories(),
                pc.getCpuManufacturer(),
                pc.hasWaterCooling()
            );
        } else if (device instanceof Laptop laptop) {
            return new LaptopEnt(
                laptop.getId(),
                laptop.getModel(),
                laptop.getBrand(),
                laptop.getAccessories(),
                laptop.getCpuManufacturer()
            );
        } else if (device instanceof Console console) {
            return new ConsoleEnt(
                console.getId(),
                console.getModel(),
                console.getBrand(),
                console.getAccessories(),
                console.getConsoleType()
            );
        }

        throw new IllegalArgumentException("Nieobsługiwany typ urządzenia: " + device.getClass().getName());
    }

    public static Device toDomain(DeviceEnt entity) {
        if (entity == null) {
            return null;
        }

        if (entity instanceof PCEnt pc) {
            return new PC(
                pc.getId(),
                pc.getModel(),
                pc.getBrand(),
                pc.getAccessories(),
                pc.getCpuManufacturer(),
                pc.isWaterCooling()
            );
        } else if (entity instanceof LaptopEnt laptop) {
            return new Laptop(
                laptop.getId(),
                laptop.getModel(),
                laptop.getBrand(),
                laptop.getAccessories(),
                laptop.getCpuManufacturer()
            );
        } else if (entity instanceof ConsoleEnt console) {
            return new Console(
                console.getId(),
                console.getModel(),
                console.getBrand(),
                console.getAccessories(),
                console.getConsoleType()
            );
        }

        throw new IllegalArgumentException("Nieobsługiwany typ encji urządzenia: " + entity.getClass().getName());
    }
}
