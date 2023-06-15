import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    private final Cluster cluster;
    private final Collection transactionCollection;

    public TransactionService(Cluster cluster, String database, String collection) {
        this.cluster = cluster;
        this.transactionCollection = cluster.bucket(database).defaultCollection();
    }

    // Méthodes CRUD...

    // Méthodes de recherche
    public List<Transaction> getTransactionsByClientId(String clientId) {
        String query = "SELECT * FROM `database`.`collection` WHERE type = 'Transaction' AND clientId = $clientId";
        QueryResult result = cluster.query(query, QueryOptions.queryOptions().parameters(JsonObject.create().put("clientId", clientId)));
        return result.rows().stream()
                .map(row -> row.contentAs(Transaction.class))
                .collect(Collectors.toList());
    }
}
