package confectionery.Model;

import java.util.ArrayList;
import java.util.List;

public class Drink extends Product {
    float alcoholPercentage;
    private List<Drink> orderedDrinks = new ArrayList<>();
    public Drink(int idProduct, String name, float price, float weight, ExpirationDate expirationDate, float alcohol) {
        super(idProduct, name, price, weight, expirationDate);
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
                ", alcoholPercentage=" + alcoholPercentage +
                '}';
    }

    public List<Drink> getOrderdDrinks() {
        return orderedDrinks;
    }
}
