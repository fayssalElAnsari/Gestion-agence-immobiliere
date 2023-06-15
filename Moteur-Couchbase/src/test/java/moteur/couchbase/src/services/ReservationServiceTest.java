package moteur.couchbase.src.services;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static moteur.couchbase.DataLoad.bucketCouchbase;
import static moteur.couchbase.DataLoad.connectToCouchbase;
import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private ReservationService service;
    private Bucket bucket;
    private Cluster cluster;

    @BeforeEach
    public void setUp() {
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        ClusterEnvironment env = ClusterEnvironment.builder().build();
        cluster = connectToCouchbase(env);
        bucket = cluster.bucket(bucketCouchbase);
        Collection collection = bucket.scope("tester").collection("Reservation");
        service = new ReservationService(cluster, "tester", collection);
    }

    @Test
    void createReservation() {
        JsonObject reservation = JsonObject.create()
                .put("_id", "test-reservation")
                .put("apartmentId", "96E02A35")
                .put("userId", "F68173DA")
                .put("startDate", "2023-07-04T19:01:54.107Z")
                .put("endDate", "2023-07-03T07:02:53.572Z")
                .put("price", 481.26);
        service.createReservation(reservation);
        System.out.println("Méthode createReservation Work");
        service.deleteReservation("test-reservation");

    }

    @Test
    void createReservations() {
        JsonObject reservation1 = JsonObject.create()
                .put("_id", "test-reservation1")
                .put("apartmentId", "96E02A35")
                .put("userId", "F68173DA")
                .put("price", 481.26);
        JsonObject reservation2 = JsonObject.create()
                .put("_id", "test-reservation2")
                .put("apartmentId", "96E02A35")
                .put("userId", "F68173DA")
                .put("startDate", "2023-07-04T19:01:54.107Z")
                .put("endDate", "2023-07-03T07:02:53.572Z")
                .put("price", 481.26);

        List<JsonObject> clients = Arrays.asList(reservation1, reservation2);
        service.createReservations(clients);

        System.out.println("Méthode createReservations Work");

        service.deleteReservation("test-reservation1");
        service.deleteReservation("test-reservation2");
    }

    @Test
    void getReservation() {
        // Appel de la méthode pour obtenir l'appartement
        JsonObject appartement = service.getReservation("mydoc50");
        System.out.println("Métohde getReservation Work " + appartement);
    }

    @Test
    void deleteReservation() {
        service.deleteReservation("mydoc60");
        System.out.println("Métohde deleteReservation Work ");
    }

    @Test
    void deleteReservations() {
        List<String> ids = Arrays.asList("mydoc61", "mydoc62");
        service.deleteReservations(ids);
        System.out.println("Métohde deleteReservations Work ");
    }

    @Test
    void createAndDeleteReservation() {
        JsonObject reservation = JsonObject.create()
                .put("_id", "test-reservation")
                .put("apartmentId", "96E02A35")
                .put("userId", "F68173DA")
                .put("startDate", "2023-07-04T19:01:54.107Z")
                .put("endDate", "2023-07-03T07:02:53.572Z")
                .put("price", 481.26);

        service.createReservation(reservation);
        JsonObject createdReservation = service.getReservation("test-reservation");

        assertEquals(reservation, createdReservation);

        service.deleteReservation("test-reservation");
    }

    @Test
    void updateReservation() {
        JsonObject reservation = JsonObject.create()
                .put("_id", "test-reservation")
                .put("apartmentId", "96E02A35")
                .put("userId", "F68173DA")
                .put("startDate", "2023-07-04T19:01:54.107Z")
                .put("endDate", "2023-07-03T07:02:53.572Z")
                .put("price", 481.26);

        service.createReservation(reservation);

        reservation.put("price", 500.00);
        service.updateReservation("test-reservation", reservation);

        JsonObject updatedReservation = service.getReservation("test-reservation");

        assertEquals(reservation, updatedReservation);

        service.deleteReservation("test-reservation");
    }

    @Test
    void findReservationsByApartmentId() {
        // Assurez-vous que vous avez quelques réservations dans la base de données avec cet apartmentId
        String apartmentId = "96E02A35";
        List<JsonObject> reservations = service.getReservationsByApartmentId(apartmentId);
        assertFalse(reservations.isEmpty());
    }
}
