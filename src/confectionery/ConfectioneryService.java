package confectionery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import confectionery.Model.*;
import confectionery.Repository.Repository;

public class ConfectioneryService {


    private final Repository<Cake> menu;
    private final Repository<Drink> drink;
    private final Repository<Order> orderRepository;
    private final Repository<User> users;
    private int orderIdCounter = 1;


    public ConfectioneryService(Repository<Cake> menu, Repository<Drink> drink, Repository<Order> orderRepository, Repository<User> users) {
        this.menu = menu;
        this.drink = drink;
        this.orderRepository = orderRepository;
        this.users = users;
    }

    public List<Cake> getCakes() {
        return menu.getAll();
    }
    public List<Drink> getDrinks() {
        return drink.getAll();
    }
    public List<User> getUsers() {return users.getAll();}


    public Order placeOrder(List<Integer> cakeIds, List<Integer> drinkIds) {
        List<Product> products = new ArrayList<>();

        for (int cakeId : cakeIds) {
            Cake cake = findCakeById(cakeId);
            if (cake != null) {
                products.add(cake);
            }
        }

        for (int drinkId : drinkIds) {
            Drink drink = findDrinkById(drinkId);
            if (drink != null) {
                products.add(drink);
            }
        }

        Order order = new Order(products, orderIdCounter++, LocalDate.now());
        orderRepository.create(order);
        return order;
    }


    private Cake findCakeById(int id) {
        for (Cake cake : menu.getAll()) {
            if(cake.getID() == id) {
                return cake;
            }
        }
            return null;
    }


    private Drink findDrinkById(int id) {
        for(Drink drink : drink.getAll()) {
            if(drink.getID() == id) {
                return drink;
            }
        }
        return null;
    }


    public Order getOrderById(int orderId) {
        for(Order order : orderRepository.getAll()) {
            if(order.getID() == orderId) {
                return order;
            }
        }
        return null;
    }

    public boolean authenticateAdmin(String email, String password) {
               return users.getAll().stream().filter(user -> user instanceof Admin)
                .map(user -> (Admin) user) // Downcast to access attributes
                .anyMatch(admin -> admin.getEmail().equals(email) && admin.getPassword().equals(password));

    }
    public boolean authenticateClient() {
        return true;
    }
}