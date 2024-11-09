package confectionery.Model;

import java.time.LocalDate;
import java.util.List;

public class Order implements HasID {
    private final List<Product> products;
    private final Integer orderID;
    private final LocalDate date;

    public Order(List<Product> products, Integer orderID, LocalDate date) {
        this.products = products;
        this.orderID = orderID;
        this.date = date;
    }

    public float getTotal() {
        float totalPrice = 0;
        for (Product p : this.products) {
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }
    public int getTotalPoints(){
        int totalPoints = 0;
        for (Product p : this.products) {
            totalPoints += p.getPoints();
        }
        return totalPoints;
    }
    public void addProduct(Product p) {
        this.products.add(p);
    }

    public LocalDate getDate() {
        return date;
    }


    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Integer getID() {
        return this.orderID;
    }


}
