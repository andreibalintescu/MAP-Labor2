package confectionery.Model;

import java.sql.SQLOutput;
import java.util.List;

public class Client extends User {
    private final Integer ClientID;
    private List<Order> orders;

    public Client(String name, String address, Integer Id, List<Order> orders) {
        super(name, address);
        this.ClientID = Id;
        this.orders = orders;
    }

    @Override
    public boolean login() {
        System.out.print("Press Enter to log in as Client: ");
        String input = new java.util.Scanner(System.in).nextLine();
        return input.isEmpty();
    }

    @Override
    public Integer getID() {
        return this.ClientID;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void placeOrder(Order order) {
        this.orders.add(order);
    }

    public void getInvoice(){
        System.out.println("Your ordered products:");
        for (Order order : this.orders) {
            for (Product product : order.getProducts()) {
                System.out.println(product.getName() + "................................." + product.getPrice() + "lei");
            }
            System.out.println("Your total is: " + order.getTotal());
        }
    }
}
