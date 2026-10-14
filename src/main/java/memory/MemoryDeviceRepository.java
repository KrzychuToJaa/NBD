package memory;

import pobi.model.Device;

import java.util.ArrayList;
import java.util.List;

public class MemoryDeviceRepository {
    private List<Device> devices;

    public MemoryDeviceRepository() {
        this.devices = new ArrayList<>();
    }

    /**
     * Znajduje urządzenie po ID i zwraca je
     * @param id ID urządzenia
     * @return referencja na urządzenie
     */
    public Device findByID(String id) {
        for (Device device : devices) {
            if (device.getID().equals(id)) {
                return device;
            }
        }
        throw new IllegalArgumentException("Urządzenie o podanym ID nie zostało znalezione");
    }

    /**
     * Dodanie urządzenia do repozytorium
     * @param device urządzenie do dodania
     */
    public void add(Device device) {
        if (device == null) return;
        for (Device d : devices) {
            if (d.getID().equals(device.getID())) {
                throw new IllegalArgumentException("Urządzenie o podanym ID już istnieje");
            }
        }
        devices.add(device);
    }

    /**
     * Usunięcie urządzenia z repozytorium
     * @param device urządzenie do usunięcia
     */
    public void remove(Device device) {
        if (device != null) {
            devices.removeIf(d -> d.getID().equals(device.getID()));
        }
    }

    /**
     * Zwraca liczbę urządzeń w repozytorium
     * @return liczba urządzeń
     */
    public int size() {
        return devices.size();
    }

    /**
     * Zwraca listę wszystkich urządzeń
     * @return lista urządzeń
     */
    public List<Device> findAll() {
        return List.copyOf(devices);  
    }

}
