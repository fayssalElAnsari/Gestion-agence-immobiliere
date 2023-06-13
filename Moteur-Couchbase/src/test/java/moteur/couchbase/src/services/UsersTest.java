package moteur.couchbase.src.services;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.logging.Level;
import java.util.logging.LogManager;

import static moteur.couchbase.DataLoad.bucketCouchbase;
import static moteur.couchbase.DataLoad.connectToCouchbase;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersTest {
    @Mock
    private Collection collection;

    private Users users;

    @BeforeEach
    void setUp() {
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        MockitoAnnotations.initMocks(this);
        ClusterEnvironment env = ClusterEnvironment.builder().build();

        Bucket bucket;
        try {
            Cluster cluster = connectToCouchbase(env);
            bucket = cluster.bucket(bucketCouchbase);
            System.out.println("Connection to Couchbase successful!");
        } catch (Exception e) {
            System.out.println("Connection to Couchbase failed: " + e.getMessage());
            return;
        }
        
        collection = bucket.scope("tester").collection("Users");
        users = new Users(collection);
    }

    @Test
    void testInsertSingleRecord() throws DocumentExistsException {
        String id = "6488607c01b103077d0e2c93";
        JsonObject content = JsonObject.create()
                .put("name", "Taren Ledner")
                .put("email", "user59372491@example.com")
                .put("password", "vb767lxuiy0xfa")
                .put("_class", "com.projetmongodb.m1.immobilier.model.User");

        users.insert(id, content);

    }

    @Test
    void testUpdateSingleRecord() throws DocumentNotFoundException {
        String id = "6488607c01b103077d0e2c93";
        JsonObject content = JsonObject.create()
                .put("name", "Updated Name")
                .put("email", "updatedEmail@example.com")
                .put("password", "updatedPassword")
                .put("_class", "com.projetmongodb.m1.immobilier.model.User");

        users.update(id, content);

    }

    @Test
    void testDeleteSingleRecord() throws DocumentNotFoundException {
        String id = "6488607c01b103077d0e2c93";

        users.delete(id);

    }

    @Test
    void testSelectSingleRecord() throws DocumentNotFoundException {
        String id = "6488607c01b103077d0e2c93";
        //GetResult result = mock(GetResult.class);
        //when(collection.get(id)).thenReturn(result);

        GetResult actualResult = users.select(id);
        System.out.println(actualResult);

        //assertEquals(result, actualResult);
    }
}
