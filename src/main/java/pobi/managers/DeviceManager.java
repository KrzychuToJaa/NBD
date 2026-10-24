package pobi.managers;

import pobi.model.Device;
import pobi.repositories.DeviceRepository;
import java.util.List;
import java.util.Objects;

public class DeviceManager {
    private final DeviceRepository deviceRepository;

    public DeviceManager(DeviceRepository deviceRepository) {
        this.deviceRepository = Objects.requireNonNull(deviceRepository);
    }

    public Device registerDevice(String model, String brand, String accessories) {
        return deviceRepository.add(new Device(0, model, brand, accessories));
    }

    public Device getDevice(long id) {
        return deviceRepository.findById(id);
    }

    public void unregisterDevice(long id) {
        deviceRepository.remove(deviceRepository.findById(id));
    }

    public List<Device> findAllDevices() {
        return deviceRepository.findAll();
    }

    public DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }
}
