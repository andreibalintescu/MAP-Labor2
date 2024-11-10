package confectionery.Model;

/**
 * class Product which implements the HasId interface
 */

public class Product implements HasID {

    private final int idProduct;
    private String name;
    private float price;
    private float weight;
    ExpirationDate expirationDate;
    int points;

    /**
     * @param idProduct the product id
     * @param name the product name
     * @param price the product price
     * @param weight the product weight
     * @param expirationDate the product expiration date
     * @param points the product points
     */
    public Product(int idProduct, String name, float price, float weight, ExpirationDate expirationDate,int points) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.expirationDate = expirationDate;
        this.points = points;

    }

    /**
     * prints the expiration date from the product
     */
    public void viewExpirationDate() {
        System.out.print("Expiration Date :");
        System.out.print(expirationDate.getDay() + ".");
        System.out.print(expirationDate.getMonth() + ".");
        System.out.println(expirationDate.getYear() + ".");
    }

    /**
     * prints the price of the product
     */
    public void printPrice() {
        System.out.println("Price: " + price);
    }
    /**
     * prints the weight of the product
     */
    public void printWeight() {
        System.out.println("Weight: " + weight);
    }

    /**
     * prints the name of the product
     */
    public void printName() {
        System.out.println("Name: " + name);
    }

    /**
     * @return the product id
     */
    public int getIdProduct() {
        return idProduct;
    }

    /**
     * @return the mane
     */
    public String getName() {
        return name;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @return the weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * @return the expiration date
     */
    public ExpirationDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * @return the product id
     */
    @Override
    public Integer getID() {
       return idProduct;
    }


    /**
     * @return the points from the product
     */
    public int getPoints() {
        return points;
    }

    /**
     * a string method which includes the Product id,name,price,weight,expirationDate,points
     * @return
     */
    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", expirationDate=" + expirationDate +
                ", points=" + points +
                '}';
    }
}
