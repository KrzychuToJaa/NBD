package pobi;

import pobi.managers.ClientManager;
import pobi.managers.DeviceManager;
import pobi.managers.ServiceManager;
import pobi.model.UserInterface;

public class Main {
    public static void main(String[] args) {
        ClientManager clientManager = new ClientManager();
        ServiceManager serviceManager = new ServiceManager();
        DeviceManager deviceManager = new DeviceManager();

        UserInterface ui = new UserInterface(clientManager, serviceManager, deviceManager);
        ui.start();
    }
}