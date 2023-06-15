import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import moteur.couchbase.src.models.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationService {
    private final Cluster cluster;
    private final Collection reservationCollection;

    public ReservationService(Cluster cluster, String database, String collection) {
        this.cluster = cluster;
        this.reservationCollection = cluster.bucket(database).defaultCollection();
    }

    // Méthodes CRUD...
    public void createReservation(Reservation reservation) {
        reservationCollection.insert(reservation.getId(), reservation);
    }

    public void createReservations(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            reservationCollection.insert(reservation.getId(), reservation);
        }
    }

    // Lire
    public List<Reservation> getAllReservations() {
        String statement = String.format("SELECT * FROM `%s` WHERE type = 'Reservation'", reservationCollection.name());
        QueryResult result = cluster.query(statement);
        return result.rowsAs(Reservation.class);
    }

    public Reservation getReservation(String id) {
        GetResult getResult = reservationCollection.get(id);
        return getResult.contentAs(Reservation.class);
    }

    public Map<String, Reservation> getReservations(List<String> ids) {
        Map<String, Reservation> reservations = new HashMap<>();
        ids.forEach(id -> {
            try {
                GetResult getResult = reservationCollection.get(id);
                reservations.put(id, getResult.contentAs(Reservation.class));
            } catch (DocumentNotFoundException e) {
                reservations.put(id, null);
            }
        });
        return reservations;
    }

    // Mettre à jour
    public void updateReservation(String id, Reservation updatedReservation) {
        reservationCollection.replace(id, updatedReservation);
    }

    public void updateReservations(Map<String, Reservation> reservations) {
        reservations.forEach((id, reservation) -> reservationCollection.replace(id, reservation));
    }

    // Supprimer
    public void deleteReservation(String id) {
        reservationCollection.remove(id);
    }

    public void deleteReservations(List<String> ids) {
        for (String id : ids) {
            reservationCollection.remove(id);
        }
    }


    // Méthodes de recherche
    public List<Reservation> getReservationsByApartmentId(String apartmentId) {
        String statement = String.format("SELECT * FROM `%s` WHERE apartmentId = $apartmentId", reservationCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters((JsonObject) Map.of("apartmentId", apartmentId))
        );

        return result.rowsAs(Reservation.class);
    }

}
