package confectionery.Model;

public abstract class User implements HasID {
    protected String name;
    protected String address;

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public abstract boolean login();

}
