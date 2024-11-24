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


    /**
     * Gets the name of the user.
     *
     * @return the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the address of the user.
     *
     * @return the user's address.
     */
    public String getAddress() {
        return address;
    }


    public abstract String toString();

}