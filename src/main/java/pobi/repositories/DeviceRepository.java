package pobi.repositories;

import pobi.model.Device;
import pobi.model.Console;
import pobi.model.PC;
import pobi.model.Laptop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class DeviceRepository {
    private List<Device> devices;

    public DeviceRepository() {
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
        if (devices.isEmpty()) {
            throw new IllegalArgumentException("Brak zarejestrowanych urządzeń");
        }
        return devices;
    }

    /**
     * Zapisuje dane urządzeń do pliku
     * @param filename nazwa pliku
     */
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Device device : devices) {
                writer.write(device.getID() + "," + device.getType() + "," + device.getModel() + "," +
                             device.getBrand() + "," + device.getAccessories() + "," + 
                             device.getBasePrice() + "," + device.getSpecialInfo());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wczytuje dane urządzeń z pliku
     * @param filename nazwa pliku
     */
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            this.devices.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String id = parts[0];
                    String type = parts[1];
                    String model = parts[2];
                    String brand = parts[3];
                    String accessories = parts[4];
                    double basePrice = Double.parseDouble(parts[5]);
                    String specialInfo = parts[6];

                    if (type.equals("PC") && parts.length >= 8) {
                        boolean waterCooling = parts[7].equals("yes");
                        Device pc = new PC(id, type, model, brand, accessories, basePrice, specialInfo, waterCooling);
                        add(pc);
                    } else if (type.equals("Laptop")) {
                        Device laptop = new Laptop(id, type, model, brand, accessories, basePrice, specialInfo);
                        add(laptop);
                    } else if (type.equals("Console")) {
                        Console.ConsoleType consoleType;
                        if (specialInfo.equals("Nintendo")) {
                            consoleType = Console.ConsoleType.Nintendo;
                        } else if (specialInfo.equals("PlayStation")) {
                            consoleType = Console.ConsoleType.PlayStation;
                        } else if (specialInfo.equals("Xbox")) {
                            consoleType = Console.ConsoleType.Xbox;
                        } else {
                            consoleType = Console.ConsoleType.Unknown;
                        }
                        Device console = new Console(id, type, model, brand, accessories, basePrice, consoleType);
                        add(console);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}