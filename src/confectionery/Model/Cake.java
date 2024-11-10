package confectionery.Model;


/**
 * class Cake which inheritances from the main class Product
 * a Client can order different types of cakes
 */
public class Cake extends Product {
    private final int calories;
    public Cake(int idProduct, String name, float price, float weight, ExpirationDate expirationDate,int points, int calories) {
        super(idProduct, name, price, weight, expirationDate,points);
        this.calories=calories;
    }

    /**
     * gets the calories from the Cake
     */
    public int getCalories() {
        return calories;
    }

    /**
     *
     * @return a String method containing the Cake id,name,price,weight,expirationDate,points and calories
     */
    @Override
    public String toString() {
        return getID() + ". " + getName() + ", " + getWeight() + " gr" + ", " + getCalories() + " calories" + "..............  " + getPrice() + "lei" + '\n' +
                "Expires on the " + getExpirationDate().getDay() + " of " + getExpirationDate().getMonth() + " " + getExpirationDate().getYear() + '\n' +
                "Points: " + getPoints();
    }




}

