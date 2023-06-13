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
            cluster = Cluster.connect(connection, ClusterOptions.clusterOptions(username, password).environment(env));
            bucket = cluster.bucket(bucketCouchbase);
            System.out.println("Connection to Couchbase successful!");
        } catch (Exception e) {
            System.out.println("Connection to Couchbase failed: " + e.getMessage());
            return;
        }

        // Un map qui lie chaque fichier JSON à une collection
        Map<String, String> fileToCollectionMap = Map.of(
                "C:\\Environnement.Travail\\Environnement.MasterInfo\\Gestion-agence-immobiliere\\Moteur-Couchbase\\src\\main\\java\\moteur\\couchbase\\objets\\objet.json", "test",
                "C:\\Environnement.Travail\\Environnement.MasterInfo\\Gestion-agence-immobiliere\\Moteur-Couchbase\\src\\main\\java\\moteur\\couchbase\\objets\\ze.json", "ze"

                // Ajoutez plus d'entrées ici si nécessaire
        );

        for (Map.Entry<String, String> entry : fileToCollectionMap.entrySet()) {
            String filePath = entry.getKey();
            String collectionName = entry.getValue();


            try {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                JsonArray jsonArray = JsonArray.fromJson(content);

                // Créez un scope si nécessaire
                String scopeName = "te";
                try {
                    bucket.collections().createScope(scopeName);
                    System.out.println("Scope created!");
                } catch (ScopeExistsException e) {
                    System.out.println("Scope already exists, not created.");
                }

                // Créez une collection si nécessaire
                try {
                    bucket.collections().createCollection(CollectionSpec.create(collectionName, scopeName));
                    System.out.println("Collection created!");
                } catch (CollectionExistsException e) {
                    System.out.println("Collection already exists, not created.");
                }

                // Accéder à la collection
                Collection collection = bucket.scope(scopeName).collection(collectionName);

                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.getObject(i);

                    // Générer un ID de document unique
                    String docId = "mydoc" + i; // Modifiez ceci selon vos besoins

                    MutationResult result = collection.upsert(docId, jsonObject,
                            UpsertOptions.upsertOptions().expiry(Duration.ofHours(1)));
                    System.out.println("Insert or update item ok.");

                }

            } catch (IOException e) {
                System.out.println("Erreur lors de la lecture du fichier: " + e.getMessage());
            }

        }

        // N'oubliez pas de fermer les ressources lorsque vous avez terminé
        cluster.disconnect();
        env.shutdown();
    }
}
