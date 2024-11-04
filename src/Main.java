import confectionery.Model.Cakes;
import confectionery.Model.Drinks;
import confectionery.Model.ExpirationDate;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ExpirationDate expirationDate=new ExpirationDate(2024,10,7);
        List<String> allergens = List.of( "Lactate");
        Cakes cake=new Cakes(1,"Tiramisu",50,200,expirationDate,1200,allergens);
        Drinks drink=new Drinks(1,"Coca Cola",10,20,expirationDate,12);
        cake.viewAllergens();
        cake.getCalories();
        cake.viewExpirationDate();
        cake.getPrice();
        cake.getName();
        cake.getWeight();
        drink.viewAlcoholPercentage();
        }
    }
