package confectionery.Model;

import java.util.Scanner;

/**
 * main class User ,which implemets the HasId interface
 * there are 2 tpes of users ->client and admin
 */
public abstract class User implements HasID {

    protected String name;
    protected String address;

    /**
     *
     * @param name represents the user name
     * @param address represents the user adress
     */
    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public abstract boolean login(Scanner scanner);

}
