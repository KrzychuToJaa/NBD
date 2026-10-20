package memory;

import pobi.repositories.DeviceRepository;

import pobi.model.Device;

import java.util.ArrayList;
import java.util.List;

public class MemoryDeviceRepository implements DeviceRepository {
    private List<Device> devices;
    private long nextId = 1;

    public MemoryDeviceRepository() {
        this.devices = new ArrayList<>();
    }

    private Device unsupportedDevice() {
        throw new IllegalArgumentException("Nieobsługiwany typ urządzenia");
    }

    /**
     * Znajduje urządzenie po ID i zwraca je
     * @param id ID urządzenia
     * @return referencja na urządzenie
     */
    @Override
    public Device findById(long id) {
        for (Device device : devices) {
            if (device.getId() == id) {
                return device;
            }
        }
        throw new IllegalArgumentException("Urządzenie o podanym ID nie zostało znalezione");
    }

    /**
     * Dodanie urządzenia do repozytorium
     * @param device urządzenie do dodania
     */
    @Override
    public synchronized Device add(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Urządzenie nie może być null.");
        }
        for (Device d : devices) {
            if (d.getId() == device.getId()) {
                throw new IllegalArgumentException("Urządzenie o podanym ID już istnieje");
            }
        }
        long id = device.getId() == 0 ? nextId : device.getId();
        Device saved = device.getId() == 0 ? device instanceof pobi.model.PC pc
                ? new pobi.model.PC(id, pc.getModel(), pc.getBrand(), pc.getAccessories(), pc.getCpuManufacturer(), pc.hasWaterCooling())
                : device instanceof pobi.model.Laptop laptop
                    ? new pobi.model.Laptop(id, laptop.getModel(), laptop.getBrand(), laptop.getAccessories(), laptop.getCpuManufacturer())
                    : device instanceof pobi.model.Console console
                        ? new pobi.model.Console(id, console.getModel(), console.getBrand(), console.getAccessories(), console.getConsoleType()) : unsupportedDevice() : device;
        devices.add(saved);
        nextId = Math.max(nextId, id + 1);
        return saved;
    }

    /**
     * Usunięcie urządzenia z repozytorium
     * @param device urządzenie do usunięcia
     */
    @Override
    public void remove(Device device) {
        if (device != null) {
            devices.removeIf(d -> d.getId() == device.getId());
        }
    }

    /**
     * Zwraca liczbę urządzeń w repozytorium
     * @return liczba urządzeń
     */
    @Override
    public int size() {
        return devices.size();
    }

    /**
     * Zwraca listę wszystkich urządzeń
     * @return lista urządzeń
     */
    @Override
    public List<Device> findAll() {
        return List.copyOf(devices);  
    }

}
