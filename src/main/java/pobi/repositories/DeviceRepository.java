package pobi.repositories;

import pobi.model.Device;
import java.util.List;

/** Brak wyniku: IllegalArgumentException. add nadaje ID obiektom z ID 0 i zwraca zapisany obiekt. */
public interface DeviceRepository {
    Device add(Device device);

    Device findById(long id);

    List<Device> findAll();

    void remove(Device device);

    int size();
}