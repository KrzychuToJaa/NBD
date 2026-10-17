package pobi.infrastructure.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import pobi.infrastructure.jpa.entity.ClientEnt;
import pobi.infrastructure.jpa.mapper.ClientMapper;
import pobi.model.Client;

public class DatabaseCheck {

    public static void main(String[] args) {
        String phone = "111222333";

        try (EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("nbd")) {

            saveExampleClient(factory, phone);

            try (EntityManager entityManager = factory.createEntityManager()) {

                ClientEnt entity = entityManager.createQuery("select c from ClientEnt c where c.phoneNumber = :phone", ClientEnt.class)
                    .setParameter("phone", phone).getSingleResult();

                if (entity == null) {
                    throw new IllegalStateException("Nie odczytano zapisanego klienta" );
                }

                Client client = ClientMapper.toDomain(entity);

                System.out.println("\nKlient odczytany z bazy:");
                System.out.println(client.getInfo());
            }
        }
    }

    private static void saveExampleClient(
            EntityManagerFactory factory,
            String phone) {

        try (EntityManager entityManager = factory.createEntityManager()) {

            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();

                ClientEnt existing = entityManager.createQuery(
                    "select c from ClientEnt c where c.phoneNumber = :phone", ClientEnt.class)
                    .setParameter("phone", phone).getResultStream().findFirst().orElse(null);

                if (existing == null) {
                    Client client = new Client(
                        0,
                        "Maciej",
                        "Mamrot",
                        phone,
                        "Łęczno",
                        "Łysa",
                        "12"
                    );

                    ClientEnt entity = ClientMapper.toEntity(client);

                    entityManager.persist(entity);
                }

                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw e;
            }
        }
    }
}