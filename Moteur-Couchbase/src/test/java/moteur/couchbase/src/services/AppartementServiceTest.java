package moteur.couchbase.src.services;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static moteur.couchbase.DataLoad.bucketCouchbase;
import static moteur.couchbase.DataLoad.connectToCouchbase;
import static org.junit.jupiter.api.Assertions.*;

public class AppartementServiceTest {
    private Cluster cluster;
    private Collection collection;
    private AppartementService service;

    @BeforeEach
    public void setUp() {
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        ClusterEnvironment env = ClusterEnvironment.builder().build();
        cluster = connectToCouchbase(env);
        Bucket bucket = cluster.bucket(bucketCouchbase);
        collection = bucket.scope("tester").collection("Apartments");
        service = new AppartementService(cluster, "tester", collection);
    }

    @Test
    public void testCreateAndRetrieveAppartement() {
        JsonObject appartement = JsonObject.create()
                .put("_id", "testId")
                .put("title", "testTitle")
                // Ajoutez ici tous les autres champs de l'appartement
                ;

        service.createAppartement(appartement);

        JsonObject retrievedAppartement = service.getAppartement("testId");
        assertEquals("testTitle", retrievedAppartement.getString("title"));
        // Vérifiez ici tous les autres champs de l'appartement
    }

    @Test
    public void testUpdateAppartement() {
        JsonObject appartement = service.getAppartement("testId");
        appartement.put("title", "updatedTitle");
        service.updateAppartement(appartement);

        JsonObject updatedAppartement = service.getAppartement("6488605901b103077d0e2c92");
        //assertEquals("updatedTitle", updatedAppartement.getString("title"));
    }

    @Test
    public void testDeleteAppartement() {
        service.deleteAppartement("testId");
        assertNull(service.getAppartement("testId"));
    }

    @Test
    public void testFindAppartementsByUserId() {
        // Assurez-vous que vous avez quelques appartements dans la base de données avec cet ownerId
        String ownerId = "567-85-7438";
        List<JsonObject> appartements = service.findAppartementsByUserId(ownerId);
        assertFalse(appartements.isEmpty());
        for (JsonObject appartement : appartements) {
            assertEquals(ownerId, appartement.getString("ownerId"));
        }
    }

    @Test
    public void testFindAppartementsByNumberOfRooms() {
        // Assurez-vous que vous avez quelques appartements dans la base de données avec cet ownerId
        List<JsonObject> appartements = service.findAppartementsByNumberOfRooms(2);
        assertFalse(appartements.isEmpty());

    }

    // Vous pouvez créer des tests similaires pour les méthodes findAppartementsByNumberOfRooms et findAppartementsByPriceRange
}
