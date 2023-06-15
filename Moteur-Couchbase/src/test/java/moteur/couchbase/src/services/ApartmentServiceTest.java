package moteur.couchbase.src.services;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void createAndDeleteAppartement() {
        JsonObject appartement = JsonObject.create()
                .put("_id", "123")
                .put("title", "Test title")
                .put("description", "Test description");

        // Test creation
        service.createAppartement(appartement);
        JsonObject createdAppartement = service.getAppartement("123");
        assertEquals(appartement, createdAppartement);

        // Test deletion
        service.deleteAppartement("123");
        assertNull(service.getAppartement("123"));
    }

    @Test
    void findAppartementsByUserId() {
        // Assurez-vous que vous avez quelques appartements dans la base de données avec cet ownerId
        String ownerId = "567-85-7438";
        List<JsonObject> appartements = service.findAppartementsByUserId(ownerId);
        assertFalse(appartements.isEmpty());
//        for (JsonObject appartement : appartements) {
//            assertEquals(ownerId, appartement.getString("ownerId"));
//        }


    }

    // Ajoutez d'autres méthodes de test pour tester d'autres fonctions du service.
}
