package confectionery;

import confectionery.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfectioneryController {

    private final ConfectioneryService confectioneryService ;

    public ConfectioneryController(ConfectioneryService confectioneryService) {
        this.confectioneryService = confectioneryService;
    }


     void viewMenu() {
        StringBuilder output = new StringBuilder("Cakes and Drinks :\n");
        confectioneryService.getCakes().forEach(product -> output.append(product.toString()).append("\n"));
        confectioneryService.getDrinks().forEach(product -> output.append(product.toString()).append("\n"));
        System.out.println(output);
    }

    public void viewUsers() {
        StringBuilder output = new StringBuilder("Users :\n");
        confectioneryService.getUsers().forEach(user -> output.append(user.toString()).append("\n"));
        System.out.println(output);
    }

    public void placeOrder(Scanner scanner) {
        System.out.println("Enter the IDs of cakes you want to order with a space between");
        String cakeIdsInput = scanner.nextLine();
        List<Integer> cakeIds = parseIds(cakeIdsInput);

        System.out.println("Enter the IDs of drinks you want to order with a space between");
        String drinkIdsInput = scanner.nextLine();
        List<Integer> drinkIds = parseIds(drinkIdsInput);


        Order order = confectioneryService.placeOrder(cakeIds, drinkIds);

        Client loggedInClient = (Client) confectioneryService.getLoggedInUser(); // Obținem clientul logat
        if (loggedInClient != null) {
            loggedInClient.placeOrder(order);
            System.out.println("Your order has been placed. Order ID: " + order.getID());
        } else {
            System.out.println("No client logged in.");
        }
    }


    public boolean loginAdmin(Scanner scanner) {
        System.out.print("Enter email:");
        String email = scanner.nextLine();
        System.out.print("Enter password:");
        String password = scanner.nextLine();
        if(confectioneryService.authenticateAdmin(email, password)) {
            System.out.println("You have logged in as administrator!");
            return true;
        }
        System.out.println("Failed to log in!");
        return false;
    }
    public boolean loginClient(Scanner scanner) {
        System.out.print("Enter username:");
        String username = scanner.nextLine();

        Client client = (Client) confectioneryService.getUsers().stream()
                .filter(user -> user instanceof Client && ((Client) user).getUsername().equals(username))
                .findFirst().orElse(null);

        if (client != null) {
            confectioneryService.setLoggedInUser(client);
            System.out.println("You have logged in as a client!");
            return true;
        }
        System.out.println("Failed to log in!");
        return false;
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

    public void gettBalance() {
        float balance = confectioneryService.getBalance();
        System.out.println("The total balance for this month is: " + balance + "lei");

    }

    public void generateInvoice() {
        Client loggedInClient = (Client) confectioneryService.getLoggedInUser(); // Obținem clientul logat
        loggedInClient.getInvoice();
    }
}
