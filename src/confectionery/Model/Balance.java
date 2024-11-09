package confectionery.Model;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Balance {
    private final List<Order> orders = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrders(List<Order> newOrders) {
        orders.addAll(newOrders);
    }

    public float calculateTotalBalance() {
        return (float) orders.stream().mapToDouble(Order::getTotal).sum();
    }

    // Calculate monthly balance by filtering orders by month using LocalDateTime
    public float monthlyBalance(Month month) {
        return (float) orders.stream()
                .filter(order -> order.getDate().getMonth() == month)
                .mapToDouble(Order::getTotal)
                .sum();
    }

    // Calculate yearly balance by filtering orders by year using LocalDateTime
    public float yearlyBalance(int year) {
        return (float) orders.stream()
                .filter(order -> order.getDate().getYear() == year)
                .mapToDouble(Order::getTotal)
                .sum();
    }
}