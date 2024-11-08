package confectionery.Model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Cake extends Product {
    int calories;
    public Cake(int idProduct, String name, float price, float weight, ExpirationDate expirationDate, int calories) {
        super(idProduct, name, price, weight, expirationDate);
        this.calories=calories;
    }
    public void getCalories() {
        System.out.println("Calories: "+calories);
    }

    @Override
    public String toString() {
        return "Cake{" +
                "id="+getID()+
                ", name='"+getName()+
                ", price="+getPrice()+
                ", weight="+getWeight()+
                ","+getExpirationDate()+
                "calories=" + calories +
                '}';
    }




}

