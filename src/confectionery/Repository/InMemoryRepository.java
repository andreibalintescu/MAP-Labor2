package confectionery.Repository;

import confectionery.Model.HasID;
import confectionery.Model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A repository implementation that stores data in memory.
 *
 * @param <T> The type of objects stored in the repository, which must implement HasId.
 */
public class InMemoryRepository<T extends HasID> implements IRepository<T> {
    private final Map<Integer, T> data = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(T obj) {
        data.putIfAbsent(obj.getID(), obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(Integer id) {
        return data.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(T obj) {
        data.replace(obj.getID(), obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        data.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll() {
        return data.values().stream().toList();
    }
    @Override
    public void associateOrderWithClient(int orderId, int clientId) {
        Order order = (Order) data.get(orderId); // Assuming you cast appropriately
        if (order != null) {
            order.setClientID(clientId); // Update the in-memory association
        }
    }

}