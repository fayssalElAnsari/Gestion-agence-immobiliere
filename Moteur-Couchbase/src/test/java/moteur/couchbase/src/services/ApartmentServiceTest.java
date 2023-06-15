package moteur.couchbase.src.services;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static moteur.couchbase.DataLoad.bucketCouchbase;
import static moteur.couchbase.DataLoad.connectToCouchbase;
import static org.junit.jupiter.api.Assertions.*;

class ApartmentServiceTest {

    private AppartementService service;
    private Bucket bucket;
    private Cluster cluster;
    @BeforeEach
    public void setUp() {
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        ClusterEnvironment env = ClusterEnvironment.builder().build();
        cluster = connectToCouchbase(env);
        bucket = cluster.bucket(bucketCouchbase);
        Collection collection = bucket.scope("tester").collection("Apartments");
        service = new AppartementService(cluster, "tester", collection);
    }

    @Test
    void createAppartement() {
        JsonObject appartement = JsonObject.create()
                .put("_id", "One-Appart")
                .put("ownerId", "567-85-7438")
                .put("numberOfRooms", 2)
                .put("defaultPrice", 700.0)
                .put("numberOfBathrooms", 1);
        service.createAppartement(appartement);
        System.out.println("Méthode CreateApartement Work");
        service.deleteAppartement("One-Appart");

    }

    @Test
    void createAppartements() {
        JsonObject appartement1 = JsonObject.create()
                .put("_id", "One-Appart")
                .put("ownerId", "567-85-7438")
                .put("numberOfRooms", 2)
                .put("defaultPrice", 700.0)
                .put("numberOfBathrooms", 1);
        JsonObject appartement2 = JsonObject.create()
                .put("_id", "Two-Appart")
                .put("ownerId", "123-587-6985")
                .put("numberOfRooms", 2)
                .put("defaultPrice", 400)
                .put("numberOfBathrooms", 1);
        List<JsonObject> appartements = Arrays.asList(appartement1, appartement2);
        service.createAppartements(appartements);

        System.out.println("Méthode CreateApartement Work");

        service.deleteAppartement("One-Appart");
        service.deleteAppartement("Two-Appart");
    }

    @Test
    void getAppartement() {
        // Appel de la méthode pour obtenir l'appartement
        JsonObject appartement = service.getAppartement("mydoc22");
        System.out.println("Métohde getAppartement Work " + appartement);
    }

//    @Test
//    void updateAppartement() {
//        JsonObject appartement = service.getAppartement("mydoc20");
//        appartement.put("area", 542.21);
//        service.updateAppartement(appartement);
//    }

    @Test
    void deleteAppartement() {
        service.deleteAppartement("mydoc22");
        System.out.println("Métohde deleteAppartement Work ");
    }

    @Test
    void deleteAppartements() {
        List<String> ids = Arrays.asList("mydoc25", "mydoc26");
        service.deleteAppartements(ids);
        System.out.println("Métohde deleteAppartements Work ");
    }

    @Test
    void createAndDeleteAppartement() {
        JsonObject appartement = JsonObject.create()
                .put("_id", "test-appartement")
                .put("ownerId", "567-85-7438")
                .put("numberOfRooms", 2)
                .put("defaultPrice", 700.0)
                .put("numberOfBathrooms", 1);

        service.createAppartement(appartement);
        JsonObject createdAppartement = service.getAppartement("test-appartement");

        assertEquals(appartement, createdAppartement);

        service.deleteAppartement("test-appartement");
        JsonObject deletedAppartement = service.getAppartement("test-appartement");

        assertNull(deletedAppartement);
    }

    @Test
    void updateAppartement() {
        JsonObject appartement = JsonObject.create()
                .put("_id", "test-appartement")
                .put("ownerId", "567-85-7438")
                .put("numberOfRooms", 2)
                .put("defaultPrice", 700.0)
                .put("numberOfBathrooms", 1);

        service.createAppartement(appartement);

        appartement.put("numberOfRooms", 3);
        service.updateAppartement(appartement);

        JsonObject updatedAppartement = service.getAppartement("test-appartement");

        assertEquals(appartement, updatedAppartement);

        service.deleteAppartement("test-appartement");
    }

    @Test
    void findAppartementsByUserId() {
        // Assurez-vous que vous avez quelques appartements dans la base de données avec cet ownerId
        String ownerId = "567-85-7438";
        List<JsonObject> appartements = service.findAppartementsByUserId(ownerId);
        assertFalse(appartements.isEmpty());
    }

    @Test
    void findAppartementsByNumberOfRooms() {
        // Assurez-vous que vous avez quelques appartements dans la base de données avec ce nombre de pièces
        int numberOfRooms = 2;
        List<JsonObject> appartements = service.findAppartementsByNumberOfRooms(numberOfRooms);
        assertFalse(appartements.isEmpty());
    }

    @Test
    void findAppartementsByPriceRange() {
        // Assurez-vous que vous avez quelques appartements dans la base de données avec ces prix
        double minPrice = 500.0, maxPrice = 1000.0;
        List<JsonObject> appartements = service.findAppartementsByPriceRange(minPrice, maxPrice);
        assertFalse(appartements.isEmpty());
    }

    @Test
    void findAppartementsByNumberOfBathrooms() {
        // Assurez-vous que vous avez quelques appartements dans la base de données avec ce nombre de salles de bains
        int numberOfBathrooms = 1;
        List<JsonObject> appartements = service.findAppartementsByNumberOfBathrooms(numberOfBathrooms);
        assertFalse(appartements.isEmpty());
    }

}
