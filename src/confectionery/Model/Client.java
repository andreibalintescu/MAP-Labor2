package confectionery.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client extends User {
    private final Integer ClientID;
    private final List<Order> orders = new ArrayList<>();
    public Client(String name, String address, Integer Id) {
        super(name, address);
        this.ClientID = Id;
    }

    @Override
    public boolean login(Scanner scanner) {
        System.out.print("Press Enter to log in as Client: ");
        String input = scanner.nextLine();
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
        if (this.orders.isEmpty()) {
            System.out.println("You have no orders yet. + \n");
            return;
        }
        for (Order order : this.orders) {
            System.out.println("Order Id:" + order.getID());
            for (Product product : order.getProducts()) {
                System.out.println(product.getName() + "................................." + product.getPrice() + "lei");
            }
            System.out.println("Your total for this order is: " + order.getTotal() + "lei" + "\n");
        }
        if(orders.size() > 1)
            System.out.println("Your grand total is:" + this.grandTotal() + "lei");
    }

    public String toString(){
        return "Client: " + "id " + ClientID + "," + "name " + name + "," + "address " + address;
    }

    public Object getUsername() {
        return this.name;
    }

    public float grandTotal() {
        float total = 0;
        for (Order order : this.orders) {
            total += order.getTotal();
        }
        return total;
    }
}
