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
     * @param model model urządzenia
     * @param brand marka urządzenia
     * @param accessories akcesoria urządzenia
     * @param consoleType typ konsoli
     */
    public Device registerConsole(String model, String brand, String accessories, Console.ConsoleType consoleType) {
        Device console = new Console(0, model, brand, accessories, consoleType);
        return deviceRepository.add(console);
    }
    
    /**
     * Zapisuje nowe urządzenie do repozytorium (PC), wraz z podanymi przez użytkownika danymi
     * @param model model urządzenia
     * @param brand marka urządzenia
     * @param accessories akcesoria urządzenia
     * @param cpuManufacturer producent procesora
     * @param waterCooling czy posiada chłodzenie wodne
     */
    public Device registerPC(String model, String brand, String accessories, String cpuManufacturer, boolean waterCooling) {
        Device pc = new PC(0, model, brand, accessories, cpuManufacturer, waterCooling);
        return deviceRepository.add(pc);
    }

    /**
     * Zapisuje nowe urządzenie do repozytorium (Laptop), wraz z podanymi przez użytkownika danymi
     * @param model model urządzenia
     * @param brand marka urządzenia
     * @param accessories akcesoria urządzenia
     * @param cpuManufacturer producent procesora
     */
    public Device registerLaptop(String model, String brand, String accessories, String cpuManufacturer) {
        Device laptop = new Laptop(0, model, brand, accessories, cpuManufacturer);
        return deviceRepository.add(laptop);
    }
    
    /**
     * Zwraca referencję do urządzenia o podanym ID
     * @return Urządzenie o podanym ID
     */
    public Device getDevice(long id) {
        return deviceRepository.findById(id);
    }

    /**
     * Usuwa urządzenie o podanym ID z repozytorium
     */
    public void unregisterDevice(long id) {
        deviceRepository.remove(deviceRepository.findById(id));
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
