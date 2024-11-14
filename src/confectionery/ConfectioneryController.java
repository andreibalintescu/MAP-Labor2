package confectionery;

import confectionery.Model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * The controller acts as an intermediary between the console and the service.
 * It computes the users choices from the console,
 * gathers information from the inputs introduced by the user and
 * displays messages to the console, signaling the success or failure of different processes.
 */
public class ConfectioneryController {

    private final ConfectioneryService confectioneryService;

    public ConfectioneryController(ConfectioneryService confectioneryService) {
        this.confectioneryService = confectioneryService;
    }

    /**
     * Prompts the user to create an account as either an Admin or Client.
     * The method will ask for user details to create the account.
     * @param scanner The scanner object to capture user input.
     */

    public void createAccount(Scanner scanner) {
        System.out.println("Choose your role: Admin or Client.");
        String role = scanner.nextLine();
        if (role.equals("Admin")) {
            System.out.println("Enter your name:");
            String name = scanner.nextLine();
            System.out.println("Enter your address:");
            String address = scanner.nextLine();
            System.out.println("Enter your email:");
            String email = scanner.nextLine();
            System.out.println("Enter your password:");
            String password = scanner.nextLine();
            int id = generateId();
            confectioneryService.createAdmin(name, address, email, password, id);
            System.out.println("Admin account created successfully!");
        } else if (role.equals("Client")) {
            System.out.println("Enter your name:");
            String name = scanner.nextLine();
            System.out.println("Enter your address:");
            String address = scanner.nextLine();
            int id = generateId();
            confectioneryService.createClient(name, address, id);
            System.out.println("Client account created successfully!");
        } else System.out.println("Failed to create account!");
    }

    /**
     * Authenticates the admin based on email and password.
     * @param scanner The scanner object to capture user input.
     * @return true if login is successful, false otherwise.
     */

    public boolean loginAdmin(Scanner scanner) {
        System.out.print("Enter email:");
        String email = scanner.nextLine();
        System.out.print("Enter password:");
        String password = scanner.nextLine();
        if (confectioneryService.authenticateAdmin(email, password)) {
            System.out.println("You have logged in as administrator!");
            return true;
        }
        System.out.println("Wrong email or password!");
        return false;
    }

    /**
     * Authenticates the client based on username
     * @param scanner The scanner object to capture user input.
     * @return true if login is successful, false otherwise.
     */
    public boolean loginClient(Scanner scanner) {
        System.out.print("Enter username:");
        String username = scanner.nextLine();
        if (confectioneryService.authenticateClient(username)) {
            System.out.println("You have logged in as a client!");
            return true;
        }
        System.out.println("Failed to log in!");
        return false;
    }

    /**
     * Displays the menu of cakes and drinks available for purchase order by price
     */


     void viewMenuPrice(){
         StringBuilder output = new StringBuilder("Cakes :\n");
         List<Cake> sortedCakes=new ArrayList<>(confectioneryService.getCakes());
         sortedCakes.sort(Comparator.comparing(Cake::getPrice));

         List<Drink> sortedDrinks=new ArrayList<>(confectioneryService.getDrinks());
         sortedDrinks.sort(Comparator.comparing(Drink::getPrice));
         sortedCakes.forEach(cake -> output.append(cake.toString()).append("\n"));
         output.append(" Drinks : \n");
         sortedDrinks.forEach(drink -> output.append(drink.toString()).append("\n"));
         System.out.println(output);
     }

    /**
     * Displays the menu of cakes and drinks available for purchase order by price
     */

    void viewMenuPoints(){
        StringBuilder output = new StringBuilder("Cakes :\n");
        List<Cake> sortedCakes=new ArrayList<>(confectioneryService.getCakes());
        sortedCakes.sort(Comparator.comparing(Cake::getPoints));

        List<Drink> sortedDrinks=new ArrayList<>(confectioneryService.getDrinks());
        sortedDrinks.sort(Comparator.comparing(Drink::getPoints));
        sortedCakes.forEach(cake -> output.append(cake.toString()).append("\n"));
        output.append("Drinks :\n");
        sortedDrinks.forEach(drink -> output.append(drink.toString()).append("\n"));
        System.out.println(output);
    }

    /**
     * filter the Drinks which have Alcohol
     */
    void filterByAlcohol(){
        StringBuilder output = new StringBuilder("Drinks :\n");
        List<Drink> filterDrinks=new ArrayList<>(confectioneryService.getDrinks());
        filterDrinks.stream().filter(drink -> drink.getAlcoholPercentage() > 0).forEach(drink -> output.append(drink.toString()).append("\n"));
        System.out.println(output);

    }

