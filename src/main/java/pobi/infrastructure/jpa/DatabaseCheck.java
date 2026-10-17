package pobi.infrastructure.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import pobi.infrastructure.jpa.entity.ClientEnt;
import pobi.infrastructure.jpa.mapper.ClientMapper;
import pobi.infrastructure.jpa.repository.JpaClientRepository;
import pobi.model.Client;

public class DatabaseCheck {

    public static void main(String[] args) {
        String phone = "111222333";

        try (EntityManagerFactory factory =
                 Persistence.createEntityManagerFactory("nbd")) {

            long clientId = saveExampleClient(factory, phone);

            try (EntityManager entityManager = factory.createEntityManager()) {
                JpaClientRepository repository = new JpaClientRepository(entityManager);

                ClientEnt entity = repository.findById(clientId);
                Client client = ClientMapper.toDomain(entity);

                System.out.println("\nKlient odczytany po ID:");
                System.out.println(client.getInfo());

                ClientEnt foundByPhone = repository.findByPhoneNumber(phone);

                System.out.println("ID znalezione po telefonie: " + foundByPhone.getClientId());

                System.out.println("Liczba klientów w bazie: " + repository.count());
            }
        }
    }

    private static long saveExampleClient(EntityManagerFactory factory,String phone) {

        try (EntityManager entityManager = factory.createEntityManager()) {
            JpaClientRepository repository = new JpaClientRepository(entityManager);

            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();

                ClientEnt entity = entityManager.createQuery("select c from ClientEnt c where c.phoneNumber = :phone", ClientEnt.class)
                    .setParameter("phone", phone)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

                if (entity == null) {
                    Client client = new Client(
                        0,
                        "Maciej",
                        "Mamrot",
                        phone,
                        "Łęczno",
                        "Łysa",
                        "12"
                    );

                    entity = repository.add(
                        ClientMapper.toEntity(client)
                    );
                }

                transaction.commit();
                return entity.getClientId();

            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw e;
            }
        }
    }
}