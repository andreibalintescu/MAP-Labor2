package confectionery.Model;

import confectionery.Model.Order;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Balance {
    private float balance;
    private List<Order> orders=new ArrayList<>();

//    public Balance(List<Order> orders) {
//        this.orders = orders;
//        this.balance = calculateTotalBalance();
//    }

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
