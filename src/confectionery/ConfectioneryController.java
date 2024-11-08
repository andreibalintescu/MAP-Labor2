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


    // General functions
    public void authenticate(Scanner scanner) {

    }
    public void viewMenu() {
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
    // Client-specific functions
    public boolean loginClient(Scanner scanner) {
        return confectioneryService.authenticateClient();

    }

    public void placeOrder(Scanner scanner) {
        System.out.println("Enter the IDs of cakes you want to order with a space between");
        String cakeIdsInput = scanner.nextLine();
        List<Integer> cakeIds = parseIds(cakeIdsInput);

        System.out.println("Enter the IDs of drinks you want to order with a space between");
        String drinkIdsInput = scanner.nextLine();
        List<Integer> drinkIds = parseIds(drinkIdsInput);


        Order order = confectioneryService.placeOrder(cakeIds, drinkIds);
        System.out.println("Your order has been placed. Order ID: " + order.getID());
    }

    public void getInvoice(Scanner scanner) {
        System.out.print("Enter your Order ID to get the invoice: ");
        int orderId = Integer.parseInt(scanner.nextLine());

        Order order = confectioneryService.getOrderById(orderId);
        if (order != null) {
            System.out.println("Invoice for Order ID: " + order.getID());
            System.out.println("Total: " + order.getTotal());
        } else {
            System.out.println("Order not found!");
        }
    }

    //Admin-specific functions
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
    public void getMonthlyBalance() {

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


}
