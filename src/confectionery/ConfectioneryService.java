package confectionery;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import confectionery.Model.*;
import confectionery.Repository.IRepository;

/**
 * The service layer computes the information received from the controller and applies business logic to the operations
 * used in each of the processes started in the console.
 * It has direct access to the repositories, so it can match and compare the data received from the controller to
 * the existent data in each repository.
 */
public class ConfectioneryService {

    private final IRepository<Cake> menu;
    private final IRepository<Drink> drink;
    private final IRepository<Order> orderRepository;
    private final IRepository<User> users;
    private int orderIdCounter = 1;
    private User loggedInUser;

    /**
     *
     * @param menu The repository of cakes
     * @param drink The repository of drinks
     * @param orderRepository The repository of the stored orders
     * @param users The repository of all users
     */
    public ConfectioneryService(IRepository<Cake> menu, IRepository<Drink> drink, IRepository<Order> orderRepository, IRepository<User> users) {
        this.menu = menu;
        this.drink = drink;
        this.orderRepository = orderRepository;
        this.users = users;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    /**
     * gets a list of all cakes
     * @return the available cakes
     */
    public List<Cake> getCakes() {
        return menu.getAll();
    }

    /**
     * gets a list of all drinks
     * @return the available drinks
     */

    public List<Drink> getDrinks() {
        return drink.getAll();
    }

    /**
     *  gets a list of all users
     *  @return the users
     */
    public List<User> getUsers() {
        return users.getAll();
    }
    /**

     * @param name The name of the admin.
     * @param address The address of the admin.
     * @param email The email address of the admin.
     * @param password The password for the admin account.
     * @param id The unique ID of the admin.
     */
    public void createAdmin(String name, String address, String email, String password, int id) {
        Admin admin = new Admin(password, email, id, name, address);
        users.create(admin);
    }

    /**
     * @param name The name of the client
     * @param address The adress of the client
     * @param id The id of the client
     */
    public void createClient(String name, String address, int id) {
        Client client = new Client(name, address, id);
        users.create(client);
    }

    /**
     * Authenticates an admin based on email and password.
     * @param email The email of the admin.
     * @param password The password of the admin.
     * @return true if authentication is successful, false otherwise.
     */

    public boolean authenticateAdmin(String email, String password) {
        Admin administrator = (Admin) users.getAll().stream().filter(user -> user instanceof Admin && (((Admin) user).getEmail().equals(email) && ((Admin) user).getPassword().equals(password))).findFirst().orElse(null);
        if (administrator != null) {
            loggedInUser = administrator;
            return true;
        }
        return false;
    }
    /**
     * Authenticates an client based on the username
     * @param username The username of the client
     * @return true if authentication is successful, false otherwise.
     */

    public boolean authenticateClient(String username) {
        Client client = (Client) users.getAll().stream().filter(user -> user instanceof Client && ((Client) user).getUsername().equals(username)).findFirst().orElse(null);
        if (client != null) {
            loggedInUser = client;
            return true;
        }
        return false;
    }

    /**
     * Places an order by adding selected cakes and drinks to an order and saving it to the repository.
     * @param cakeIds A list of cake IDs to be added to the order.
     * @param drinkIds A list of drink IDs to be added to the order.
     * @return true if the order is successfully placed, false if no products were selected.
     */

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
    /**
     * Deletes an order by ID from both the order repository and the client's order list.
     * @param id The ID of the order to be deleted.
     */

    public void deleteOrder(int id) {
        orderRepository.delete(id); // Remove from the repo
        ((Client) loggedInUser).deleteById(id); // And from the client
    }

    /**
     * change the ponts from a product
     * @param id the id from the product
     */
    public void productUpdate(int id){
        Cake cake=menu.get(id);
        Drink drink1=drink.get(id);
        if (cake != null ) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Current product points: " + cake.getPoints());
            System.out.print("Enter new points (leave empty to keep current points): ");
            String newPoints = scanner.nextLine();
            if (!newPoints.isEmpty()) {
                int newPoint = Integer.parseInt(newPoints);
                cake.setPoints(newPoint);
            }
            menu.update(cake);
            System.out.println("Product updated successfully!");
        } else if(drink1 != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Current product points: " + drink1.getPoints());
            System.out.print("Enter new points (leave empty to keep current points): ");
            String newPoints = scanner.nextLine();
            if (!newPoints.isEmpty()) {
                int newPoint = Integer.parseInt(newPoints);
                drink1.setPoints(newPoint);
            }
            drink.update(drink1);
            System.out.println("Product updated successfully!");
        }
        else
            System.out.println("Product with ID " + id + " not found.");
        }


    /**
     * Generates an invoice for the logged-in client based on their orders.
     */
    public void getInvoice() {
        ((Client) loggedInUser).getInvoice();
    }

    /**
     * @return The total balance calculated from all orders.
     */
    public float getBalanceTotal() {
        Balance balance = new Balance();
        balance.addOrders(orderRepository.getAll());
        for (Order order : orderRepository.getAll()) {
            System.out.println("Order" + order.getID() + ": " + order.getTotal());
        }
        return balance.calculateTotalBalance();
    }


    /**
     * prints the month with the total amount
     *  @param month The month for which the balance should be calculated.
     */
    public void getMonthlyBalance(int month) {
        double monthlyBalance = orderRepository.getAll().stream().filter(order -> order.getDate().getMonth() == Month.of(month)).mapToDouble(Order::getTotal).sum();
        System.out.println("Monthly balance for month " + month + " of the current year: " + monthlyBalance + " lei");
    }

    /**
     * prints the year with the total amount
     *  @param year The year for which the balance should be calculated.
     */
    public void getYearlyBalance(int year) {
        double yearlyBalance = orderRepository.getAll().stream().filter(order -> order.getDate().getYear() == year).mapToDouble(Order::getTotal).sum();
        System.out.println("Yearly balance for year " + year + ": " + yearlyBalance + " lei");
    }

    /**
     * Gets the client with the most points.
     * @return The client with the highest number of points.
     */
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


    /**
     * method changes the admin password
     * @param newPassword the new password that is created
     * @return true ,if the password has been changed successfully, false otherwise
     */

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
