package confectionery.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order implements HasID {
    private List<Product> products=new ArrayList<>();
    private float totalPrice;
    private Integer orderID;
    private LocalDate date;

    public Order(List<Product> products, Integer orderID, LocalDate date) {
        this.products = products;
        this.orderID = orderID;
        this.date = date;
    }

    public float getTotal() {
        totalPrice = 0;
        for (Product p : this.products) {
            this.totalPrice += p.getPrice();
        }
        return this.totalPrice;
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
