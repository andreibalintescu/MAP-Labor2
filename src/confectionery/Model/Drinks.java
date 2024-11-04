package confectionery.Model;

public class Drinks extends Product {
    float alcoholPercentage;
    public Drinks(int idProduct, String name, float price, float weight, ExpirationDate expirationDate, float alcohol) {
        super(idProduct, name, price, weight, expirationDate);
        this.alcoholPercentage = alcohol;
    }
    public void viewAlcoholPercentage() {
        System.out.println("Alcohol Percentage: " + alcoholPercentage);
    }
}
