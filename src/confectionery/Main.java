package confectionery;

import confectionery.Model.*;
import confectionery.Repository.FileRepository;
import confectionery.Repository.IRepository;

public class Main {
    // STARTING POINT FOR APP USING PERSISTENT MEMORY STORAGE METHOD
    public static void main(String[] args) {
        // Define file paths for each repository
        String cakeFilePath = "D:\\JavaProjects\\Laboratories\\Labor2\\src\\confectionery\\cakes.dat";
        String drinkFilePath = "D:\\JavaProjects\\Laboratories\\Labor2\\src\\confectionery\\drinks.dat";
        String orderFilePath = "D:\\JavaProjects\\Laboratories\\Labor2\\src\\confectionery\\orders.dat";
        String userFilePath = "D:\\JavaProjects\\Laboratories\\Labor2\\src\\confectionery\\users.dat";


        // Create FileRepository instances with the specified file paths
        IRepository<Cake> cakeRepo = new FileRepository<>(cakeFilePath);
        IRepository<Drink> drinkRepo = new FileRepository<>(drinkFilePath);
        IRepository<Order> orderRepo = new FileRepository<>(orderFilePath);
        IRepository<User> userRepo = new FileRepository<>(userFilePath);
        // Add data to the file repos for cakes and drinks
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
        drinkRepo.create(new Drink(18, "Black Tea", 20, 150, expirationDate5, 12, 0));
        drinkRepo.create(new Drink(19, "Lemonade", 25, 350, expirationDate9, 17, 0));
        drinkRepo.create(new Drink(20, "Hot Chocolate", 20, 200, expirationDate10, 22, 0));

        userRepo.create(new Client("Andrei", "Bujoreni", 123));
        userRepo.create(new Client("Maria", "Bujoreni", 312));
        userRepo.create(new Client("Ioana", "Bujoreni", 231));
        userRepo.create(new Admin("admin", "admin@gmail.com", 333, "Bali", "Bujoreni"));

        ConfectioneryService confectioneryService = new ConfectioneryService(cakeRepo, drinkRepo, orderRepo, userRepo);
        ConfectioneryController confectioneryController = new ConfectioneryController(confectioneryService);

        ConsoleApp consoleApp = new ConsoleApp(confectioneryController);
        consoleApp.start();
    }
}
