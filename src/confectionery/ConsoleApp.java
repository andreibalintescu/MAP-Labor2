package confectionery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import confectionery.Exception.EntityNotFoundException;
import confectionery.Model.*;
import confectionery.Repository.DataBase.CakeDBRepository;
import confectionery.Repository.DataBase.DrinkDBRepository;
import confectionery.Repository.DataBase.OrderDBRepository;
import confectionery.Repository.DataBase.UserDBRepository;
import confectionery.Repository.FileRepository;
import confectionery.Repository.InMemoryRepository;
import confectionery.Repository.IRepository;
import confectionery.Exception.ValidationException;

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
            try {
                System.out.print("""
                    Welcome to the Confectionery App!
                    1. Create account
                    2. Login as Administrator
                    3. Login as Client
                    0. Exit
                    Please select an option:
                    """);

                String option = scanner.nextLine();


                if (!isValidOption(option, "1", "2", "3", "0")) {
                    throw new ValidationException("Invalid option. Please select a valid option.");
                }

                switch (option) {
                    case "1":
                        confectioneryController.createAccount(scanner);
                        break;
                    case "2":
                        if (confectioneryController.loginAdmin(scanner)) {
                            adminMenu();
                        }
                        break;
                    case "3":
                        if (confectioneryController.loginClient(scanner)) {
                            clientMenu();
                        }
                        break;
                    case "0":
                        System.out.println("Thank you for using Confectionery!");
                        running = false;
                        break;
                    default:
                        throw new ValidationException("Invalid option. Please select a valid option.");
                }
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }



    private void clientMenu() {
        boolean clientRunning = true;
        while (clientRunning) {
            try {
                System.out.print("""
                    Client Menu:
                    1. View Menu order by Points
                    2. View Menu order by Price
                    3. Place Order
                    4. Generate Invoice
                    5. View Profile
                    6. View Drinks With Alcohol
                    7. View Products available until December 2024
                    0. Logout
                    Please select an option:
                    """);

                String option = scanner.nextLine().trim();

                if (!isValidOption(option, "1", "2", "3", "4", "5", "6", "7", "0")) {
                    throw new ValidationException("Invalid option. Please select a valid option.");
                }


                switch (option) {
                    case "1" -> confectioneryController.viewMenuPoints();
                    case "2" -> confectioneryController.viewMenuPrice();
                    case "3" -> confectioneryController.placeOrder(scanner);
                    case "4" -> confectioneryController.generateInvoice();
                    case "5" -> confectioneryController.getProfile();
                    case "6" -> confectioneryController.filterByAlcohol();
                    case "7" -> confectioneryController.filterByExpirationDate();
                    case "0" -> clientRunning = false;
                }
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    private void adminMenu() {
        boolean adminRunning = true;
        while (adminRunning) {
            try {
                System.out.print("""
                    Admin Menu:
                    1. View Total Balance
                    2. View Monthly Balance
                    3. View Yearly Balance
                    4. View Users with their Orders
                    5. View Profile
                    6. View Client with the most Points
                    7. Change Password
                    8. Update Products
                    9. Delete User
                    0. Logout
                    Please select an option:
                    """);

                String option = scanner.nextLine().trim();


                if (!isValidOption(option, "1", "2", "3", "4", "5", "6", "7", "8", "9", "0")) {
                    throw new ValidationException("Invalid option. Please select a valid option.");
                }

                switch (option) {
                    case "1" -> confectioneryController.getBalanceTotal();
                    case "2" -> confectioneryController.generateMonthlyBalance(scanner);
                    case "3" -> confectioneryController.generateYearlyBalance(scanner);
                    case "4" -> confectioneryController.viewUsers();
                    case "5" -> confectioneryController.getProfile();
                    case "6" -> confectioneryController.viewMostPoints();
                    case "7" -> confectioneryController.changePassword(scanner);
                    case "8" -> confectioneryController.updateProduct(scanner);
                    case "9" -> confectioneryController.deleteUser(scanner);
                    case "0" -> adminRunning = false;
                }
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
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
        //in memory
//        IRepository<Cake> cakeRepo = createInMemoryCakesRepository();
//        IRepository<Drink> drinkRepo = createInMemoryDrinksRepository();
//        IRepository<Order> orderRepo = new InMemoryRepository<>();
//        IRepository<User> userRepo = createInMemoryUsersRepository();


        //filerepository

//        String cakeFilePath = "cakes.csv";
//        String drinkFilePath = "drinks.csv";
//        String userFilePath = "users.csv";
//        String orderFilePath = "orders.csv";
//
//
//        IRepository<Cake> cakeRepo = new FileRepository<>(cakeFilePath);
//        IRepository<Drink> drinkRepo = new FileRepository<>(drinkFilePath);
//        IRepository<User> userRepo = new FileRepository<>(userFilePath);
//        IRepository<Order> orderRepo = new FileRepository<>(orderFilePath);
//
//        intializeCakeRepository(cakeRepo);
//        initializeDrinkRepository(drinkRepo);
//        initializeUserRepository(userRepo);
        
    //databaserepository

        String DB_URL = "jdbc:sqlite:C:/Users/Denisa/JavaProjects/LAB2MAP/src/confectionery.db";
        String DB_USER = "user";
        String DB_PASSWORD = "password";

        IRepository<Cake> cakeRepo = new CakeDBRepository(DB_URL, DB_USER, DB_PASSWORD);
        IRepository<Drink> drinkRepo = new DrinkDBRepository(DB_URL, DB_USER, DB_PASSWORD);
        IRepository<User> userRepo = new UserDBRepository(DB_URL, DB_USER, DB_PASSWORD);
       IRepository<Order> orderRepo = new OrderDBRepository(DB_URL, DB_USER, DB_PASSWORD);

        ConfectioneryService confectioneryService = new ConfectioneryService(cakeRepo, drinkRepo, orderRepo, userRepo);
        ConfectioneryController confectioneryController = new ConfectioneryController(confectioneryService);

        ConsoleApp consoleApp = new ConsoleApp(confectioneryController);

        consoleApp.start();
    }

    /**
     *
     * @param option is the option introduced by the client or admin
     * @param validOptions the option that are correct
     * @return true if the option is right or false otherwise
     */
    private boolean isValidOption(String option, String... validOptions) {
        for (String validOption : validOptions) {
            if (validOption.equals(option)) {
                return true;
            }
        }
        return false;
    }

    private static void intializeCakeRepository(IRepository<Cake> cakeRepo) {
        ExpirationDate expirationDate1 = new ExpirationDate(2026, Month.February, Day.Eleventh);
        ExpirationDate expirationDate3 = new ExpirationDate(2024, Month.December, Day.Eighteenth);
        ExpirationDate expirationDate4 = new ExpirationDate(2024, Month.December, Day.First);
        ExpirationDate expirationDate2 = new ExpirationDate(2024, Month.November, Day.Thirteenth);
        ExpirationDate expirationDate5 = new ExpirationDate(2024, Month.December, Day.Fourteenth);

        cakeRepo.create(new Cake(1, "Tiramisu", 100, 50, expirationDate3, 140, 1000));
        cakeRepo.create(new Cake(2, "Eclair", 130, 50, expirationDate4, 120, 1000));
        cakeRepo.create(new Cake(3, "Dubai Chocolate", 200, 100, expirationDate2, 99, 1200));
        cakeRepo.create(new Cake(4, "Carrot Cake", 110, 55, expirationDate3, 68, 900));
        cakeRepo.create(new Cake(5, "Cheesecake", 200, 100, expirationDate1, 100, 500));
        cakeRepo.create(new Cake(6, "Lemon Cake", 130, 65, expirationDate2, 75, 600));
        cakeRepo.create(new Cake(7, "Apple Pie", 90, 45, expirationDate5, 60, 950));
        cakeRepo.create(new Cake(8, "Vanilla Cake", 100, 50, expirationDate2, 108, 1000));
        cakeRepo.create(new Cake(9, "Pineapple Upside Down Cake", 140, 70, expirationDate5, 95, 650));
        cakeRepo.create(new Cake(10, "Strawberry Shortcake", 160, 80, expirationDate4, 90, 750));


    }

    private static void initializeDrinkRepository(IRepository<Drink> drinkRepo) {
        ExpirationDate expirationDate6 = new ExpirationDate(2025, Month.April, Day.Fifteenth);
        ExpirationDate expirationDate7 = new ExpirationDate(2024, Month.May, Day.TwentyFourth);
        ExpirationDate expirationDate8 = new ExpirationDate(2026, Month.July, Day.TwentyFirst);
        ExpirationDate expirationDate9 = new ExpirationDate(2023, Month.March, Day.Thirteenth);
        ExpirationDate expirationDate10 = new ExpirationDate(2027, Month.January, Day.First);
        drinkRepo.create(new Drink(11, "Water", 10, 50, expirationDate6, 30, 0));
        drinkRepo.create(new Drink(12, "Cappuccino", 15, 200, expirationDate7, 45, 0));
        drinkRepo.create(new Drink(13, "Latte", 14, 250, expirationDate8, 68, 0));
        drinkRepo.create(new Drink(14, "Beer", 19, 200, expirationDate9, 10, 8));
        drinkRepo.create(new Drink(15, "Tequila", 18, 300, expirationDate10, 15, 6));
        drinkRepo.create(new Drink(16, "Mocha", 16, 250, expirationDate6, 46, 5));
        drinkRepo.create(new Drink(17, "Green Tea", 21, 150, expirationDate7, 17, 0));
        drinkRepo.create(new Drink(18, "Black Tea", 20, 150, expirationDate6, 12, 0));
        drinkRepo.create(new Drink(19, "Lemonade", 25, 350, expirationDate9, 17, 0));
        drinkRepo.create(new Drink(20, "Hot Chocolate", 20, 200, expirationDate10, 22, 0));
    }


    private static void initializeUserRepository(IRepository<User> userRepo) {
        userRepo.create(new Client("Andrei", "Bujoreni", 123));
        userRepo.create(new Client("Maria", "Bujoreni", 312));
        userRepo.create(new Client("Ioana", "Bujoreni", 231));
        userRepo.create(new Admin("admin", "admin@gmail.com", 333, "Bali", "Bujoreni"));


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