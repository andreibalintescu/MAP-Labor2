package confectionery.Model;

import java.io.*;

/**
 * main class User ,which implemets the HasId interface
 * there are 2 tpes of users ->client and admin
 */
public abstract class User implements HasID, Serializable {

    protected String name;
    protected String address;

    /**
     *
     * @param name represents the username
     * @param address represents the user adress
     */
    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public User(){

    }

    // Custom serialization method
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Serialize non-transient fields
        oos.writeObject(name);
        oos.writeObject(address);
    }

    // Custom deserialization method
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Deserialize non-transient fields
        name = (String) ois.readObject();
        address = (String) ois.readObject();
    }

    public abstract String toString();

}
