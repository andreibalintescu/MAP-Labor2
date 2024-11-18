package confectionery.Repository;

import confectionery.Model.HasID;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A repository implementation that stores data in a CSV file.
 *
 * @param <T> The type of objects stored in the repository.
 */
public class CSVFileRepository<T extends HasID> implements IRepository<T> {
    private final String filePath;
    private final Function<String[], T> deserializer;
    private final Function<T, String[]> serializer;

    public CSVFileRepository(String filePath, Function<String[], T> deserializer, Function<T, String[]> serializer) {
        this.filePath = filePath;
        this.deserializer = deserializer;
        this.serializer = serializer;
    }

    @Override
    public void create(T obj) {
        if (getAll().stream().anyMatch(existing -> existing.getID().equals(obj.getID()))) {
            System.out.println("Object with ID " + obj.getID() + " already exists. Skipping creation.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(String.join(",", serializer.apply(obj)));
            writer.newLine();
            System.out.println("Created object with ID " + obj.getID());
        } catch (IOException e) {
            System.err.println("Error while creating object: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public T get(Integer id) {
        T found = getAll().stream().filter(obj -> obj.getID().equals(id)).findFirst().orElse(null);
        if (found == null) {
            System.out.println("Object with ID " + id + " not found.");
        } else {
            System.out.println("Retrieved object with ID " + id);
        }
        return found;
    }

    @Override
    public void update(T obj) {
        List<T> allObjects = getAll();
        boolean updated = false;

        for (int i = 0; i < allObjects.size(); i++) {
            if (allObjects.get(i).getID().equals(obj.getID())) {
                allObjects.set(i, obj);
                updated = true;
                System.out.println("Updated object with ID " + obj.getID());
                break;
            }
        }

        if (updated) {
            writeAll(allObjects);
        } else {
            System.out.println("Object with ID " + obj.getID() + " not found. Skipping update.");
        }
    }

    @Override
    public void delete(Integer id) {
        List<T> allObjects = getAll();
        boolean removed = allObjects.removeIf(obj -> obj.getID().equals(id));

        if (removed) {
            writeAll(allObjects);
            System.out.println("Deleted object with ID " + id);
        } else {
            System.out.println("Object with ID " + id + " not found. Skipping deletion.");
        }
    }

    @Override
    public List<T> getAll() {
        List<T> objects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                T obj = deserializer.apply(fields);
                if (obj != null) {
                    objects.add(obj);
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return objects;
    }

    private void writeAll(List<T> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T obj : objects) {
                writer.write(String.join(",", serializer.apply(obj)));
                writer.newLine();
            }
            System.out.println("Successfully wrote all objects to file.");
        } catch (IOException e) {
            System.err.println("Error while writing file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
