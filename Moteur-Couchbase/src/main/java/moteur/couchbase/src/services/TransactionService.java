package moteur.couchbase.src.services;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionService {
    private final Cluster cluster;
    private final Collection transactionCollection;

    public TransactionService(Cluster cluster, String database, Collection collection) {
        this.cluster = cluster;
        this.transactionCollection = collection;
    }

    // Méthodes CRUD...

    // Créer
    public void createTransaction(JsonObject transaction) {
        String id = transaction.getString("_id");
        transactionCollection.insert(id, transaction);
    }

    public void createTransactions(List<JsonObject> transactions) {
        transactions.forEach(this::createTransaction);
    }

    // Lire
    public List<JsonObject> getAllTransactions() {
        String statement = String.format("SELECT * FROM `%s`", transactionCollection.name());
        QueryResult result = cluster.query(statement);
        return result.rowsAsObject();
    }

    public JsonObject getTransaction(String id) {
        GetResult getResult = transactionCollection.get(id);
        return getResult.contentAsObject();
    }

    public List<JsonObject> getTransactions(List<String> ids) {
        return ids.stream()
                .map(id -> transactionCollection.get(id).contentAsObject())
                .collect(Collectors.toList());
    }

    // Mettre à jour
    public void updateTransaction(String id, JsonObject updatedTransaction) {
        transactionCollection.replace(id, updatedTransaction);
    }

    public void updateTransactions(List<String> ids, List<JsonObject> updatedTransactions) {
        for (int i = 0; i < ids.size(); i++) {
            transactionCollection.replace(ids.get(i), updatedTransactions.get(i));
        }
    }

    // Supprimer
    public void deleteTransaction(String id) {
        transactionCollection.remove(id);
    }

    public void deleteTransactions(List<String> ids) {
        ids.forEach(this::deleteTransaction);
    }


    // Méthodes de recherche
    public List<JsonObject> getTransactionsByClientId(String clientId) {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Transactions` WHERE clientId = $clientId", transactionCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters(JsonObject.create().put("clientId", clientId))
        );

        return result.rowsAsObject();
    }

}
