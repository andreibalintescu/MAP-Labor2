package confectionery;

import java.util.Scanner;

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
                    Please select from the following options:

                        1.View Menu
                        2.Place Order :)
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
                case "2":
                    confectioneryController.placeOrder(scanner);
                    break;
                case "3":
                    confectioneryController.getInvoice(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }



   
    public static void main(String[] args) {
        Repository<Cake> cakesrepo = createInMemoryCakesRepository();
        Repository<Drink> drinksrepo=createInMemoryDrinksRepository();
        Repository<Order> orderRepo = new InMemoryRepository();

        ConfectioneryService confectioneryService = new ConfectioneryService(cakesrepo,drinksrepo,orderRepo);
        ConfectioneryController confectioneryController = new ConfectioneryController(confectioneryService);

        ConsoleApp consoleApp = new ConsoleApp(confectioneryController);

        consoleApp.start();
    }



    private static Repository<Cake> createInMemoryCakesRepository() {
        Repository<Cake> cakeRepository = new InMemoryRepository<>();

        ExpirationDate expirationDate1=new ExpirationDate(2026,Month.February,Day.Eleventh);
        ExpirationDate expirationDate3=new ExpirationDate(2024,Month.December,Day.Eighteenth);
        ExpirationDate expirationDate4=new ExpirationDate(2024,Month.December,Day.First);
        ExpirationDate expirationDate2=new ExpirationDate(2024,Month.November,Day.Thirteenth);
        ExpirationDate expirationDate5=new ExpirationDate(2024,Month.December,Day.Fourteenth);

        cakeRepository.create(new Cake(1,"Tiramisu",100,50,expirationDate3,1000));
        cakeRepository.create(new Cake(2,"Ecler",130,50,expirationDate4,1000));
        cakeRepository.create(new Cake(3,"Dubai Chocolate",200,100,expirationDate2,1200));
        cakeRepository.create(new Cake(4, "Carrot Cake", 110, 55, expirationDate3, 900));
        cakeRepository.create(new Cake(5, "Cheesecake", 200, 100, expirationDate4, 500));
        cakeRepository.create(new Cake(6, "Lemon Cake", 130, 65, expirationDate2, 600));
        cakeRepository.create(new Cake(7, "Apple Pie", 90, 45, expirationDate5, 950));
        cakeRepository.create(new Cake(8, "Vanilla Cake", 100, 50, expirationDate2, 1000));
        cakeRepository.create(new Cake(9, "Pineapple Upside Down Cake", 140, 70, expirationDate5, 650));
        cakeRepository.create(new Cake(10, "Strawberry Shortcake", 160, 80, expirationDate4, 750));


        return cakeRepository;
    }
    private static Repository<Drink> createInMemoryDrinksRepository() {
        Repository<Drink> drinksRepository = new InMemoryRepository<>();
        ExpirationDate expirationDate=new ExpirationDate(2025, Month.April, Day.Fifteenth);
        ExpirationDate expirationDate6 = new ExpirationDate(2025, Month.April, Day.Fifteenth);
        ExpirationDate expirationDate7 = new ExpirationDate(2024, Month.May, Day.TwentyFourth);
        ExpirationDate expirationDate8 = new ExpirationDate(2026, Month.July, Day.TwentyFirst);
        ExpirationDate expirationDate9 = new ExpirationDate(2023, Month.March, Day.Thirteenth);
        ExpirationDate expirationDate10 = new ExpirationDate(2027, Month.January, Day.First);
        drinksRepository.create(new Drink(11,"Water",10,50,expirationDate6,0));
        drinksRepository.create(new Drink(12, "Cappuccino", 15, 200, expirationDate7, 0));
        drinksRepository.create(new Drink(13, "Latte", 14, 250, expirationDate8, 0));
        drinksRepository.create(new Drink(14, "Beer", 19, 200, expirationDate9, 8));
        drinksRepository.create(new Drink(15, "Teqila", 18, 300, expirationDate10, 6));
        drinksRepository.create(new Drink(16, "Mocha", 16, 250, expirationDate6, 5));
        drinksRepository.create(new Drink(17, "Green Tea", 21, 150, expirationDate7, 0));
        drinksRepository.create(new Drink(18, "Black Tea", 20, 150, expirationDate8, 0));
        drinksRepository.create(new Drink(19, "Lemonade", 25, 350, expirationDate9, 0));
        drinksRepository.create(new Drink(20, "Hot Chocolate", 20, 200, expirationDate10, 0));

        return drinksRepository;
    }
}
