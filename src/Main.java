
import confectionery.Model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        ExpirationDate expirationDate = new ExpirationDate(2024, Month.November, Day.Fourth);
        Cake cake1 = new Cake(1, "Chocolate Cake", 70, 23, expirationDate, 658);
        Cake cake2 = new Cake(2, "Strawberry Cake", 95, 18, expirationDate, 526);
        Drink drink1 = new Drink(1, "Coffee", 16, 300, expirationDate, 0);
        Client client1 = new Client("Andrei", "Bujoreni", 123, orders );

        products.add(cake1);
        products.add(cake2);
        products.add(drink1);
        LocalDate today = LocalDate.now();
        Order order = new Order(products, 12, today);
        client1.placeOrder(order);
        client1.getInvoice();
        Balance balance = new Balance(client1.getOrders());
        Admin admin = new Admin("admin","admin@gmail.com", 333, "Bali", "Bujoreni", balance);
        balance.monthlyBalance(order.getDate().getMonth());
    }
}