package pobi.managers;

import pobi.model.Device;
import pobi.model.Console;
import pobi.model.PC;
import pobi.model.Laptop;
import pobi.repositories.DeviceRepository;
import java.util.List;

public class DeviceManager {
    private DeviceRepository deviceRepository;

    public DeviceManager() {
        this.deviceRepository = new DeviceRepository();
    }

    /**
     * Zapisuje nowe urządzenie do repozytorium (konsole), wraz z podanymi przez użytkownika danymi
     * @param id ID urządzenia
     * @param type typ urządzenia
     * @param model model urządzenia
     * @param brand marka urządzenia
     * @param accessories akcesoria urządzenia
     * @param basePrice cena podstawowa urządzenia
     * @param consoleType typ konsoli
     */
    public void registerConsole(String id, String type, String model, String brand, String accessories, double basePrice, Console.ConsoleType consoleType) {
        Device console = new Console(id, type, model, brand, accessories, basePrice, consoleType);
        deviceRepository.add(console);
    }
    
    /**
     * Zapisuje nowe urządzenie do repozytorium (PC), wraz z podanymi przez użytkownika danymi
     * @param id ID urządzenia
     * @param type typ urządzenia
     * @param model model urządzenia
     * @param brand marka urządzenia
     * @param accessories akcesoria urządzenia
     * @param basePrice cena podstawowa urządzenia
     * @param cpuManufacturer producent procesora
     * @param waterCooling czy posiada chłodzenie wodne
     */
    public void registerPC(String id, String type, String model, String brand, String accessories, double basePrice, String cpuManufacturer, boolean waterCooling) {
        Device pc = new PC(id, type, model, brand, accessories, basePrice, cpuManufacturer, waterCooling);
        deviceRepository.add(pc);
    }

    /**
     * Zapisuje nowe urządzenie do repozytorium (Laptop), wraz z podanymi przez użytkownika danymi
     * @param id ID urządzenia
     * @param type typ urządzenia
     * @param model model urządzenia
     * @param brand marka urządzenia
     * @param accessories akcesoria urządzenia
     * @param basePrice cena podstawowa urządzenia
     * @param cpuManufacturer producent procesora
     */
    public void registerLaptop(String id, String type, String model, String brand, String accessories, double basePrice, String cpuManufacturer) {
        Device laptop = new Laptop(id, type, model, brand, accessories, basePrice, cpuManufacturer);
        deviceRepository.add(laptop);
    }
    
    /**
     * Zwraca referencję do urządzenia o podanym ID
     * @param id ID urządzenia
     * @return Urządzenie o podanym ID
     */
    public Device getDevice(String id) {
        return deviceRepository.findByID(id);
    }

    /**
     * Usuwa urządzenie o podanym ID z repozytorium
     * @param id ID urządzenia
     */
    public void unregisterDevice(String id) {
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
     * Zapisuje dane urządzeń do pliku
     * @param filename nazwa pliku
     */
    public void saveToFile(String filename) {
        deviceRepository.saveToFile(filename);
    }

    /**
     * Wczytuje dane urządzeń z pliku
     * @param filename nazwa pliku
     */
    public void loadFromFile(String filename) {
        deviceRepository.loadFromFile(filename);
    }

    /**
     * Zwraca referencję do repozytorium urządzeń
     * @return Repozytorium urządzeń
     */
    public DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }
}