package confectionery;

import java.util.Comparator;

public class ConfectioneryController {

    private final ConfectioneryService confectioneryService ;

    public ConfectioneryController(ConfectioneryService confectioneryService) {
        this.confectioneryService = confectioneryService;
    }

    public void viewMenu() {
        StringBuilder output = new StringBuilder("Cakes and Drinks :\n");
        confectioneryService.getCakes().forEach(product -> output.append(product.toString()).append("\n"));
        confectioneryService.getDrinks().forEach(product -> output.append(product.toString()).append("\n"));
        System.out.println(output);
    }

    public void placeOrderCake(Integer productId) {
        confectioneryService.orderCake(productId);
        System.out.println("order placed for product with id " + productId );
    }
    public void placeOrderDrink(Integer productId) {
        confectioneryService.orderDrink(productId);
        System.out.println("order placed for product with id " + productId );
    }
}