    /**
     * filter the Product available until December 2024
     */
    void filterByExpirationDate(){
        StringBuilder output = new StringBuilder("Cakes :\n");
        List<Cake> filterCakes=new ArrayList<>(confectioneryService.getCakes());
        filterCakes.stream().filter(cake->cake.getExpirationDate().getYear() <=2024).forEach(filterCake -> output.append(filterCake.toString()).append("\n"));

        output.append("Drinks :\n");
        List<Drink> filterDrinks=new ArrayList<>(confectioneryService.getDrinks());
        filterDrinks.stream().filter(drink -> drink.getExpirationDate().getYear() == 2024 && (drink.getExpirationDate().getMonth()==Month.November || drink.getExpirationDate().getMonth()==Month.December )).forEach(drink -> output.append(drink.toString()).append("\n"));
        System.out.println(output);
    }
    /**
     * Displays a list of all registered clients in the system.
     */
    public void viewUsers() {
        StringBuilder output = new StringBuilder("List of clients registered in the system: :\n");
        confectioneryService.getUsers().stream().filter(user -> user instanceof Client).forEach(user -> output.append(user).append("\n"));

        System.out.println(output);
    }

    /**
     * Displays the client with the most points and their total points.
     */

    public void viewMostPoints() {
        User client = confectioneryService.getClientWithMostPoints();
        System.out.println("The client with the most points is  ");
        System.out.println(client);
        Client client1 = (Client) client;
        System.out.println("Total points: " + client1.grandTotalPoints());

    }
    /**
     * Allows the client to place an order by entering cake and drink IDs.
     * @param scanner The scanner object to capture user input.
     */

    public void placeOrder(Scanner scanner) {
        System.out.println("Enter the IDs of cakes you want to order separated by space:");
        String cakeIdsInput = scanner.nextLine();
        List<Integer> cakeIds = parseIds(cakeIdsInput);

        System.out.println("Enter the IDs of drinks you want to order separated by space:");
        String drinkIdsInput = scanner.nextLine();
        List<Integer> drinkIds = parseIds(drinkIdsInput);

        if (confectioneryService.placeOrder(cakeIds, drinkIds)) System.out.println("Your order has been placed!");
        else System.out.println("Failed to place order!.");

    }
    /**
     * Allows the client to cancel a previously placed order.
     * @param scanner The scanner object to capture user input.
     */

    public void cancelOrder(Scanner scanner) {
        System.out.println("Enter the Id of the order you would like to delete from the receipt or 0 to cancel the request:");
        int id = Integer.parseInt(scanner.nextLine());
        if (id != 0) {
            confectioneryService.deleteOrder(id);
            System.out.println("Order canceled successfully!");
        } else System.out.println("Request canceled!");
    }

    /**
     * Displays the total balance of the confectionery.
     */

    public void getBalanceTotal() {
        float balance = confectioneryService.getBalanceTotal();
        System.out.println("The total balance  is: " + balance + " lei");
    }

    /**
     * Generates and displays the balance for a specific month.
     * @param scanner The scanner object to capture user input.
     */

    public void generateMonthlyBalance(Scanner scanner) {
        System.out.println("Enter the desired month:");
        int month = Integer.parseInt(scanner.nextLine());
        confectioneryService.getMonthlyBalance(month);

    }
    /**
     * Generates and displays the balance for a specific year.
     * @param scanner The scanner object to capture user input.
     */

    public void generateYearlyBalance(Scanner scanner) {
        System.out.println("Enter the desired year:");
        int year = Integer.parseInt(scanner.nextLine());
        confectioneryService.getYearlyBalance(year);
    }
    /**
     * Generates and prints an invoice for the current period.
     */

    public void generateInvoice() {
        System.out.println("Generating invoice...\n");
        confectioneryService.getInvoice();
    }


    /**
     * Displays the profile information of the user.
     */

    public void getProfile() {
        System.out.println("Here is information about your profile:");
        System.out.println(confectioneryService.getLoggedInUser().toString());
    }

    //Misc.
    /**
     * Parses a space-separated string of IDs into a list of integers.
     * @param input The space-separated string of IDs.
     * @return A list of integers representing the IDs.
     */
    private List<Integer> parseIds(String input) {
        List<Integer> ids = new ArrayList<>();
        if (!input.isEmpty()) {
            String[] parts = input.split(" ");
            for (String part : parts) {
                ids.add(Integer.parseInt(part));
            }
        }
        return ids;
    }

    /**
     * Generates a random ID for a user.
     * @return A randomly generated integer ID.
     */

    private int generateId() {
        return (int) (Math.random() * 1000);
    }

    /**
     * Allows the user to change their password.
     * @param scanner The scanner object to capture user input.
     */

    public void changePassword(Scanner scanner) {
        System.out.println("Enter your new password:");
        String newPassword = scanner.nextLine();
        System.out.println("Retype your new password:");
        String retypePassword = scanner.nextLine();
        if(newPassword.equals(retypePassword) && confectioneryService.updatePassword(newPassword))
            System.out.println("Password changed successfully!");
        else
            System.out.println("Failed to change password!");
    }
}
