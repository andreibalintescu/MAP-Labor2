package confectionery;

import confectionery.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfectioneryController {

    private final ConfectioneryService confectioneryService;

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
        StringBuilder output = new StringBuilder("List of clients registered in the system: :\n");
        confectioneryService.getUsers().stream().filter(user -> user instanceof Client).forEach(user -> output.append(user).append("\n"));
        System.out.println(output);
    }

    public void placeOrder(Scanner scanner) {
        System.out.println("Enter the IDs of cakes you want to order with a space between");
        String cakeIdsInput = scanner.nextLine();
        List<Integer> cakeIds = parseIds(cakeIdsInput);

        System.out.println("Enter the IDs of drinks you want to order with a space between");
        String drinkIdsInput = scanner.nextLine();
        List<Integer> drinkIds = parseIds(drinkIdsInput);

        if (confectioneryService.placeOrder(cakeIds, drinkIds))
            System.out.println("Your order has been placed!");
        else
            System.out.println("Failed to place order!.");

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


    public void getBalanceTotal() {
        float balance = confectioneryService.getBalanceT();
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


}
