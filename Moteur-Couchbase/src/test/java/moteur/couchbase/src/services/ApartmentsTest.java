package moteur.couchbase.src.services;

import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
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
import com.couchbase.client.java.json.JsonArray;

import java.util.logging.Level;
import java.util.logging.LogManager;

import static moteur.couchbase.DataLoad.bucketCouchbase;
import static moteur.couchbase.DataLoad.connectToCouchbase;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApartmentsTest {
    @Mock
    private Collection collection;

    private Apartments apartments;

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

        collection = bucket.scope("tester").collection("Apartments");
        apartments = new Apartments(collection);
    }

    @Test
    void testInsertSingleRecord() throws DocumentExistsException {
        String id = "6488605901b103077d0e2c92";

        JsonArray images = JsonArray.from(
                "http://lorempixel.com/g/1024/768/nature/",
                "http://lorempixel.com/640/350/nightlife/",
                "http://lorempixel.com/g/1024/768/technics/",
                "http://lorempixel.com/320/200/animals/",
                "http://lorempixel.com/1600/1200/food/"
        );

        JsonObject content = JsonObject.create()
                .put("title", "ut")
                .put("description", "Beatae dolor rerum qui est voluptatem.")
                .put("defaultPrice", 790.59)
                .put("numberOfRooms", 3)
                .put("numberOfBathrooms", 2)
                .put("area", 138.43)
                .put("address", "Suite 379 3463 Bode Island, South Silas, CT 82063")
                .put("location", JsonObject.create()
                        .put("latitude", -3.33783)
                        .put("longitude", 51.84902))
                .put("ownerId", "567-85-7438")
                .put("images", JsonObject.create()
                        .put("images", images))
                // Similairement, ajoutez les objets "rates", "createdAt", "updatedAt", "_class"
                ;

        apartments.insert(id, content);

        //verify(collection, times(1)).insert(id, content);
    }

    @Test
    void testUpdateSingleRecord() throws DocumentNotFoundException {
        String id = "6488605901b103077d0e2c92";
        JsonObject content = JsonObject.create()
                // update with new values
                .put("title", "Updated Title")
                .put("description", "Updated description.")
                .put("defaultPrice", 900.00);
        // etc, for all fields you wish to update

        apartments.update(id, content);

        //verify(collection, times(1)).replace(id, content);
    }

    @Test
    void testDeleteSingleRecord() throws DocumentNotFoundException {
        String id = "6488605901b103077d0e2c92";

        apartments.delete(id);

        //verify(collection, times(1)).remove(id);
    }

    @Test
    void testSelectSingleRecord() throws DocumentNotFoundException {
        String id = "6488605901b103077d0e2c92";
        //GetResult result = mock(GetResult.class);
        //when(collection.get(id)).thenReturn(result);

        GetResult actualResult = apartments.select(id);
        System.out.println(actualResult);

        //assertEquals(result, actualResult);
        ///verify(collection, times(1)).get(id);
    }
}
