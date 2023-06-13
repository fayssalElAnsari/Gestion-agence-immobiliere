package moteur.couchbase.src.services;

import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;

import java.util.List;
import java.util.stream.Collectors;

public class Clients {

    private Collection collection;

    public Clients(Collection collection) {
        this.collection = collection;
    }

    // Insert single record
    public MutationResult insert(String id, JsonObject content) throws DocumentExistsException {
        return collection.insert(id, content);
    }

    // Insert multiple records
    public List<MutationResult> insertMultiple(List<String> ids, List<JsonObject> contents) throws DocumentExistsException {
        return ids.stream()
                .map(id -> collection.insert(id, contents.get(ids.indexOf(id))))
                .collect(Collectors.toList());
    }

    // Update single record
    public MutationResult update(String id, JsonObject content) throws DocumentNotFoundException {
        return collection.replace(id, content);
    }

    // Update multiple records
    public List<MutationResult> updateMultiple(List<String> ids, List<JsonObject> contents) throws DocumentNotFoundException {
        return ids.stream()
                .map(id -> collection.replace(id, contents.get(ids.indexOf(id))))
                .collect(Collectors.toList());
    }

    // Delete single record
    public void delete(String id) throws DocumentNotFoundException {
        collection.remove(id);
    }

    // Delete multiple records
    public void deleteMultiple(List<String> ids) throws DocumentNotFoundException {
        ids.forEach(id -> collection.remove(id));
    }

    // Select single record
    public GetResult select(String id) throws DocumentNotFoundException {
        return collection.get(id);
    }

    // Select multiple records
    public List<GetResult> selectMultiple(List<String> ids) throws DocumentNotFoundException {
        return ids.stream()
                .map(id -> collection.get(id))
                .collect(Collectors.toList());
    }
}
