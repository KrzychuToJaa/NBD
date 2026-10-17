package memory;

import pobi.repositories.DeviceRepository;

import pobi.model.Device;

import java.util.ArrayList;
import java.util.List;

public class MemoryDeviceRepository implements DeviceRepository {
    private List<Device> devices;

    public MemoryDeviceRepository() {
        this.devices = new ArrayList<>();
    }

    /**
     * Znajduje urządzenie po ID i zwraca je
     * @param id ID urządzenia
     * @return referencja na urządzenie
     */
    @Override
    public Device findByID(long id) {
        for (Device device : devices) {
            if (device.getID() == id) {
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
    public void add(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Urządzenie nie może być null.");
        }
        for (Device d : devices) {
            if (d.getID() == device.getID()) {
                throw new IllegalArgumentException("Urządzenie o podanym ID już istnieje");
            }
        }
        devices.add(device);
    }

    /**
     * Usunięcie urządzenia z repozytorium
     * @param device urządzenie do usunięcia
     */
    @Override
    public void remove(Device device) {
        if (device != null) {
            devices.removeIf(d -> d.getID() == device.getID());
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
