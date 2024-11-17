package confectionery.Repository;

import confectionery.Model.HasID;


import java.io.*;
import java.util.*;

public class CSVFileRepository<T extends HasID> extends FileRepository<T> {
    private final String filePath;

    public CSVFileRepository(String filePath) {
        super(filePath);
        this.filePath = filePath;
    }

    @Override
    public void create(T obj) {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            // Serialize the object to a CSV string
            String csvLine = serializeToCSV(obj);
            out.println(csvLine);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T get(Integer id) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                T obj = deserializeFromCSV(line);
                if (obj != null && obj.getID().equals(id)) {
                    return obj;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(T obj) {
        // Read all data, update the matching object, and write it back to the file
        List<T> allData = getAll();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (T item : allData) {
                if (item.getID().equals(obj.getID())) {
                    out.println(serializeToCSV(obj));
                } else {
                    out.println(serializeToCSV(item));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        // Read all data, remove the matching object, and write the rest back to the file
        List<T> allData = getAll();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (T item : allData) {
                if (!item.getID().equals(id)) {
                    out.println(serializeToCSV(item));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> getAll() {
        List<T> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                T obj = deserializeFromCSV(line);
                if (obj != null) {
                    data.add(obj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String serializeToCSV(T obj) {
        // Implement a method to convert obj to a CSV string
        // Example: "1,Name,Value" based on your object fields
        return obj.getID() + "," + obj; // Modify as needed
    }

    private T deserializeFromCSV(String csvLine) {
        // Implement a method to convert a CSV string back to an object
        // Example: split the csvLine and create a new object
        return null; // Implement deserialization logic
    }
}