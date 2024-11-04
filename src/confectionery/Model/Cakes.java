package confectionery.Model;

import java.util.List;

public class Cakes extends Product {
    int calories;
    List<String> Allergens;
    public Cakes(int idProduct, String name, float price, float weight, ExpirationDate expirationDate, int calories, List<String> Allergens) {
        super(idProduct, name, price, weight, expirationDate);
        this.calories=calories;
        this.Allergens=Allergens;
    }
    public void getCalories() {
        System.out.println("Calories: "+calories);
    }
    public void viewAllergens(){
        System.out.print("Allergens : ");
        for (String allergen : Allergens)
                System.out.println(allergen+" ");

    }
}
