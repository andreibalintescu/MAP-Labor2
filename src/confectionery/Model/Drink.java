package confectionery.Model;

/**
 * class Drink which inheritances from the main class Product
 * a Client can order different types of drinks
 */
public class Drink extends Product {
    private final float alcoholPercentage;
    public Drink(int idProduct, String name, float price, float weight, ExpirationDate expirationDate,int points, float alcohol) {
        super(idProduct, name, price, weight, expirationDate,points);
        this.alcoholPercentage = alcohol;
    }

    /**
     * gets the alcohol from the Drinks
     */
    public  float getAlcoholPercentage() {
        return alcoholPercentage;
    }

    /**
     * a toString method with the attributes
     * @return the drink with the id,name,price,weight,expirationDate,points and alcohol
     */
    @Override
    public String toString() {
        return getID() + ". " + getName() + ", " + getWeight() + " ml" + ", " + getAlcoholPercentage() + "% alc" + "..............  " + getPrice() + "lei" + '\n' +
                "Expires on the " + getExpirationDate().getDay() + " of " + getExpirationDate().getMonth() + " " + getExpirationDate().getYear() + '\n' +
                "Points: " + getPoints();
    }


}
