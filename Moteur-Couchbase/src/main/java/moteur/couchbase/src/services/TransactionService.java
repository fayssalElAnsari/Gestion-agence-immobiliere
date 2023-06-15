package moteur.couchbase.src.services;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import moteur.couchbase.src.models.Transaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionService {
    private final Cluster cluster;
    private final Collection transactionCollection;

    public TransactionService(Cluster cluster, String database, String collection) {
        this.cluster = cluster;
        this.transactionCollection = cluster.bucket(database).defaultCollection();
    }

    // Méthodes CRUD...

    // Créer
    public void createTransaction(Transaction transaction) {
        transactionCollection.insert(transaction.getId(), transaction);
    }

    public void createTransactions(List<Transaction> transactions) {
        transactions.forEach(transaction -> transactionCollection.insert(transaction.getId(), transaction));
    }

    // Lire
    public List<Transaction> getAllTransactions() {
        String statement = String.format("SELECT * FROM `%s` WHERE type = 'Transaction'", transactionCollection.name());
        QueryResult result = cluster.query(statement);
        return result.rowsAs(Transaction.class);
    }

    public Transaction getTransaction(String id) {
        GetResult getResult = transactionCollection.get(id);
        return getResult.contentAs(Transaction.class);
    }

    public List<Transaction> getTransactions(List<String> ids) {
        return ids.stream()
                .map(id -> transactionCollection.get(id).contentAs(Transaction.class))
                .collect(Collectors.toList());
    }

    // Mettre à jour
    public void updateTransaction(String id, Transaction updatedTransaction) {
        transactionCollection.replace(id, updatedTransaction);
    }

    public void updateTransactions(List<String> ids, List<Transaction> updatedTransactions) {
        for (int i = 0; i < ids.size(); i++) {
            transactionCollection.replace(ids.get(i), updatedTransactions.get(i));
        }
    }

    // Supprimer
    public void deleteTransaction(String id) {
        transactionCollection.remove(id);
    }

    public void deleteTransactions(List<String> ids) {
        ids.forEach(id -> transactionCollection.remove(id));
    }


    // Méthodes de recherche
    public List<Transaction> getTransactionsByClientId(String clientId) {
        String statement = String.format("SELECT * FROM `%s` WHERE clientId = $clientId AND type = 'Transaction'", transactionCollection.name());
        QueryResult result = cluster.query(
                statement,
                QueryOptions.queryOptions().parameters((JsonObject) Map.of("clientId", clientId))
        );

        return result.rowsAs(Transaction.class);
    }

}
