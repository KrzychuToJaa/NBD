package pobi.managers;

import pobi.model.Device;
import pobi.model.Console;
import pobi.model.PC;
import pobi.model.Laptop;

import java.util.List;
import java.util.Objects;

import pobi.repositories.DeviceRepository;

public class DeviceManager {
    private final DeviceRepository deviceRepository;

    public DeviceManager(DeviceRepository deviceRepository) {
        this.deviceRepository = Objects.requireNonNull(deviceRepository);
    }

    /**
     * Zapisuje nowe urządzenie do repozytorium (konsole), wraz z podanymi przez użytkownika danymi
     * @param id ID urządzenia
     * @param model model urządzenia
     * @param brand marka urządzenia
     * @param accessories akcesoria urządzenia
     * @param consoleType typ konsoli
     */
    public void registerConsole(long id, String model, String brand, String accessories, Console.ConsoleType consoleType) {
        Device console = new Console(id, model, brand, accessories, consoleType);
        deviceRepository.add(console);
    }
    
    /**
     * Zapisuje nowe urządzenie do repozytorium (PC), wraz z podanymi przez użytkownika danymi
     * @param id ID urządzenia
     * @param model model urządzenia
     * @param brand marka urządzenia
     * @param accessories akcesoria urządzenia
     * @param cpuManufacturer producent procesora
     * @param waterCooling czy posiada chłodzenie wodne
     */
    public void registerPC(long id, String model, String brand, String accessories, String cpuManufacturer, boolean waterCooling) {
        Device pc = new PC(id, model, brand, accessories, cpuManufacturer, waterCooling);
        deviceRepository.add(pc);
    }

    /**
     * Zapisuje nowe urządzenie do repozytorium (Laptop), wraz z podanymi przez użytkownika danymi
     * @param id ID urządzenia
     * @param model model urządzenia
     * @param brand marka urządzenia
     * @param accessories akcesoria urządzenia
     * @param cpuManufacturer producent procesora
     */
    public void registerLaptop(long id, String model, String brand, String accessories, String cpuManufacturer) {
        Device laptop = new Laptop(id, model, brand, accessories, cpuManufacturer);
        deviceRepository.add(laptop);
    }
    
    /**
     * Zwraca referencję do urządzenia o podanym ID
     * @param id ID urządzenia
     * @return Urządzenie o podanym ID
     */
    public Device getDevice(long id) {
        return deviceRepository.findByID(id);
    }

    /**
     * Usuwa urządzenie o podanym ID z repozytorium
     * @param id ID urządzenia
     */
    public void unregisterDevice(long id) {
        deviceRepository.remove(deviceRepository.findByID(id));
    }

    /**
     * Zwraca informacje o wszystkich urządzeniach
     * @return Lista urządzeń
     */
    public List<Device> findAllDevices() {
        return deviceRepository.findAll();
    }

    /**
     * Zwraca referencję do repozytorium urządzeń
     * @return Repozytorium urządzeń
     */
    public DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }
}
