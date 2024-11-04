package confectionery.Model;


public class Cake extends Product {
    int calories;
    public Cake(int idProduct, String name, float price, float weight, ExpirationDate expirationDate, int calories) {
        super(idProduct, name, price, weight, expirationDate);
        this.calories=calories;
    }
    public void getCalories() {
        System.out.println("Calories: "+calories);
    }

    }

