package pobi.infrastructure.jpa.adapter;

import pobi.infrastructure.jpa.entity.DeviceEnt;
import pobi.infrastructure.jpa.mapper.DeviceMapper;
import pobi.infrastructure.jpa.repository.JpaDeviceRepository;
import pobi.model.Device;
import pobi.repositories.DeviceRepository;

import java.util.List;
import java.util.Objects;

public class JpaDeviceRepositoryAdapter implements DeviceRepository {
    private final JpaDeviceRepository repository;

    public JpaDeviceRepositoryAdapter(JpaDeviceRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Device add(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Urządzenie nie może być null");
        }

        DeviceEnt entity = DeviceMapper.toEntity(device);
        DeviceEnt saved = repository.add(entity);

        return DeviceMapper.toDomain(saved);
    }

    @Override
    public Device findById(long id) {
        return DeviceMapper.toDomain(repository.findById(id));
    }

    @Override
    public List<Device> findAll() {
        return repository.findAll()
            .stream()
            .map(DeviceMapper::toDomain)
            .toList();
    }

    @Override
    public void remove(Device device) {
        if (device != null) {
            repository.remove(device.getId());
        }
    }

    @Override
    public int size() {
        return Math.toIntExact(repository.count());
    }
}