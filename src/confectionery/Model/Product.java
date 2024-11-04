package confectionery.Model;

public class Product implements HasID {

    private final int idProduct;
    private String name;
    private float price;
    private float weight;
    ExpirationDate expirationDate;

    public Product(int idProduct, String name, float price, float weight, ExpirationDate expirationDate) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.expirationDate = expirationDate;

    }

    public void viewExpirationDate() {
        System.out.print("Expiration Date :");
        System.out.print(expirationDate.getDay() + ".");
        System.out.print(expirationDate.getMonth() + ".");
        System.out.println(expirationDate.getYear() + ".");
    }

    public void printPrice() {
        System.out.println("Price: " + price);
    }

    public void printWeight() {
        System.out.println("Weight: " + weight);
    }

    public void printName() {
        System.out.println("Name: " + name);
    }

    public int getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public float getWeight() {
        return weight;
    }

    public ExpirationDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public Integer getID() {
       return idProduct;
    }
}
