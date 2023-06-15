package moteur.couchbase.src.services;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {
    private final Cluster cluster;
    private final Collection userCollection;

    public UserService(Cluster cluster, String database, Collection collection) {
        this.cluster = cluster;
        this.userCollection = collection;
    }

    // Méthodes CRUD...
    // Créer
    public void createUser(JsonObject user) {
        String id = user.getString("_id");
        userCollection.insert(id, user);
    }

    public void createUsers(List<JsonObject> users) {
        users.forEach(this::createUser);
    }

    // Lire
    public JsonObject getUser(String id) {
        GetResult getResult = userCollection.get(id);
        return getResult.contentAsObject();
    }

    public List<JsonObject> getUsers(List<String> ids) {
        return ids.stream()
                .map(this::getUser)
                .collect(Collectors.toList());
    }

    public List<JsonObject> getAllUsers() {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Users`", userCollection.name());
        QueryResult result = cluster.query(statement);
        return result.rowsAsObject();
    }

    // Mettre à jour
    public void updateUser(String id, JsonObject updatedUser) {
        userCollection.replace(id, updatedUser);
    }

    public void updateUsers(Map<String, JsonObject> usersMap) {
        usersMap.forEach(this::updateUser);
    }

    // Supprimer
    public void deleteUser(String id) {
        userCollection.remove(id);
    }

    public void deleteUsers(List<String> ids) {
        ids.forEach(this::deleteUser);
    }

    // Méthodes de recherche
    public List<JsonObject> getUsersNotVerified() {
        String statement = String.format("SELECT * FROM `mtest`.`tester`.`Users` WHERE isVerified = false", userCollection.name());
        QueryResult result = cluster.query(statement);
        return result.rowsAsObject();
    }

}
