package pobi.infrastructure.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import pobi.infrastructure.jpa.adapter.JpaClientRepositoryAdapter;
import pobi.infrastructure.jpa.repository.JpaClientRepository;
import pobi.model.Client;
import pobi.repositories.ClientRepository;

public class DatabaseCheck {

    public static void main(String[] args) {
        String phone = "111222333";

        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("nbd")) {
            long clientId = saveExampleClient(factory, phone);

            try (EntityManager entityManager = factory.createEntityManager()) {
                ClientRepository repository = new JpaClientRepositoryAdapter(
                    new JpaClientRepository(entityManager)
                );

                Client client = repository.findById(clientId);

                System.out.println("\nKlient odczytany po ID:");
                System.out.println(client.getInfo());

                Client foundByPhone = repository.findByPhoneNumber(phone);

                System.out.println("ID znalezione po telefonie: " + foundByPhone.getClientId());
                System.out.println("Liczba klientów w bazie: " + repository.size());
            }
        }
    }

    private static long saveExampleClient(EntityManagerFactory factory, String phone) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            JpaClientRepository jpaRepository = new JpaClientRepository(entityManager);
            ClientRepository repository = new JpaClientRepositoryAdapter(jpaRepository);

            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();

                long clientId;

                if (jpaRepository.phoneNumberExists(phone, 0)) {
                    Client existing = repository.findByPhoneNumber(phone);
                    clientId = existing.getClientId();
                } else {
                    Client client = new Client(
                        0,
                        "Maciej",
                        "Mamrot",
                        phone,
                        "Łęczno",
                        "Łysa",
                        "12"
                    );

                    Client saved = repository.add(client);
                    clientId = saved.getClientId();
                }

                transaction.commit();
                return clientId;

            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw e;
            }
        }
    }
}
