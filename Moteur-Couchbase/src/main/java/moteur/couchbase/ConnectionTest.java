package moteur.couchbase;

import com.couchbase.client.java.*;
import com.couchbase.client.java.env.ClusterEnvironment;

public class ConnectionTest {

    private static String connection = "couchbase://127.0.0.1";
    private static String username = "admin";
    private static String password = "admin123";
    private static String bucketCouchbase = "mtest";

    public static void main(String[] args) {
        ClusterEnvironment env = ClusterEnvironment.builder().build();
        Cluster cluster;

        try {
            cluster = Cluster.connect(connection, ClusterOptions.clusterOptions(username, password).environment(env));
            Bucket bucket = cluster.bucket(bucketCouchbase);
            System.out.println("Connection to Couchbase successful!");
        } catch (Exception e) {
            System.out.println("Connection to Couchbase failed: " + e.getMessage());
            return;
        }

        // Don't forget to close resources when you're done
        //cluster.disconnect();
        //env.shutdown();
    }
}
