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

class ClientServiceTest {

    private ClientService service;
    private Bucket bucket;
    private Cluster cluster;

    @BeforeEach
    public void setUp() {
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        ClusterEnvironment env = ClusterEnvironment.builder().build();
        cluster = connectToCouchbase(env);
        bucket = cluster.bucket(bucketCouchbase);
        Collection collection = bucket.scope("tester").collection("Clients");
        service = new ClientService(cluster, "tester", collection);
    }

    @Test
    void createClient() {
        JsonObject client = JsonObject.create()
                .put("_id", "test-client")
                .put("email", "testemail@gmail.com")
                .put("password", "testpassword")
                .put("phoneNumber", "1234567890")
                .put("countryOfOrigin", "Test Country")
                .put("isVerified", false)
                .put("profilePicture", "http://test.com/profile.jpg")
                .put("reservations", Arrays.asList("123-45-6789", "987-65-4321"))
                .put("transactions", Arrays.asList("567-89-1234", "432-10-9876"));
        service.createClient(client);
        System.out.println("Méthode createClient Work");
        service.deleteClient("test-client");

    }

    @Test
    void createClients() {
        JsonObject client1 = JsonObject.create()
                .put("_id", "test-ou")
                .put("email", "aze@gmail.com")
                .put("password", "vfv")
                .put("phoneNumber", "1234567890")
                .put("countryOfOrigin", "France")
                .put("isVerified", false)
                .put("profilePicture", "http://test.com/profile.jpg")
                .put("transactions", Arrays.asList("567-89-1234", "432-10-9876"));
        JsonObject client2 = JsonObject.create()
                .put("_id", "test-bono")
                .put("email", "testemail@gmail.com")
                .put("password", "testpassword")
                .put("phoneNumber", "1234567890")
                .put("isVerified", false)
                .put("profilePicture", "http://test.com/profile.jpg")
                .put("reservations", Arrays.asList("123-45-6789", "987-65-4321"))
                .put("transactions", Arrays.asList("567-89-1234", "432-10-9876"));
        List<JsonObject> clients = Arrays.asList(client1, client2);
        service.createClients(clients);

        System.out.println("Méthode createClients Work");

        service.deleteClient("test-ou");
        service.deleteClient("test-bono");
    }

    @Test
    void getClient() {
        // Appel de la méthode pour obtenir l'appartement
        JsonObject appartement = service.getClient("mydoc32");
        System.out.println("Métohde getClient Work " + appartement);
    }

//    @Test
//    void updateAppartement() {
//        JsonObject appartement = service.getAppartement("mydoc20");
//        appartement.put("area", 542.21);
//        service.updateAppartement(appartement);
//    }

    @Test
    void deleteClient() {
        service.deleteClient("mydoc60");
        System.out.println("Métohde deleteAppartement Work ");
    }

    @Test
    void deleteClients() {
        List<String> ids = Arrays.asList("mydoc61", "mydoc62");
        service.deleteClients(ids);
        System.out.println("Métohde deleteAppartements Work ");
    }


    @Test
    void createAndDeleteClient() {
        JsonObject client = JsonObject.create()
                .put("_id", "test-client")
                .put("email", "testemail@gmail.com")
                .put("password", "testpassword")
                .put("phoneNumber", "1234567890")
                .put("countryOfOrigin", "Test Country")
                .put("isVerified", false)
                .put("profilePicture", "http://test.com/profile.jpg")
                .put("reservations", Arrays.asList("123-45-6789", "987-65-4321"))
                .put("transactions", Arrays.asList("567-89-1234", "432-10-9876"));

        service.createClient(client);
        JsonObject createdClient = service.getClient("test-client");

        assertEquals(client, createdClient);

        service.deleteClient("test-client");
        JsonObject deletedClient = service.getClient("test-client");

        assertNull(deletedClient);
    }

    @Test
    void updateClient() {
        JsonObject client = JsonObject.create()
                .put("_id", "test-client")
                .put("email", "testemail@gmail.com")
                .put("password", "testpassword")
                .put("phoneNumber", "1234567890")
                .put("countryOfOrigin", "Test Country")
                .put("isVerified", false)
                .put("profilePicture", "http://test.com/profile.jpg")
                .put("reservations", Arrays.asList("123-45-6789", "987-65-4321"))
                .put("transactions", Arrays.asList("567-89-1234", "432-10-9876"));

        service.createClient(client);

        client.put("email", "updatedemail@gmail.com");
        service.updateClient(client);

        JsonObject updatedClient = service.getClient("test-client");

        assertEquals(client, updatedClient);

        service.deleteClient("test-client");
    }

    @Test
    void getClientsByCountry() {
        // Assurez-vous que vous avez quelques clients dans la base de données de ce pays
        String country = "Aruba";
        List<JsonObject> clients = service.getClientsByCountry(country);
        assertFalse(!clients.isEmpty());
    }

    @Test
    void getClientsNotVerified() {
        // Assurez-vous que vous avez quelques clients dans la base de données qui ne sont pas vérifiés
        List<JsonObject> clients = service.getClientsNotVerified();
        assertFalse(clients.isEmpty());
    }
}
