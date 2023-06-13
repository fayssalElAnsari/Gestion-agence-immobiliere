package moteur.couchbase;

import com.couchbase.client.core.error.CollectionExistsException;
import com.couchbase.client.core.error.ScopeExistsException;
import com.couchbase.client.java.*;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.UpsertOptions;
import com.couchbase.client.java.manager.collection.CollectionSpec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class DataLoad {

    private static String connection = "couchbase://127.0.0.1";
    private static String username = "admin";
    private static String password = "admin123";
    private static String bucketCouchbase = "mtest";

    public static void main(String[] args) {

        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        ClusterEnvironment env = ClusterEnvironment.builder().build();
        Bucket bucket;
        Cluster cluster;

        try {
            cluster = connectToCouchbase(env);
            bucket = cluster.bucket(bucketCouchbase);
            System.out.println("Connection to Couchbase successful!");
        } catch (Exception e) {
            System.out.println("Connection to Couchbase failed: " + e.getMessage());
            return;
        }

        Map<String, String> fileToCollectionMap = Map.of(
                "C:\\Environnement.Travail\\Environnement.MasterInfo\\Gestion-agence-immobiliere\\Moteur-Couchbase\\src\\main\\java\\moteur\\couchbase\\objets\\objet.json", "test",
                "C:\\Environnement.Travail\\Environnement.MasterInfo\\Gestion-agence-immobiliere\\Moteur-Couchbase\\src\\main\\java\\moteur\\couchbase\\objets\\ze.json", "ze"
        );

        loadDataFromFilesToCollections(bucket, fileToCollectionMap);

        disconnectFromCouchbase(cluster, env);
    }

    public static Cluster connectToCouchbase(ClusterEnvironment env) {
        return Cluster.connect(connection, ClusterOptions.clusterOptions(username, password).environment(env));
    }

    public static void loadDataFromFilesToCollections(Bucket bucket, Map<String, String> fileToCollectionMap) {
        for (Map.Entry<String, String> entry : fileToCollectionMap.entrySet()) {
            String filePath = entry.getKey();
            String collectionName = entry.getValue();

            try {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                JsonArray jsonArray = JsonArray.fromJson(content);

                String scopeName = "tester";
                createScopeIfNotExists(bucket, scopeName);
                createCollectionIfNotExists(bucket, collectionName, scopeName);

                Collection collection = bucket.scope(scopeName).collection(collectionName);
                loadDataIntoCollection(collection, jsonArray);

            } catch (IOException e) {
                System.out.println("Erreur lors de la lecture du fichier: " + e.getMessage());
            }
        }
    }

    public static void createScopeIfNotExists(Bucket bucket, String scopeName) {
        try {
            bucket.collections().createScope(scopeName);
            System.out.println("Scope created!");
        } catch (ScopeExistsException e) {
            System.out.println("Scope already exists, not created.");
        }
    }

    public static void createCollectionIfNotExists(Bucket bucket, String collectionName, String scopeName) {
        try {
            bucket.collections().createCollection(CollectionSpec.create(collectionName, scopeName));
            System.out.println("Collection created!");
        } catch (CollectionExistsException e) {
            System.out.println("Collection already exists, not created.");
        }
    }

    public static void loadDataIntoCollection(Collection collection, JsonArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.getObject(i);

            String docId = "mydoc" + i;

            MutationResult result = collection.upsert(docId, jsonObject,
                    UpsertOptions.upsertOptions().expiry(Duration.ofHours(1)));
            System.out.println("Insert or update item ok.");
        }
    }

    public static void disconnectFromCouchbase(Cluster cluster, ClusterEnvironment env) {
        cluster.disconnect();
        env.shutdown();
    }
}
