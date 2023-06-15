package moteur.couchbase.src.services;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import moteur.couchbase.src.models.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientService {
    private final Cluster cluster;
    private final Collection clientCollection;

    public ClientService(Cluster cluster, String database, String collection) {
        this.cluster = cluster;
        this.clientCollection = cluster.bucket(database).defaultCollection();
    }

    // Methodes CRUD

    // Creation
    public void createClient(Client client) {
        clientCollection.insert(client.getId(), client);
    }

    public void createClients(List<Client> clients) {
        clients.forEach(this::createClient);
    }

    // Lecture
    public Client getClient(String id) {
        try {
            GetResult result = clientCollection.get(id);
            return result.contentAs(Client.class);
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    // Mise à jour
    public void updateClient(Client client) {
        clientCollection.replace(client.getId(), client);
    }

    public void updateClients(List<Client> clients) {
        clients.forEach(this::updateClient);
    }

    // Suppression
    public void deleteClient(String id) {
        clientCollection.remove(id);
    }

    public void deleteClients(List<String> ids) {
        ids.forEach(this::deleteClient);
    }

    // Méthode de recherche
    public List<Client> getClientsByCountry(String country) {
        String query = "SELECT * FROM `database`.`collection` WHERE type = 'Client' AND country = $country";
        QueryResult result = cluster.query(query, QueryOptions.queryOptions().parameters(JsonObject.create().put("country", country)));
        return result.rows().stream()
                .map(row -> row.contentAs(Client.class))
                .collect(Collectors.toList());
    }

    public List<Client> getClientsNotVerified() {
        String query = "SELECT * FROM `database`.`collection` WHERE type = 'Client' AND isVerified = false";
        QueryResult result = cluster.query(query);
        return result.rows().stream()
                .map(row -> row.contentAs(Client.class))
                .collect(Collectors.toList());
    }
}
