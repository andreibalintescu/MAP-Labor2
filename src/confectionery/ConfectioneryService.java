package confectionery;

import java.util.List;

import confectionery.Model.Cake;
import confectionery.Model.Drink;
import confectionery.Repository.Repository;

public class ConfectioneryService {


    private final Repository<Cake> menu;
    private final Repository<Drink> drink;

    public ConfectioneryService(Repository<Cake> menu, Repository<Drink> drink) {
        this.menu = menu;
        this.drink = drink;
    }

    public List<Cake> getCakes() {
        return menu.getAll();
    }
    public List<Drink> getDrinks() {
        return drink.getAll();
    }

    public void orderCake(Integer productId) {
        Cake orderedCake= menu.get(productId);
        orderedCake.getOrderedCakes().add(orderedCake);
        menu.update(orderedCake);

        }

    public void orderDrink(Integer productId) {
        Drink orderedDrink = drink.get(productId);
        orderedDrink.getOrderdDrinks().add(orderedDrink);
        drink.update(orderedDrink);

    }
}