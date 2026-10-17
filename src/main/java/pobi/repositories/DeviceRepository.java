package pobi.repositories;

import pobi.model.Device;
import java.util.List;

public interface DeviceRepository {
    void add(Device device);

    Device findByID(long id);

    List<Device> findAll();

    void remove(Device device);

    int size();
}