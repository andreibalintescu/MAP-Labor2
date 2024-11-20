package confectionery;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import confectionery.Model.*;
import confectionery.Repository.CSVFileRepository;
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
                    1. View Menu order by Points
                    2. View Menu order by Price
                    3. Place Order
                    4. Cancel Order
                    5. Generate Invoice
                    6. View Profile
                    7. View Drinks With Alcohol
                    8. View Products available until December 2024
                    0. Logout
                    Please select an option:
                    """);

            String option = scanner.nextLine();
            switch (option) {
                case "1" ->confectioneryController.viewMenuPoints();
                case "2" -> confectioneryController.viewMenuPrice();
                case "3" -> confectioneryController.placeOrder(scanner);
                case "4" -> confectioneryController.cancelOrder(scanner);
                case "5" -> confectioneryController.generateInvoice();
                case "6" -> confectioneryController.getProfile();
                case "7" ->confectioneryController.filterByAlcohol();
                case "8" ->confectioneryController.filterByExpirationDate();
                case "0" -> clientRunning = false;
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
                    4. View Users with their Orders
                    5. View Profile
                    6. View Client with the most Points
                    7. Change Password
                    8. Update Products
                    9. Delete User
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
                case "8" -> confectioneryController.updateProduct(scanner);
                case "9" -> confectioneryController.deleteUser(scanner);
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
        //in memory
//        IRepository<Cake> cakeRepo = createInMemoryCakesRepository();
//        IRepository<Drink> drinkRepo = createInMemoryDrinksRepository();
//        IRepository<Order> orderRepo = new InMemoryRepository<>();
//        IRepository<User> userRepo = createInMemoryUsersRepository();


        //filerepository

        String cakeFilePath = "cakes.csv";
        String drinkFilePath = "drinks.csv";
        String userFilePath = "users.csv";
        String orderFilePath = "orders.csv";

        IRepository<Cake> cakeRepo = new CSVFileRepository<>(cakeFilePath, cakeDeserializer, cakeSerializer);
        IRepository<Drink> drinkRepo = new CSVFileRepository<>(drinkFilePath, drinkDeserializer, drinkSerializer);
        IRepository<User> userRepo = new CSVFileRepository<>(userFilePath, userDeserializer, userSerializer);
        IRepository<Order> orderRepo = new CSVFileRepository<>(orderFilePath, orderDeserializer, orderSerializer);

        intializeCakeRepository(cakeRepo);
        initializeDrinkRepository(drinkRepo);
        initializeUserRepository(userRepo);


        ConfectioneryService confectioneryService = new ConfectioneryService(cakeRepo, drinkRepo, orderRepo, userRepo);
        ConfectioneryController confectioneryController = new ConfectioneryController(confectioneryService);

        ConsoleApp consoleApp = new ConsoleApp(confectioneryController);

        consoleApp.start();
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






    static Function<String[], Cake> cakeDeserializer = fields -> {
        try {
            return new Cake(
                    Integer.parseInt(fields[0]), // ID
                    fields[1],                   // Name
                    Double.parseDouble(fields[2]), // Price
                    Double.parseDouble(fields[3]), // Weight
                    ExpirationDate.parse(fields[4]), // Expiration Date (Assume ExpirationDate.parse exists)
                    Integer.parseInt(fields[5]),    // Points
                    Integer.parseInt(fields[6])     // Calories
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    static Function<Cake, String[]> cakeSerializer = cake -> new String[]{
            String.valueOf(cake.getID()),
            cake.getName(),
            String.valueOf(cake.getPrice()),
            String.valueOf(cake.getWeight()),
            cake.getExpirationDate().toString(),
            String.valueOf(cake.getPoints()),
            String.valueOf(cake.getCalories())
    };

    static Function<Drink, String[]> drinkSerializer = drink -> new String[]{
            String.valueOf(drink.getID()),
            drink.getName(),
            String.valueOf(drink.getPrice()),
            String.valueOf(drink.getWeight()),
            drink.getExpirationDate().toString(),
            String.valueOf(drink.getPoints()),
            String.valueOf(drink.getAlcoholPercentage())

    };

    static Function<String[], Drink> drinkDeserializer = fields -> {
        try{
            return new Drink(
                    Integer.parseInt(fields[0]),
                    fields[1],
                    Double.parseDouble(fields[2]),
                    Double.parseDouble(fields[3]),
                    ExpirationDate.parse(fields[4]),
                    Integer.parseInt(fields[5]),
                    Double.parseDouble(fields[6])
            );
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    static Function<String[], User> userDeserializer = fields -> {
        String type = fields[0]; // "Admin" or "Client"
        try {
            if ("Admin".equals(type)) {
                return new Admin(
                        fields[4],                 // Password
                        fields[5],                 // Email
                        Integer.parseInt(fields[1]), // ID
                        fields[2],                 // Name
                        fields[3]                  // Address
                );
            } else if ("Client".equals(type)) {
                Client client = new Client(
                        fields[2],                 // Name
                        fields[3],                 // Address
                        Integer.parseInt(fields[1]) // ID
                );
                // Store order IDs (comma-separated string) as a reference for later processing
                if (fields.length > 4 && !fields[4].isEmpty()) {
                    String[] orderIDs = fields[4].split(",");
                    for (String orderID : orderIDs) {
                        // Simply associate the IDs; do not deserialize the full orders here
                        client.placeOrder(new Order(Integer.parseInt(orderID))); // Placeholder for ID-only orders
                    }
                }
                return client;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };


    static Function<User, String[]> userSerializer = user -> {
        if (user instanceof Admin admin) {
            return new String[]{
                    "Admin",
                    String.valueOf(admin.getID()),
                    admin.getName(),
                    admin.getAddress(),
                    admin.getPassword(),
                    admin.getEmail()
            };
        } else if (user instanceof Client client) {
            // Serialize order IDs as a comma-separated string
            String orderIds = client.getOrders().stream()
                    .map(order -> String.valueOf(order.getID()))
                    .reduce((id1, id2) -> id1 + "," + id2)
                    .orElse("");
            return new String[]{
                    "Client",
                    String.valueOf(client.getID()),
                    client.getName(),
                    client.getAddress(),
                    orderIds
            };
        }
        return new String[0];
    };

    static Function<Order, String[]> orderSerializer = order -> {
        String productIds = order.getProducts().stream()
                .map(product -> String.valueOf(product.getID()))
                .reduce((id1, id2) -> id1 + "," + id2)
                .orElse(""); // Empty if no products
        return new String[]{
                String.valueOf(order.getID()),
                order.getDate().toString(),
                productIds
        };
    };

    static Function<String[], Order> orderDeserializer = fields -> {
        try {
            Integer orderID = Integer.parseInt(fields[0]);
            LocalDate date = LocalDate.parse(fields[1]);
            List<Product> products = new ArrayList<>();

            if (fields.length > 2 && !fields[2].isEmpty()) {
                String[] productIDs = fields[2].split(",");
                for (String productId : productIDs) {
                    // Placeholder for product IDs; actual resolution happens later
                    products.add(new Product(Integer.parseInt(productId)));
                }
            }
            return new Order(products, orderID, date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };
}
