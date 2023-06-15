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

class UserServiceTest {

    private UserService service;
    private Bucket bucket;
    private Cluster cluster;

    @BeforeEach
    public void setUp() {
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        ClusterEnvironment env = ClusterEnvironment.builder().build();
        cluster = connectToCouchbase(env);
        bucket = cluster.bucket(bucketCouchbase);
        Collection collection = bucket.scope("tester").collection("Users");
        service = new UserService(cluster, "tester", collection);
    }

    @Test
    void createUser() {
        JsonObject user = JsonObject.create()
                .put("_id", "test-user")
                .put("name", "Test User")
                .put("email", "test@example.com")
                .put("password", "testpassword");
        service.createUser(user);
        System.out.println("Méthode createUser Work");
        service.deleteUser("test-user");

    }

    @Test
    void createUsers() {
        JsonObject user1 = JsonObject.create()
                .put("_id", "test-user1")
                .put("name", "Test User")
                .put("email", "test@example.com")
                .put("password", "testpassword");
        JsonObject user2 = JsonObject.create()
                .put("_id", "test-user2")
                .put("name", "Test User")
                .put("email", "test@example.com")
                .put("password", "testpassword");

        List<JsonObject> users = Arrays.asList(user1, user2);
        service.createUsers(users);

        System.out.println("Méthode createUsers Work");

        service.deleteUser("test-user1");
        service.deleteUser("test-user2");
    }

    @Test
    void getUser() {
        // Appel de la méthode pour obtenir l'appartement
        JsonObject appartement = service.getUser("mydoc50");
        System.out.println("Métohde getUser Work " + appartement);
    }

    @Test
    void deleteUser() {
        service.deleteUser("mydoc60");
        System.out.println("Métohde deleteUser Work ");
    }

    @Test
    void deleteUsers() {
        List<String> ids = Arrays.asList("mydoc61", "mydoc62");
        service.deleteUsers(ids);
        System.out.println("Métohde deleteUsers Work ");
    }

    @Test
    void createAndDeleteUser() {
        JsonObject user = JsonObject.create()
                .put("_id", "test-user")
                .put("name", "Test User")
                .put("email", "test@example.com")
                .put("password", "testpassword");

        service.createUser(user);
        JsonObject createdUser = service.getUser("test-user");

        assertEquals(user, createdUser);

        service.deleteUser("test-user");

    }

    @Test
    void updateUser() {
        JsonObject user = JsonObject.create()
                .put("_id", "test-user")
                .put("name", "Test User")
                .put("email", "test@example.com")
                .put("password", "testpassword");

        service.createUser(user);

        user.put("name", "Updated Test User");
        service.updateUser("test-user", user);

        JsonObject updatedUser = service.getUser("test-user");

        assertEquals(user, updatedUser);

        service.deleteUser("test-user");
    }

    @Test
    void findUsersNotVerified() {
        // Assurez-vous que vous avez quelques utilisateurs non vérifiés dans la base de données
        List<JsonObject> users = service.getUsersNotVerified();
        assertFalse(!users.isEmpty());
    }
}
