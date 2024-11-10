package confectionery;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import confectionery.Model.*;
import confectionery.Repository.Repository;

/**
 * The service layer computes the information received from the controller and applies business logic to the operations
 * used in each of the processes started in the console.
 * It has direct access to the repositories, so it can match and compare the data received from the controller to
 * the existent data in each repository.
 */
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

    public void createAdmin(String name, String address, String email, String password, int id) {
        Admin admin = new Admin(password, email, id, name, address);
        users.create(admin);
    }

    public void createClient(String name, String address, int id) {
        Client client = new Client(name, address, id);
        users.create(client);
    }

    public boolean authenticateAdmin(String email, String password) {
        Admin administrator = (Admin) users.getAll().stream().filter(user -> user instanceof Admin && (((Admin) user).getEmail().equals(email) && ((Admin) user).getPassword().equals(password))).findFirst().orElse(null);
        if (administrator != null) {
            loggedInUser = administrator;
            return true;
        }
        return false;
    }

    public boolean authenticateClient(String username) {
        Client client = (Client) users.getAll().stream().filter(user -> user instanceof Client && ((Client) user).getUsername().equals(username)).findFirst().orElse(null);
        if (client != null) {
            loggedInUser = client;
            return true;
        }
        return false;
    }

    public boolean placeOrder(List<Integer> cakeIds, List<Integer> drinkIds) {
        List<Product> products = new ArrayList<>();
        Order order = new Order(products, orderIdCounter++, LocalDate.now());

        for (int cakeId : cakeIds) {
            if (menu.get(cakeId) != null) order.addProduct(menu.get(cakeId));
        }

        for (int drinkId : drinkIds) {
            if (drink.get(drinkId) != null) order.addProduct(drink.get(drinkId));
        }

        if (order.getProducts().isEmpty()) return false;

        orderRepository.create(order); // Add the Order in the Repository

        ((Client) loggedInUser).placeOrder(order); // Add Order internally in the current Client
        return true;

    }

    public void deleteOrder(int id) {
        orderRepository.delete(id); // Remove from the repo
        ((Client) loggedInUser).deleteById(id); // And from the client
    }

    public void getInvoice() {
        ((Client) loggedInUser).getInvoice();
    }

    public float getBalanceTotal() {
        Balance balance = new Balance();
        balance.addOrders(orderRepository.getAll());
        for (Order order : orderRepository.getAll()) {
            System.out.println("Order" + order.getID() + ": " + order.getTotal());
        }
        return balance.calculateTotalBalance();
    }

    public void getMonthlyBalance(int month) {
        double monthlyBalance = orderRepository.getAll().stream().filter(order -> order.getDate().getMonth() == Month.of(month)).mapToDouble(Order::getTotal).sum();
        System.out.println("Monthly balance for month " + month + " of the current year: " + monthlyBalance + " lei");
    }

    public void getYearlyBalance(int year) {
        double yearlyBalance = orderRepository.getAll().stream().filter(order -> order.getDate().getYear() == year).mapToDouble(Order::getTotal).sum();
        System.out.println("Yearly balance for year " + year + ": " + yearlyBalance + " lei");
    }

    public User getClientWithMostPoints() {
        User clientWithMostPoints = null;
        int maxPoints = 0;

        for (User user : users.getAll()) {
            if (user instanceof Client) {
                int clientPoints = ((Client) user).grandTotalPoints();

                if (clientPoints > maxPoints) {
                    maxPoints = clientPoints;
                    clientWithMostPoints = user;
                }
            }
        }
        return clientWithMostPoints;
    }


    public boolean updatePassword(String newPassword) {
        if(newPassword.equals(((Admin)loggedInUser).getPassword()))
            return false;
        else
        {
            ((Admin)loggedInUser).setPassword(newPassword);
            return true;
        }
    }
}
