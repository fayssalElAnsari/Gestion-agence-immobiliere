package moteur.couchbase.src.services;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import moteur.couchbase.src.models.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {
    private final Cluster cluster;
    private final Collection userCollection;

    public UserService(Cluster cluster, String database, String collection) {
        this.cluster = cluster;
        this.userCollection = cluster.bucket(database).defaultCollection();
    }

    // Méthodes CRUD...
    // Créer
    public void createUser(User user) {
        userCollection.insert(user.getId(), user);
    }

    public void createUsers(List<User> users) {
        users.forEach(this::createUser);
    }

    // Lire
    public User getUser(String id) {
        GetResult getResult = userCollection.get(id);
        return getResult.contentAs(User.class);
    }

    public List<User> getUsers(List<String> ids) {
        return ids.stream()
                .map(this::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        String statement = String.format("SELECT * FROM `%s` WHERE type = 'User'", userCollection.name());
        QueryResult result = cluster.query(statement);
        return result.rowsAs(User.class);
    }

    // Mettre à jour
    public void updateUser(String id, User updatedUser) {
        userCollection.replace(id, updatedUser);
    }

    public void updateUsers(Map<String, User> usersMap) {
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
    public List<User> getUsersNotVerified() {
        String statement = String.format("SELECT * FROM `%s` WHERE type = 'User' AND isVerified = false", userCollection.name());
        QueryResult result = cluster.query(statement);
        return result.rowsAs(User.class);
    }

}
