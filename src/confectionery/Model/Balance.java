package confectionery.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Balance class manages a list of orders and products, and provides methods to calculate total, monthly, and yearly balances based on the orders.
 * This class is used to track financial transactions in the system.
 */
public class Balance {
    /**
     * a list of orders and products
     */
    private final List<Order> orders = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();

    /**
     * gets a list of products
     *
     * @return a list of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * gets a list of orders
     *
     * @return a list of orders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Adds a list of new orders to the current list of orders.
     *
     * @param newOrders The list of new orders to be added.
     */
    public void addOrders(List<Order> newOrders) {
        orders.addAll(newOrders);
    }

    /**
     * Calculates the total balance by summing the total amount of all orders.
     *
     * @return The total balance of all orders.
     */
    public float calculateTotalBalance() {
        return (float) orders.stream().mapToDouble(Order::getTotal).sum();
    }
}