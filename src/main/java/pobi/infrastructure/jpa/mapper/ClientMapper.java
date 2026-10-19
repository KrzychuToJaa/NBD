package pobi.infrastructure.jpa.mapper;

import pobi.model.Client;
import pobi.infrastructure.jpa.entity.ClientEnt;
import java.util.List;

public final class ClientMapper {
    private ClientMapper() {}

    public static ClientEnt toEntity(Client client) {
        return new ClientEnt(client.getClientId(), client.getFirstName(), client.getLastName(),
                client.getPhoneNumber(), client.getCity(), client.getStreet(), client.getNumber(), client.isArchive());
    }

    public static Client toDomainWithoutServices(ClientEnt entity) {
        if (entity == null) { return null; }
        return new Client(entity.getClientId(), entity.getFirstName(), entity.getLastName(),
                entity.getPhoneNumber(), entity.getCity(), entity.getStreet(), entity.getNumber(), entity.isArchive());
    }

    public static Client toDomain(ClientEnt entity) {
        return toDomain(entity, new DomainMappingContext());
    }

    public static List<Client> toDomainList(List<ClientEnt> entities) {
        DomainMappingContext context = new DomainMappingContext();
        return entities.stream().map(entity -> toDomain(entity, context)).toList();
    }

    static Client toDomain(ClientEnt entity, DomainMappingContext context) {
        if (entity == null) { return null; }
        Client client = context.client(entity);
        if (context.expandedClients.add(entity.getClientId())) {
            for (var service : entity.getServices()) {
                ServiceMapper.toDomain(service, context);
            }
        }
        return client;
    }
}