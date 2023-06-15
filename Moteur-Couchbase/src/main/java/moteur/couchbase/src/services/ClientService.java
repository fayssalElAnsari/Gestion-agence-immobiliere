package moteur.couchbase.src.services;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientService {
    private final Cluster cluster;
    private final Collection clientCollection;

    public ClientService(Cluster cluster, String database, Collection collection) {
        this.cluster = cluster;
        this.clientCollection = collection;
    }

    // Methodes CRUD

    // Creation
    public void createClient(JsonObject client) {
        String id = client.getString("_id");
        clientCollection.insert(id, client);
    }

    public void createClients(List<JsonObject> clients) {
        clients.forEach(this::createClient);
    }

    // Lecture
    public JsonObject getClient(String id) {
        try {
            GetResult result = clientCollection.get(id);
            return result.contentAsObject();
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    public List<JsonObject> getAllClients() {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Clients` WHERE type = 'Client'", clientCollection.name());
        QueryResult result = cluster.query(statement);

        return result.rowsAsObject();
    }


    // Mise à jour
    public void updateClient(JsonObject client) {
        String id = client.getString("_id");
        clientCollection.replace(id, client);
    }

    public void updateClients(List<JsonObject> clients) {
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

    public List<JsonObject> getClientsByCountry(String country) {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Clients` WHERE countryOfOrigin = $country", clientCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("country", country))
        );

        return result.rowsAsObject();
    }

    public List<JsonObject> getClientsNotVerified() {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Clients` WHERE isVerified = false", clientCollection.name());
        QueryResult result = cluster.query(statement);

        return result.rowsAsObject();
    }

}
