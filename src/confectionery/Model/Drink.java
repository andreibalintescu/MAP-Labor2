package confectionery.Model;

import java.util.ArrayList;
import java.util.List;

public class Drink extends Product {
    float alcoholPercentage;
    public Drink(int idProduct, String name, float price, float weight, ExpirationDate expirationDate,int points, float alcohol) {
        super(idProduct, name, price, weight, expirationDate,points);
        this.alcoholPercentage = alcohol;
    }
    public void viewAlcoholPercentage() {
        System.out.println("Alcohol Percentage: " + alcoholPercentage);
    }

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
