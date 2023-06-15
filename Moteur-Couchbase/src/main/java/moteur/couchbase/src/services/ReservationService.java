import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
    private final Cluster cluster;
    private final Collection reservationCollection;

    public ReservationService(Cluster cluster, String database, String collection) {
        this.cluster = cluster;
        this.reservationCollection = cluster.bucket(database).defaultCollection();
    }

    // Méthodes CRUD...

    // Méthodes de recherche
    public List<Reservation> getReservationsByApartmentId(String apartmentId) {
        String query = "SELECT * FROM `database`.`collection` WHERE type = 'Reservation' AND apartmentId = $apartmentId";
        QueryResult result = cluster.query(query, QueryOptions.queryOptions().parameters(JsonObject.create().put("apartmentId", apartmentId)));
        return result.rows().stream()
                .map(row -> row.contentAs(Reservation.class))
                .collect(Collectors.toList());
    }
}
