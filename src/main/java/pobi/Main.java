package pobi;

import pobi.model.UserInterface;
import pobi.repositories.LogicContainer;

public class Main {
    public static void main(String[] args) {
        LogicContainer container = new LogicContainer();
        container.fillData();
        UserInterface ui = new UserInterface(container.getClientManager(), container.getDeviceManager(), container.getEmployeeManager(), container.getOfferingManager(), container.getServiceManager());
        ui.start();
    }
}
