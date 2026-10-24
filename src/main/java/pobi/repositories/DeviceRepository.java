package pobi.repositories;

import pobi.model.Device;
import java.util.List;

public interface DeviceRepository {
    Device add(Device device);

    Device findById(long id);

    List<Device> findAll();

    void remove(Device device);

    int size();
}
