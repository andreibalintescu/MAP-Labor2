package confectionery;

import java.util.List;

import confectionery.Model.Cake;
import confectionery.Model.Drink;
import confectionery.Repository.Repository;

public class ConfectioneryService {


    private final Repository<Cake> menu;
    private final Repository<Drink> drink;

    public ConfectioneryService(Repository<Cake> menu, Repository<Drink> drinksrepo) {
        this.menu = menu;
        this.drink = drinksrepo;
    }

    public List<Cake> getCakes() {
        return menu.getAll();
    }
    public List<Drink> getDrinks() {
        return drink.getAll();
    }
}