package confectionery;

import java.util.Scanner;
import java.util.stream.IntStream;

import confectionery.ConfectioneryController;
import confectionery.ConfectioneryService;
import confectionery.Model.*;


import confectionery.Repository.InMemoryRepository;
import confectionery.Repository.Repository;

public class ConsoleApp {

    private final ConfectioneryController confectioneryController;

    public ConsoleApp(ConfectioneryController confectioneryController) {
        this.confectioneryController = confectioneryController;
    }

    
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("""
                    Select an option:

                    1.View Menu
                    2.Place Order
                    3.GetInvoice
                    0. Exit
                    """);

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    confectioneryController.viewMenu();
                    break;

                default:
            }
        }
    }


   
    public static void main(String[] args) {
        Repository<Product> menurepo = createInMemoryMenuRepository();

        ConfectioneryService confectioneryService = new ConfectioneryService(menurepo);
        ConfectioneryController confectioneryController = new ConfectioneryController(confectioneryService);

        ConsoleApp consoleApp = new ConsoleApp(confectioneryController);

        consoleApp.start();
    }

    
    
    private static Repository<Product> createInMemoryMenuRepository() {
        Repository<Product> menuRepo = new InMemoryRepository<>();
        ExpirationDate expirationDate=new ExpirationDate(2025, Month.April, Day.Fifteenth);
        menuRepo.create(new Cake(1,"Tiramisu",100,50,expirationDate,1000));
        menuRepo.create(new Cake(2,"Ecler",130,50,expirationDate,1000));

        return menuRepo;
    }
}