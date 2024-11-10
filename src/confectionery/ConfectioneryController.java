package confectionery;

import confectionery.Model.*;

import java.util.ArrayList;
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

    void viewMenu() {
        StringBuilder output = new StringBuilder("Cakes and Drinks :\n");
        confectioneryService.getCakes().forEach(product -> output.append(product.toString()).append("\n"));
        confectioneryService.getDrinks().forEach(product -> output.append(product.toString()).append("\n"));
        System.out.println(output);
    }

    public void viewUsers() {
        StringBuilder output = new StringBuilder("List of clients registered in the system: :\n");
        confectioneryService.getUsers().stream().filter(user -> user instanceof Client).forEach(user -> output.append(user).append("\n"));

        System.out.println(output);
    }

    public void viewMostPoints() {
        User client = confectioneryService.getClientWithMostPoints();
        System.out.println("The client with the most points is  ");
        System.out.println(client);
        Client client1 = (Client) client;
        System.out.println("Total points: " + client1.grandTotalPoints());

    }

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

    public void cancelOrder(Scanner scanner) {
        System.out.println("Enter the Id of the order you would like to delete from the receipt or 0 to cancel the request:");
        int id = Integer.parseInt(scanner.nextLine());
        if (id != 0) {
            confectioneryService.deleteOrder(id);
            System.out.println("Order canceled successfully!");
        } else System.out.println("Request canceled!");
    }


    public void getBalanceTotal() {
        float balance = confectioneryService.getBalanceTotal();
        System.out.println("The total balance  is: " + balance + " lei");
    }

    public void generateMonthlyBalance(Scanner scanner) {
        System.out.println("Enter the desired month:");
        int month = Integer.parseInt(scanner.nextLine());
        confectioneryService.getMonthlyBalance(month);

    }

    public void generateYearlyBalance(Scanner scanner) {
        System.out.println("Enter the desired year:");
        int year = Integer.parseInt(scanner.nextLine());
        confectioneryService.getYearlyBalance(year);
    }

    public void generateInvoice() {
        System.out.println("Generating invoice...\n");
        confectioneryService.getInvoice();
    }

    public void getProfile() {
        System.out.println("Here is information about your profile:");
        System.out.println(confectioneryService.getLoggedInUser().toString());
    }

    //Misc.
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

    private int generateId() {
        return (int) (Math.random() * 1000);
    }


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
