import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final Cluster cluster;
    private final Collection userCollection;

    public UserService(Cluster cluster, String database, String collection) {
        this.cluster = cluster;
        this.userCollection = cluster.bucket(database).defaultCollection();
    }

    // Méthodes CRUD...

    // Méthodes de recherche
    public List<User> getUsersNotVerified() {
        String query = "SELECT * FROM `database`.`collection` WHERE type = 'User' AND verified = false";
        QueryResult result = cluster.query(query);
        return result.rows().stream()
                .map(row -> row.contentAs(User.class))
                .collect(Collectors.toList());
    }
}
