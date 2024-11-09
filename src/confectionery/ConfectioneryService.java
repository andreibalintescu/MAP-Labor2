package confectionery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import confectionery.Model.*;
import confectionery.Repository.Repository;


public class ConfectioneryService {

    private final Repository<Cake> menu;
    private final Repository<Drink> drink;
    private final Repository<Order> orderRepository;
    private final Repository<User> users;
    private int orderIdCounter = 1;
    private User loggedInUser;

    public ConfectioneryService(Repository<Cake> menu, Repository<Drink> drink, Repository<Order> orderRepository, Repository<User> users) {
        this.menu = menu;
        this.drink = drink;
        this.orderRepository = orderRepository;
        this.users = users;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public List<Cake> getCakes() {
        return menu.getAll();
    }

    public List<Drink> getDrinks() {
        return drink.getAll();
    }

    public List<User> getUsers() {
        return users.getAll();
    }

    public boolean placeOrder(List<Integer> cakeIds, List<Integer> drinkIds) {
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
        if(products.isEmpty()) return false;

        Order order = new Order(products, orderIdCounter++, LocalDate.now());
        orderRepository.create(order); // Add the Order in the Repository

        ((Client)loggedInUser).placeOrder(order); // Add Order internally in the current Client
        return true;

    }


    private Cake findCakeById(int id) {
        for (Cake cake : menu.getAll()) {
            if (cake.getID() == id) {
                return cake;
            }
        }
        return null;
    }

    private Drink findDrinkById(int id) {
        for (Drink drink : drink.getAll()) {
            if (drink.getID() == id) {
                return drink;
            }
        }
        return null;
    }

    public float getBalanceT() {
        Balance balance = new Balance();
        balance.addOrders(orderRepository.getAll());
        for (Order order : orderRepository.getAll()) {
            System.out.println(order.getTotal());
        }
        return balance.calculateTotalBalance();
    }


    public boolean authenticateAdmin(String email, String password) {
        Admin administrator = (Admin) users.getAll().stream()
                .filter(user -> user instanceof Admin && (((Admin) user).getEmail().equals(email) && ((Admin) user).getPassword().equals(password)))
                .findFirst().orElse(null);
        if (administrator != null) {
            loggedInUser = administrator;
            return true;
        }
        return false;
    }

    public boolean authenticateClient(String username) {
        Client client = (Client) users.getAll().stream()
                .filter(user -> user instanceof Client && ((Client) user).getUsername().equals(username))
                .findFirst().orElse(null);
        if (client != null) {
            loggedInUser = client;
            return true;
        }
        return false;
    }

    void getInvoice(){
        ((Client) loggedInUser).getInvoice();
    }
}
