package moteur.couchbase.src.services;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationService {
    private final Cluster cluster;
    private final Collection reservationCollection;

    public ReservationService(Cluster cluster, String database, Collection collection) {
        this.cluster = cluster;
        this.reservationCollection = collection;
    }

    // Méthodes CRUD
    public void createReservation(JsonObject reservation) {
        String id = reservation.getString("_id");
        reservationCollection.insert(id, reservation);
    }

    public void createReservations(List<JsonObject> reservations) {
        reservations.forEach(this::createReservation);
    }

    // Lire
    public List<JsonObject> getAllReservations() {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Reservation`", reservationCollection.name());
        QueryResult result = cluster.query(statement);
        return result.rowsAsObject();
    }

    public JsonObject getReservation(String id) {
        GetResult getResult = reservationCollection.get(id);
        return getResult.contentAsObject();
    }

    public Map<String, JsonObject> getReservations(List<String> ids) {
        Map<String, JsonObject> reservations = new HashMap<>();
        ids.forEach(id -> {
            try {
                GetResult getResult = reservationCollection.get(id);
                reservations.put(id, getResult.contentAsObject());
            } catch (DocumentNotFoundException e) {
                reservations.put(id, null);
            }
        });
        return reservations;
    }

    // Mettre à jour
    public void updateReservation(String id, JsonObject updatedReservation) {
        reservationCollection.replace(id, updatedReservation);
    }

    public void updateReservations(Map<String, JsonObject> reservations) {
        reservations.forEach(this::updateReservation);
    }

    // Supprimer
    public void deleteReservation(String id) {
        reservationCollection.remove(id);
    }

    public void deleteReservations(List<String> ids) {
        ids.forEach(this::deleteReservation);
    }


    // Méthodes de recherche
    public List<JsonObject> getReservationsByApartmentId(String apartmentId) {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Reservation` WHERE apartmentId = $apartmentId", reservationCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("apartmentId", apartmentId))
        );

        return result.rowsAsObject();
    }
}
