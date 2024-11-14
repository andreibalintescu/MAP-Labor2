package confectionery;

import java.util.Scanner;

import confectionery.Model.*;


import confectionery.Repository.InMemoryRepository;
import confectionery.Repository.IRepository;

/**
 * The class ConsoleApp represents the implementation of the interface layer.
 * Its sole role is to display relevant text in the console, during runtime.
 */
public class ConsoleApp {

    private final ConfectioneryController confectioneryController;
    private final Scanner scanner;

    public ConsoleApp(ConfectioneryController confectioneryController) {
        this.confectioneryController = confectioneryController;
        this.scanner = new Scanner(System.in);
    }


    public void start() {
        boolean running = true;

        while (running) {
            System.out.print("""
                    Welcome to the Confectionery App!
                    1. Create account
                    2. Login as Administrator
                    3. Login as Client
                    0. Exit
                    Please select an option:
                    """);

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    confectioneryController.createAccount(scanner);
                    break;
                case "2":
                    if (confectioneryController.loginAdmin(scanner))
                        adminMenu();
                    break;
                case "3":
                    if (confectioneryController.loginClient(scanner))
                        clientMenu();
                    break;
                case "0":
                    System.out.println("Thank you for using Confectionery!");
                    running = false;
                    break;
            }
        }

    }

    private void clientMenu() {
        boolean clientRunning = true;
        while (clientRunning) {
            System.out.print("""
                    Client Menu:
                    0. View Menu order by Points
                    1. View Menu order by Price
                    2. Place Order
                    3. Cancel Order
                    4. Generate Invoice
                    5. View Profile
                    6. View Drinks With Alcohol
                    7. View Products available until December 2024
                    00. Logout
                    Please select an option:
                    """);

            String option = scanner.nextLine();
            switch (option) {
                case "0" ->confectioneryController.viewMenuPoints();
                case "1" -> confectioneryController.viewMenuPrice();
                case "2" -> confectioneryController.placeOrder(scanner);
                case "3" -> confectioneryController.cancelOrder(scanner);
                case "4" -> confectioneryController.generateInvoice();
                case "5" -> confectioneryController.getProfile();
                case "6" ->confectioneryController.filterByAlcohol();
                case "7" ->confectioneryController.filterByExpirationDate();
                case "00" -> clientRunning = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void adminMenu() {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.print("""
                    Admin Menu:
                    1. View Total Balance
                    2. View Monthly Balance
                    3. View Yearly Balance
                    4. View Users
                    5. View Profile
                    6. View Client with the most Points
                    7. Change Password
                    0. Logout
                    Please select an option:
                    """);

            String option = scanner.nextLine();
            switch (option) {
                case "1" -> confectioneryController.getBalanceTotal();
                case "2" -> confectioneryController.generateMonthlyBalance(scanner);
                case "3" -> confectioneryController.generateYearlyBalance(scanner);
                case "4" -> confectioneryController.viewUsers();
                case "5" -> confectioneryController.getProfile();
                case "6" -> confectioneryController.viewMostPoints();
                case "7" -> confectioneryController.changePassword(scanner);
                case "0" -> adminRunning = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Start point of the application.
     * This is where the in-memory repositories are created.
     * The service is given direct access to these repositories.
     * The controller is then connected to the service.
     * Finally, the application console starts with all the components laid in place.
     */
    public static void main(String[] args) {
        IRepository<Cake> cakeRepo = createInMemoryCakesRepository();
        IRepository<Drink> drinkRepo = createInMemoryDrinksRepository();
        IRepository<Order> orderRepo = new InMemoryRepository<>();
        IRepository<User> userRepo = createInMemoryUsersRepository();

        ConfectioneryService confectioneryService = new ConfectioneryService(cakeRepo, drinkRepo, orderRepo, userRepo);
        ConfectioneryController confectioneryController = new ConfectioneryController(confectioneryService);

        ConsoleApp consoleApp = new ConsoleApp(confectioneryController);

        consoleApp.start();
    }

    /**
     * Initializes the cake repository with relevant data.
     *
     * @return a cake repository.
     */
    private static IRepository<Cake> createInMemoryCakesRepository() {
        IRepository<Cake> cakeRepository = new InMemoryRepository<>();

        ExpirationDate expirationDate1 = new ExpirationDate(2026, Month.February, Day.Eleventh);
        ExpirationDate expirationDate3 = new ExpirationDate(2024, Month.December, Day.Eighteenth);
        ExpirationDate expirationDate4 = new ExpirationDate(2024, Month.December, Day.First);
        ExpirationDate expirationDate2 = new ExpirationDate(2024, Month.November, Day.Thirteenth);
        ExpirationDate expirationDate5 = new ExpirationDate(2024, Month.December, Day.Fourteenth);

        cakeRepository.create(new Cake(1, "Tiramisu", 100, 50, expirationDate3, 140, 1000));
        cakeRepository.create(new Cake(2, "Eclair", 130, 50, expirationDate4, 120, 1000));
        cakeRepository.create(new Cake(3, "Dubai Chocolate", 200, 100, expirationDate2, 99, 1200));
        cakeRepository.create(new Cake(4, "Carrot Cake", 110, 55, expirationDate3, 68, 900));
        cakeRepository.create(new Cake(5, "Cheesecake", 200, 100, expirationDate1, 100, 500));
        cakeRepository.create(new Cake(6, "Lemon Cake", 130, 65, expirationDate2, 75, 600));
        cakeRepository.create(new Cake(7, "Apple Pie", 90, 45, expirationDate5, 60, 950));
        cakeRepository.create(new Cake(8, "Vanilla Cake", 100, 50, expirationDate2, 108, 1000));
        cakeRepository.create(new Cake(9, "Pineapple Upside Down Cake", 140, 70, expirationDate5, 95, 650));
        cakeRepository.create(new Cake(10, "Strawberry Shortcake", 160, 80, expirationDate4, 90, 750));


        return cakeRepository;
    }

    /**
     * Initializes the drink repository with relevant data.
     *
     * @return a drink repository.
     */
    private static IRepository<Drink> createInMemoryDrinksRepository() {
        IRepository<Drink> drinksRepository = new InMemoryRepository<>();
        ExpirationDate expirationDate5 = new ExpirationDate(2025, Month.April, Day.Fifteenth);
        ExpirationDate expirationDate6 = new ExpirationDate(2025, Month.April, Day.Fifteenth);
        ExpirationDate expirationDate7 = new ExpirationDate(2024, Month.May, Day.TwentyFourth);
        ExpirationDate expirationDate8 = new ExpirationDate(2026, Month.July, Day.TwentyFirst);
        ExpirationDate expirationDate9 = new ExpirationDate(2023, Month.March, Day.Thirteenth);
        ExpirationDate expirationDate10 = new ExpirationDate(2027, Month.January, Day.First);
        drinksRepository.create(new Drink(11, "Water", 10, 50, expirationDate6, 30, 0));
        drinksRepository.create(new Drink(12, "Cappuccino", 15, 200, expirationDate7, 45, 0));
        drinksRepository.create(new Drink(13, "Latte", 14, 250, expirationDate8, 68, 0));
        drinksRepository.create(new Drink(14, "Beer", 19, 200, expirationDate9, 10, 8));
        drinksRepository.create(new Drink(15, "Tequila", 18, 300, expirationDate10, 15, 6));
        drinksRepository.create(new Drink(16, "Mocha", 16, 250, expirationDate6, 46, 5));
        drinksRepository.create(new Drink(17, "Green Tea", 21, 150, expirationDate7, 17, 0));
        drinksRepository.create(new Drink(18, "Black Tea", 20, 150, expirationDate5, 12, 0));
        drinksRepository.create(new Drink(19, "Lemonade", 25, 350, expirationDate9, 17, 0));
        drinksRepository.create(new Drink(20, "Hot Chocolate", 20, 200, expirationDate10, 22, 0));

        return drinksRepository;
    }

    /**
     * Initializes the user repository with relevant data.
     *
     * @return a user repository.
     */
    private static IRepository<User> createInMemoryUsersRepository() {
        IRepository<User> usersRepository = new InMemoryRepository<>();
        usersRepository.create(new Client("Andrei", "Bujoreni", 123));
        usersRepository.create(new Client("Maria", "Bujoreni", 312));
        usersRepository.create(new Client("Ioana", "Bujoreni", 231));
        usersRepository.create(new Admin("admin", "admin@gmail.com", 333, "Bali", "Bujoreni"));
        return usersRepository;
    }
}
