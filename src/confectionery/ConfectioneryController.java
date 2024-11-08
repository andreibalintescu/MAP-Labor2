package confectionery;

import confectionery.Model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfectioneryController {

    private final ConfectioneryService confectioneryService ;

    public ConfectioneryController(ConfectioneryService confectioneryService) {
        this.confectioneryService = confectioneryService;
    }

    public void viewMenu() {
        StringBuilder output = new StringBuilder("Cakes and Drinks :\n");
        confectioneryService.getCakes().forEach(product -> output.append(product.toString()).append("\n"));
        confectioneryService.getDrinks().forEach(product -> output.append(product.toString()).append("\n"));
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
