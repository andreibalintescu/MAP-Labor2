package confectionery;

import confectionery.Model.*;
import confectionery.Repository.*;
import confectionery.Repository.DataBase.CakeDBRepository;
import confectionery.Repository.DataBase.DBRepository;
import confectionery.Repository.DataBase.DrinkDBRepository;
import confectionery.Repository.DataBase.UserDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    @BeforeEach
    public void setUp() throws Exception {
        // Clean up files for FileRepository
        new File("cake_test.dat").delete();
        new File("drink_test.dat").delete();
        new File("user_test.dat").delete();

        // Clean up the database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db", "", "")) {
            try (PreparedStatement stmt1 = connection.prepareStatement("DROP TABLE IF EXISTS Cakes");
                 PreparedStatement stmt2 = connection.prepareStatement("DROP TABLE IF EXISTS Drinks");
                 PreparedStatement stmt3 = connection.prepareStatement("DROP TABLE IF EXISTS Clients");
                 PreparedStatement stmt4 = connection.prepareStatement("DROP TABLE IF EXISTS Admins")) {
                stmt1.execute();
                stmt2.execute();
                stmt3.execute();
                stmt4.execute();
            }
        }


        // Recreate tables for DB repositories
        CakeDBRepository cakeDBRepo = new CakeDBRepository("jdbc:sqlite:test.db", "", "");
        DrinkDBRepository drinkRepo = new DrinkDBRepository("jdbc:sqlite:test.db", "", "");
        UserDBRepository userRepo = new UserDBRepository("jdbc:sqlite:test.db", "", "");

        cakeDBRepo.close();
        drinkRepo.close();
        userRepo.close();
    }

    private static Stream<IRepository<Cake>> provideCakeRepositories() {
        return Stream.of(
                new InMemoryRepository<>(),
                new FileRepository<>("cake_test.dat"),
                new CakeDBRepository("jdbc:sqlite:test.db", "", "") // Adjust DB credentials
        );
    }

    private static Stream<IRepository<Drink>> provideDrinkRepositories() {
        return Stream.of(
                new InMemoryRepository<>(),
                new FileRepository<>("drink_test.dat"),
                new DrinkDBRepository("jdbc:sqlite:test.db", "", "")
        );
    }

    private static Stream<IRepository<User>> provideUserRepositories() {
        return Stream.of(
                new InMemoryRepository<>(),
                new FileRepository<>("user_test.dat"),
                new UserDBRepository("jdbc:sqlite:test.db", "", "") // Adjust DB credentials
        );
    }
    // Test CRUD operations for Cake repositories
    @ParameterizedTest
    @MethodSource("provideCakeRepositories")
    void testCakeCRUD(IRepository<Cake> repository) {
        // Create and store a new Cake
        Cake cake = new Cake(1, "Tiramisu", 100, 50, new ExpirationDate(2024, Month.December, Day.Eighteenth), 140, 1000);
        repository.create(cake);

        // Retrieve and verify the Cake
        Cake retrievedCake = repository.get(1);
        assertNotNull(retrievedCake);
        assertEquals("Tiramisu", retrievedCake.getName());

        // Update and verify
        retrievedCake.setName("Updated Tiramisu");
        repository.update(retrievedCake);
        Cake updatedCake = repository.get(1);
        assertEquals("Updated Tiramisu", updatedCake.getName());

        // Delete and verify
        repository.delete(1);
        assertNull(repository.get(1));

        // Test getAll()
        repository.create(new Cake(2, "Cheesecake", 120, 60, new ExpirationDate(2024, Month.June, Day.Fifteenth), 130, 500));
        List<Cake> cakes = repository.getAll();
        assertEquals(1, cakes.size());
        assertEquals("Cheesecake", cakes.get(0).getName());
    }

    @ParameterizedTest
    @MethodSource("provideDrinkRepositories")
    void testDrinkCRUD(IRepository<Drink> repository) {
        Drink drink = new Drink(11, "Water", 10, 50, new ExpirationDate(2025, Month.April, Day.Fifteenth), 30, 0);
        repository.create(drink);

        Drink retrievedDrink = repository.get(11);
        assertNotNull(retrievedDrink);
        assertEquals("Water", retrievedDrink.getName());

        retrievedDrink.setName("Updated Water");
        repository.update(retrievedDrink);
        Drink updatedDrink = repository.get(11);
        assertEquals("Updated Water", updatedDrink.getName());

        repository.delete(11);
        assertNull(repository.get(11));

        repository.create(new Drink(12, "Cappuccino", 15, 200, new ExpirationDate(2024, Month.May, Day.TwentyFourth), 45, 0));
        List<Drink> drinks = repository.getAll();
        assertEquals(1, drinks.size());
        assertEquals("Cappuccino", drinks.get(0).getName());
    }

    @ParameterizedTest
    @MethodSource("provideUserRepositories")
    void testUserCRUD(IRepository<User> repository) {
        // Create and store a Client
        Client client = new Client("Andrei", "Bujoreni", 123);
        repository.create(client);

        // Create and store an Admin
        Admin admin = new Admin("admin", "admin@gmail.com", 333, "Bali", "Bujoreni");
        repository.create(admin);

        // Retrieve and verify the Client
        User retrievedClient = repository.get(123);
        assertNotNull(retrievedClient);
        assertInstanceOf(Client.class, retrievedClient);
        assertEquals("Andrei", retrievedClient.getName());

        // Retrieve and verify the Admin
        User retrievedAdmin = repository.get(333);
        assertNotNull(retrievedAdmin);
        assertInstanceOf(Admin.class, retrievedAdmin);
        assertEquals("Bali", retrievedAdmin.getName());

        // Update and verify the Client
        retrievedClient.setName("Updated Andrei");
        repository.update(retrievedClient);
        User updatedClient = repository.get(123);
        assertEquals("Updated Andrei", updatedClient.getName());

        // Update and verify the Admin
        retrievedAdmin.setName("Updated Bali");
        repository.update(retrievedAdmin);
        User updatedAdmin = repository.get(333);
        assertEquals("Updated Bali", updatedAdmin.getName());

        // Delete and verify the Client
        repository.delete(123);
        assertNull(repository.get(123));

        // Delete and verify the Admin
        repository.delete(333);
        assertNull(repository.get(333));

        // Test getAll()
        repository.create(new Client("Maria", "Popescu", 124));
        repository.create(new Admin("admin2", "admin2@gmail.com", 334, "Balintescu", "Somewhere"));
        List<User> users = repository.getAll();
        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("Maria")));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("Balintescu")));
    }

}
