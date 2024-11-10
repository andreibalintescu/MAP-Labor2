package confectionery.Model;

import java.time.Month;
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
     * @return a list of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * gets a list of orders
     * @return a list of orders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Adds a list of new orders to the current list of orders.
     * @param newOrders The list of new orders to be added.
     */
    public void addOrders(List<Order> newOrders) {
        orders.addAll(newOrders);
    }

    /**
     * Calculates the total balance by summing the total amount of all orders.
     * @return The total balance of all orders.
     */
    public float calculateTotalBalance() {
        return (float) orders.stream().mapToDouble(Order::getTotal).sum();
    }

    /**
     * Calculate monthly balance by filtering orders by month using LocalDateTime
     * @param month The month for which the balance is being calculated.
     * @return The total balance for the specified month.
     */

    public float monthlyBalance(Month month) {
        return (float) orders.stream()
                .filter(order -> order.getDate().getMonth() == month)
                .mapToDouble(Order::getTotal)
                .sum();
    }

    /**
     * Calculate yearly balance by filtering orders by year using LocalDateTime
     * @param year The year for which the balance is being calculated.
     * @return  The total balance for the specified year.
     */

    public float yearlyBalance(int year) {
        return (float) orders.stream()
                .filter(order -> order.getDate().getYear() == year)
                .mapToDouble(Order::getTotal)
                .sum();
    }
}