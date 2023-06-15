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

class TransactionServiceTest {

    private TransactionService service;
    private Bucket bucket;
    private Cluster cluster;

    @BeforeEach
    public void setUp() {
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        ClusterEnvironment env = ClusterEnvironment.builder().build();
        cluster = connectToCouchbase(env);
        bucket = cluster.bucket(bucketCouchbase);
        Collection collection = bucket.scope("tester").collection("Transactions");
        service = new TransactionService(cluster, "tester", collection);
    }

    @Test
    void createTransaction() {
        JsonObject transaction = JsonObject.create()
                .put("_id", "test-transaction")
                .put("clientId", "361-68-1108")
                .put("reservationId", "599-65-3663")
                .put("amount", 784)
                .put("currency", "USD")
                .put("bankName", "Royal Bank of Canada")
                .put("accountNumber", "CH5051010O1C1Hi6pmjWB")
                .put("transactionType", "CREDIT")
                .put("transactionMode", "NET_BANKING")
                .put("transactionStatus", "PENDING")
                .put("transactionDateTime", "2023-06-13T12:39:12.370Z");
        service.createTransaction(transaction);
        System.out.println("Méthode createTransaction Work");
        service.deleteTransaction("test-transaction");

    }

    @Test
    void createTransactions() {
        JsonObject transaction1 = JsonObject.create()
                .put("_id", "test-transaction1")
                .put("clientId", "361-68-1108")
                .put("reservationId", "599-65-3663")
                .put("amount", 784)
                .put("currency", "USD")
                .put("bankName", "Royal Bank of Canada")
                .put("accountNumber", "CH5051010O1C1Hi6pmjWB");
        JsonObject transaction2 = JsonObject.create()
                .put("_id", "test-transaction2")
                .put("clientId", "361-85-8745")
                .put("reservationId", "599-65-3663")
                .put("amount", 784)
                .put("currency", "BTC")
                .put("bankName", "Bank FR")
                .put("accountNumber", "CH5051010O1C1Hi6pmjWB")
                .put("transactionType", "CREDIT")
                .put("transactionMode", "NET_BANKING");

        List<JsonObject> clients = Arrays.asList(transaction1, transaction2);
        service.createTransactions(clients);

        System.out.println("Méthode createTransactions Work");

        service.deleteTransaction("test-transaction1");
        service.deleteTransaction("test-transaction2");
    }

    @Test
    void getTransaction() {
        // Appel de la méthode pour obtenir l'appartement
        JsonObject appartement = service.getTransaction("mydoc50");
        System.out.println("Métohde getTransaction Work " + appartement);
    }

    @Test
    void deleteTransaction() {
        service.deleteTransaction("mydoc60");
        System.out.println("Métohde deleteTransaction Work ");
    }

    @Test
    void deleteTransactions() {
        List<String> ids = Arrays.asList("mydoc61", "mydoc62");
        service.deleteTransactions(ids);
        System.out.println("Métohde deleteTransactions Work ");
    }

    @Test
    void createAndDeleteTransaction() {
        JsonObject transaction = JsonObject.create()
                .put("_id", "test-transaction5")
                .put("clientId", "361-68-1254")
                .put("reservationId", "599-65-3663")
                .put("amount", 784)
                .put("currency", "USD")
                .put("bankName", "Royal Bank of Canada")
                .put("accountNumber", "CH5051010O1C1Hi6pmjWB")
                .put("transactionType", "CREDIT")
                .put("transactionMode", "NET_BANKING")
                .put("transactionStatus", "PENDING")
                .put("transactionDateTime", "2023-06-13T12:39:12.370Z");

        service.createTransaction(transaction);
        JsonObject createdTransaction = service.getTransaction("test-transaction5");

        assertEquals(transaction, createdTransaction);

        service.deleteTransaction("test-transaction5");
    }

    @Test
    void updateTransaction() {
        JsonObject transaction = JsonObject.create()
                .put("_id", "test-transaction5")
                .put("clientId", "361-68-4854")
                .put("reservationId", "599-65-3663")
                .put("amount", 784)
                .put("currency", "USD")
                .put("bankName", "Royal Bank of Canada")
                .put("accountNumber", "CH5051010O1C1Hi6pmjWB")
                .put("transactionType", "CREDIT")
                .put("transactionMode", "NET_BANKING")
                .put("transactionStatus", "PENDING")
                .put("transactionDateTime", "2023-06-13T12:39:12.370Z");

        service.createTransaction(transaction);

        transaction.put("transactionStatus", "COMPLETED");
        service.updateTransaction("test-transaction5", transaction);

        JsonObject updatedTransaction = service.getTransaction("test-transaction5");

        assertEquals(transaction, updatedTransaction);

        service.deleteTransaction("test-transaction5");


    }

    @Test
    void findTransactionsByClientId() {
        // Assurez-vous que vous avez quelques transactions dans la base de données avec ce clientId
        String clientId = "694-04-5032";
        List<JsonObject> transactions = service.getTransactionsByClientId(clientId);
        assertFalse(transactions.isEmpty());
    }
}
