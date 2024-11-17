package confectionery.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import confectionery.Model.HasID;
import java.util.ArrayList;
/**
 * A repository implementation that stores data in a file.
 *
 * @param <T> The type of objects stored in the repository, which must implement HasId.
 */
public class FileRepository<T extends HasID> implements IRepository<T> {
    private final String filePath;

    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void create(T obj) {
        doInFile(data -> data.putIfAbsent(obj.getID(), obj));
    }

    @Override
    public T get(Integer id) {
        return readDataFromFile().get(id);
    }

    @Override
    public void update(T obj) {
        doInFile(data -> data.replace(obj.getID(), obj));
    }

    @Override
    public void delete(Integer id) {
        doInFile(data -> data.remove(id));
    }

    @Override
    public List<T> getAll() {
        Map<Integer, T> data = readDataFromFile();
        if (data == null) {
            return new ArrayList<>(); // Return an empty list if the map is null
        }
        return data.values().stream().toList();
    }

    private void doInFile(Consumer<Map<Integer, T>> function) {
        Map<Integer, T> data = readDataFromFile();
        function.accept(data);
        writeDataToFile(data);
    }

    private Map<Integer, T> readDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Map<Integer, T>) ois.readObject();
        } catch (EOFException e) {
            // File is empty, return an empty map
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }



    private void writeDataToFile(Map<Integer, T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
