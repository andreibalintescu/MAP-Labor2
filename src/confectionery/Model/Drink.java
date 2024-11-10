package confectionery.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * class Drink which inheritances from the main class Product
 * a Client can order different types of drinks
 */
public class Drink extends Product {
    float alcoholPercentage;
    public Drink(int idProduct, String name, float price, float weight, ExpirationDate expirationDate,int points, float alcohol) {
        super(idProduct, name, price, weight, expirationDate,points);
        this.alcoholPercentage = alcohol;
    }

    /**
     * gets the alcohol from the Drinks
     */
    public void viewAlcoholPercentage() {
        System.out.println("Alcohol Percentage: " + alcoholPercentage);
    }

    /**
     * a toString method with the attributes
     * @return the drink with the id,name,price,weight,expirationDate,points and alcohol
     */
    @Override
    public String toString() {
        return "Drink{" +
                "id="+getID()+
                ", name='"+getName()+
                ", price="+getPrice()+
                ", weight="+getWeight()+
                "," +getExpirationDate()+
                ", points="+getPoints()+
                ", alcoholPercentage=" + alcoholPercentage +
                '}';
    }


}
