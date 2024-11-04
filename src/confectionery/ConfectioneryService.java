package confectionery;

import java.util.List;

import confectionery.Model.Product;
import confectionery.Repository.Repository;

public class ConfectioneryService {


    private final Repository<Product> menu;


    public ConfectioneryService(Repository<Product> menu) {
        this.menu = menu;
    }

    public List<Product> getMenu() {
        return menu.getAll();
    }
}