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

public class AppartementService {
    private final Cluster cluster;
    private final Collection appartementCollection;

    public AppartementService(Cluster cluster, String database, Collection collection) {
        this.cluster = cluster;
        this.appartementCollection = collection;
    }

    // Methodes CRUD

    // Creation
    public void createAppartement(JsonObject appartement) {
        String id = appartement.getString("_id");
        appartementCollection.insert(id, appartement);
    }

    public void createAppartements(List<JsonObject> appartements) {
        appartements.forEach(this::createAppartement);
    }

    // Lecture
    public List<JsonObject> getAllAppartement() {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Apartments` WHERE type = 'Client'", appartementCollection.name());
        QueryResult result = cluster.query(statement);

        return result.rowsAsObject();
    }

    public JsonObject getAppartement(String id) {
        try {
            GetResult result = appartementCollection.get(id);
            return result.contentAsObject();
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    // Mise à jour
    public void updateAppartement(JsonObject appartement) {
        String id = appartement.getString("_id");
        appartementCollection.replace(id, appartement);
    }

    public void updateAppartements(List<JsonObject> appartements) {
        appartements.forEach(this::updateAppartement);
    }

    // Suppression
    public void deleteAppartement(String id) {
        appartementCollection.remove(id);
    }

    public void deleteAppartements(List<String> ids) {
        ids.forEach(this::deleteAppartement);
    }

    // Methodes de recherche
    public List<JsonObject> findAppartementsByUserId(String userId) {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Apartments` WHERE ownerId = $userId");
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("userId", userId))
        );
        return result.rowsAsObject();
    }



    public List<JsonObject> findAppartementsByNumberOfRooms(int numberOfRooms) {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Apartments` WHERE numberOfRooms = $numberOfRooms", appartementCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("numberOfRooms", numberOfRooms))
        );
        return result.rowsAsObject();
    }

    public List<JsonObject> findAppartementsByPriceRange(double minPrice, double maxPrice) {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Apartments` WHERE defaultPrice >= $minPrice AND defaultPrice <= $maxPrice", appartementCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("minPrice", minPrice).put("maxPrice", maxPrice))
        );
        return result.rowsAsObject();
    }

    public List<JsonObject> findAppartementsByNumberOfBathrooms(int numberOfBathrooms) {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Apartments` WHERE numberOfBathrooms = $numberOfBathrooms", appartementCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("numberOfBathrooms", numberOfBathrooms))
        );
        return result.rowsAsObject();
    }
}
