package confectionery.Model;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client extends User {
    private final Integer ClientID;
    private List<Order> orders = new ArrayList<>();
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
            System.out.println("You have no orders yet.");
            return;
        }
        System.out.println("Your ordered products:");
        for (Order order : this.orders) {
            for (Product product : order.getProducts()) {
                System.out.println(product.getName() + "................................." + product.getPrice() + "lei");
            }
            System.out.println("Your total is: " + order.getTotal());
        }
    }

    public String toString(){
        return "Client: " + "id " + ClientID + "," + "name " + name + "," + "address " + address;
    }

    public Object getUsername() {
        return this.name;
    }
}
