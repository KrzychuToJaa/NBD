package pobi.infrastructure.jpa.mapper;

import pobi.model.Client;
import pobi.infrastructure.jpa.entity.ClientEnt;

public final class ClientMapper {

    private ClientMapper() {
    }

    public static ClientEnt toEntity(Client client) {

        return new ClientEnt(
            client.getFirstName(),
            client.getLastName(),
            client.getPhoneNumber(),
            client.getCity(),
            client.getStreet(),
            client.getNumber(),
            client.isArchive()
        );
    }

    public static Client toDomain(ClientEnt entity) {

        Client client = new Client(
            entity.getFirstName(),
            entity.getLastName(),
            entity.getPhoneNumber(),
            entity.getCity(),
            entity.getStreet(),
            entity.getNumber()
        );

        client.setArchive(entity.isArchive());

        return client;
    }
}