package moteur.couchbase.src.services;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import moteur.couchbase.src.models.Appartement;

import java.util.List;
import java.util.Map;

public class AppartementService {
    private final Cluster cluster;
    private final Collection appartementCollection;

    public AppartementService(Cluster cluster, String database, String collection) {
        this.cluster = cluster;
        this.appartementCollection = cluster.bucket(database).defaultCollection();
    }

    // Methodes CRUD

    // Creation

    public void createAppartement(Appartement appartement) {
        appartementCollection.insert(appartement.getId(), appartement);
    }

    public void createAppartements(List<Appartement> appartements) {
        appartements.forEach(this::createAppartement);
    }

    // Lecture

    public Appartement getAppartement(String id) {
        try {
            GetResult result = appartementCollection.get(id);
            return result.contentAs(Appartement.class);
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    // Mise à jour

    public void updateAppartement(Appartement appartement) {
        appartementCollection.replace(appartement.getId(), appartement);
    }

    public void updateAppartements(List<Appartement> appartements) {
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

    public List<Appartement> findAppartementsByUserId(String userId) {
        String statement = String.format("SELECT * FROM `%s` WHERE userId = $userId", appartementCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters((JsonObject) Map.of("userId", userId))
        );

        return result.rowsAs(Appartement.class);
    }

    public List<Appartement> findAppartementsByNumberOfRooms(int numberOfRooms) {
        String statement = String.format("SELECT * FROM `%s` WHERE numberOfRooms = $numberOfRooms", appartementCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters((JsonObject) Map.of("numberOfRooms", numberOfRooms))
        );

        return result.rowsAs(Appartement.class);
    }

    public List<Appartement> findAppartementsByPriceRange(double minPrice, double maxPrice) {
        String statement = String.format("SELECT * FROM `%s` WHERE price >= $minPrice AND price <= $maxPrice", appartementCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters((JsonObject) Map.of("minPrice", minPrice, "maxPrice", maxPrice))
        );

        return result.rowsAs(Appartement.class);
    }

}
