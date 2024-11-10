package confectionery.Model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * class Cake which inheritances from the main class Product
 * a Client can order different types of cakes
 */
public class Cake extends Product {
    int calories;
    public Cake(int idProduct, String name, float price, float weight, ExpirationDate expirationDate,int points, int calories) {
        super(idProduct, name, price, weight, expirationDate,points);
        this.calories=calories;
    }

    /**
     * gets the calories from the Cake
     */
    public void getCalories() {
        System.out.println("Calories: "+calories);
    }

    /**
     *
     * @return a String method containing the Cake id,name,price,weight,expirationDate,points and calories
     */
    @Override
    public String toString() {
        return "Cake{" +
                "id="+getID()+
                ", name='"+getName()+
                ", price="+getPrice()+
                ", weight="+getWeight()+
                ","+getExpirationDate()+
                ", points="+getPoints()+
                "calories=" + calories +
                '}';
    }




}

