package memory;

import pobi.model.Device;
import pobi.repositories.DeviceRepository;
import java.util.ArrayList;
import java.util.List;

public class MemoryDeviceRepository implements DeviceRepository {
    private final List<Device> devices = new ArrayList<>();
    private long nextId = 1;

    @Override
    public synchronized Device add(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Urządzenie nie może być null");
        }
        long id = device.getId() == 0 ? nextId : device.getId();
        if (devices.stream().anyMatch(existing -> existing.getId() == id)) {
            throw new IllegalArgumentException("Urządzenie o podanym ID już istnieje");
        }
        Device saved = device.getId() == 0 ? new Device(id, device.getModel(), device.getBrand(), device.getAccessories()) : device;
        devices.add(saved);
        nextId = Math.max(nextId, id + 1);
        return saved;
    }

    @Override
    public Device findById(long id) {
        return devices.stream().filter(device -> device.getId() == id).findFirst().orElseThrow(() -> new IllegalArgumentException("Urządzenie o podanym ID nie zostało znalezione"));
    }

    @Override
    public void remove(Device device) {
        if (device != null) {
            devices.removeIf(existing -> existing.getId() == device.getId());
        }
    }

    @Override
    public List<Device> findAll() {
        return List.copyOf(devices);
    }

    @Override
    public int size() {
        return devices.size();
    }
}
