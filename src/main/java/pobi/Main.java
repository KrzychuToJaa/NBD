package pobi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pobi.model.UserInterface;
import pobi.repositories.LogicContainer;

public class Main {
    public static void main(String[] args) {
        // Zasoby zamykają się także wtedy, gdy inicjalizacja lub menu zgłosi wyjątek.
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("nbd");
             EntityManager entityManager = factory.createEntityManager()) {
            LogicContainer container = new LogicContainer(entityManager);
            container.fillData();

            UserInterface ui = new UserInterface(container.getClientManager(), container.getDeviceManager(), container.getEmployeeManager(), container.getOfferingManager(), container.getServiceManager());
            ui.start();
        }
    }
}
