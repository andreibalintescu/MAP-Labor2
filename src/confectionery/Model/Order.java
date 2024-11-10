package confectionery.Model;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an order made by a client, which contains a list of Products objects,the order ID, and the date the order was placed.
 * Provides methods to calculate the total price and total points of the order and to add products to the order.
 **/

public class Order implements HasID {
    private final List<Product> products;
    private final Integer orderID;
    private final LocalDate date;

    /**
     * @param products A list of products included in the order.
     * @param orderID  The unique identifier for this order
     * @param date     The date when the order was placed.
     */
    public Order(List<Product> products, Integer orderID, LocalDate date) {
        this.products = products;
        this.orderID = orderID;
        this.date = date;
    }

    /**
     * Calculates the total price of the order by summing the prices of all products in the order
     *
     * @return The total price of the order.
     */
    public float getTotal() {
        float totalPrice = 0;
        for (Product p : this.products) {
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }

    /**
     * Calculates the total points for the order by summing the points associated with all products.
     *
     * @return The total points of the order.
     */
    public int getTotalPoints() {
        int totalPoints = 0;
        for (Product p : this.products) {
            totalPoints += p.getPoints();
        }
        return totalPoints;
    }

    /**
     * Adds a new product to the order.
     *
     * @param p the product which is added to the order
     */
    public void addProduct(Product p) {
        this.products.add(p);
    }

    /**
     * Returns the date when the order was placed.
     *
     * @return the date of the Order
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the list of products in the order.
     *
     * @return the products list
     */

    public List<Product> getProducts() {
        return products;
    }

    /**
     * Returns the unique ID of the order.
     *
     * @return The unique order ID.
     */
    @Override
    public Integer getID() {
        return this.orderID;
    }


}
