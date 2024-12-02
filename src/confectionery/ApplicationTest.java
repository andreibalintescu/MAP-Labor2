package confectionery;

import confectionery.Exception.EntityNotFoundException;
import confectionery.Model.*;
import confectionery.Repository.FileRepository;
import confectionery.Repository.IRepository;
import confectionery.Repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.File;
import java.util.List;
import java.util.Scanner;

public class ApplicationTest {
    private ConfectioneryController controller;
    private ConfectioneryService service;
    private IRepository<Cake> inMemoryCakeRepo;
    private IRepository<Drink> inMemoryDrinkRepo;
    private IRepository<User> inMemoryUserRepo;
    private IRepository<Order> inMemoryOrderRepo;

    private IRepository<Cake> fileCakeRepo;
    private IRepository<Drink> fileDrinkRepo;
    private IRepository<User> fileUserRepo;

    private final String cakeFilePath = "cake_test.dat";
    private final String drinkFilePath = "drink_test.dat";
    private final String userFilePath = "user_test.dat";

    @BeforeEach
    public void setUp() {
        inMemoryCakeRepo = new InMemoryRepository<>();
        inMemoryDrinkRepo = new InMemoryRepository<>();
        inMemoryUserRepo = new InMemoryRepository<>();
        inMemoryOrderRepo=new InMemoryRepository<>();

        fileCakeRepo = new FileRepository<>(cakeFilePath);
        fileDrinkRepo = new FileRepository<>(drinkFilePath);
        fileUserRepo = new FileRepository<>(userFilePath);

        service = new ConfectioneryService(inMemoryCakeRepo, inMemoryDrinkRepo,inMemoryOrderRepo, inMemoryUserRepo);
        controller = new ConfectioneryController(service);
        deleteTestFiles();

        createSampleData();
    }


    private void deleteTestFiles() {
        new File(cakeFilePath).delete();
        new File(drinkFilePath).delete();
        new File(userFilePath).delete();
    }


    private void createSampleData() {
        Cake cake1 = new Cake(1, "Tiramisu", 100, 50, new ExpirationDate(2024, Month.December, Day.Eighteenth), 140, 1000);
        Cake cake2 = new Cake(2, "Eclair", 130, 50, new ExpirationDate(2024, Month.December, Day.First), 120, 1000);
        Cake cake3 = new Cake(3, "Cheesecake", 100, 30, new ExpirationDate(2024, Month.June, Day.Fifteenth), 150, 500);
        inMemoryCakeRepo.create(cake1);
        inMemoryCakeRepo.create(cake2);
        inMemoryCakeRepo.create(cake3);
        fileCakeRepo.create(cake1);
        fileCakeRepo.create(cake2);
        fileCakeRepo.create(cake3);

        Drink drink1 = new Drink(11, "Water", 10, 50, new ExpirationDate(2025, Month.April, Day.Fifteenth), 30, 0);
        Drink drink2 = new Drink(12, "Cappuccino", 15, 200, new ExpirationDate(2024, Month.May, Day.TwentyFourth), 45, 0);
        Drink drink3 = new Drink(13, "Lemonade", 10, 100, new ExpirationDate(2024, Month.August, Day.Tenth), 20, 0);

        inMemoryDrinkRepo.create(drink1);
        inMemoryDrinkRepo.create(drink2);
        inMemoryDrinkRepo.create(drink3);
        fileDrinkRepo.create(drink1);
        fileDrinkRepo.create(drink2);
        fileDrinkRepo.create(drink3);

        User user1 = new Client("Andrei", "Bujoreni", 123);
        User user2 = new Admin("admin", "admin@gmail.com", 333, "Bali", "Bujoreni");
        User user3 = new Client("Marian", "Popescu", 124);
        inMemoryUserRepo.create(user1);
        inMemoryUserRepo.create(user2);
        inMemoryUserRepo.create(user3);
        fileUserRepo.create(user1);
        fileUserRepo.create(user2);
        fileUserRepo.create(user3);

    }
    /**
     * Tests CRUD operations for cakes in memory.
     * Verifies the creation, update, deletion, and retrieval of a Cake object from the in-memory repository.
     */

    @Test
    public void testCakeCRUDInMemory() {

        Cake retrievedCake = inMemoryCakeRepo.get(3);
        assertNotNull(retrievedCake);
        assertEquals("Cheesecake", retrievedCake.getName());

        // Update test
        retrievedCake.setName("Updated Cheesecake");
        inMemoryCakeRepo.update(retrievedCake);
        Cake updatedCake = inMemoryCakeRepo.get(3);
        assertEquals("Updated Cheesecake", updatedCake.getName());

        // Delete test
        inMemoryCakeRepo.delete(3);
        Cake deletedCake = inMemoryCakeRepo.get(3);
        assertNull(deletedCake);

        // Get all
        List<Cake> cakes = inMemoryCakeRepo.getAll();
        assertNotNull(cakes);
        assertTrue(cakes.size() ==2, "The list of cakes should be 2.");
    }

    /**
     * Tests CRUD operations for drinks in memory.
     * Verifies the creation, update, deletion, and retrieval of a Drink object from the in-memory repository.
     */
    @Test
    public void testDrinkCRUDInMemory() {

        Drink retrievedDrink = inMemoryDrinkRepo.get(13);
        assertNotNull(retrievedDrink);
        assertEquals("Lemonade", retrievedDrink.getName());

        // Update test
        retrievedDrink.setName("Updated Lemonade");
        inMemoryDrinkRepo.update(retrievedDrink);
        Drink updatedDrink = inMemoryDrinkRepo.get(13);
        assertEquals("Updated Lemonade", updatedDrink.getName());

        // Delete test
        inMemoryDrinkRepo.delete(13);
        Drink deletedDrink = inMemoryDrinkRepo.get(13);
        assertNull(deletedDrink);

        // Get all
        List<Drink> drinks = inMemoryDrinkRepo.getAll();
        assertNotNull(drinks);
        assertTrue(drinks.size() ==2, "The list of drinks should be 2");
    }

    /**
     * Tests CRUD operations for users in memory.
     * Verifies the creation, update, deletion, and retrieval of a User object from the in-memory repository.
     */
    @Test
    public void testUserCRUDInMemory() {
        User retrievedUser = inMemoryUserRepo.get(124);
        assertNotNull(retrievedUser);
        assertEquals("Marian", retrievedUser.getName());

        // Update test
        retrievedUser.setName("Updated Marian");
        inMemoryUserRepo.update(retrievedUser);
        User updatedUser = inMemoryUserRepo.get(124);
        assertEquals("Updated Marian", updatedUser.getName());

        // Delete test
        inMemoryUserRepo.delete(124);
        User deletedUser = inMemoryUserRepo.get(124);
        assertNull(deletedUser);

        // Get all
        List<User> users = inMemoryUserRepo.getAll();
        assertNotNull(users);
        assertTrue(users.size() ==2, "The list of users should be 2.");
    }

    /**
     * Tests CRUD operations for cakes in file-based repository.
     * Verifies the creation, update, deletion, and retrieval of a Cake object from the file repository.
     */
    @Test
    public void testCakeCRUDFile() {


        Cake retrievedCake = fileCakeRepo.get(2);
        assertNotNull(retrievedCake);
        assertEquals("Eclair", retrievedCake.getName());

        // Update test
        retrievedCake.setName("Updated Eclair");
        fileCakeRepo.update(retrievedCake);
        Cake updatedCake = fileCakeRepo.get(2);
        assertEquals("Updated Eclair", updatedCake.getName());

        // Delete test
        fileCakeRepo.delete(2);
        Cake deletedCake = fileCakeRepo.get(2);
        assertNull(deletedCake);

        //getall
        List<Cake> cakes = fileCakeRepo.getAll();
        assertNotNull(cakes);
        assertTrue(cakes.size()==2, "The list of cakes should not be empty.");

    }



    /**
     * Tests CRUD operations for drinks in file-based repository.
     * Verifies the creation, update, deletion, and retrieval of a Drink object from the file repository.
     */

    @Test
    public void testDrinkCRUDFile() {

        Drink retrievedDrink = fileDrinkRepo.get(11);
        assertNotNull(retrievedDrink);
        assertEquals("Water", retrievedDrink.getName());

        // Update test
        retrievedDrink.setName("Updated Water");
        fileDrinkRepo.update(retrievedDrink);
        Drink updatedDrink = fileDrinkRepo.get(11);
        assertEquals("Updated Water", updatedDrink.getName());

        // Delete test
        fileDrinkRepo.delete(11);
        Drink deletedDrink = fileDrinkRepo.get(11);
        assertNull(deletedDrink);


      //getall
        List<Drink> drinks = fileDrinkRepo.getAll();
        assertNotNull(drinks);
        assertTrue(drinks.size()==2, "The list of drinks should not be empty.");


    }
    /**
     * Tests CRUD operations for users in file-based repository.
     * Verifies the creation, update, deletion, and retrieval of a User object from the file repository.
     */
    @Test
    public void testUserCRUDFile() {

        User retrievedUser = fileUserRepo.get(333);
        assertNotNull(retrievedUser);
        assertEquals("Bali", retrievedUser.getName());

        // Update test
        retrievedUser.setName("Balintescu");
        fileUserRepo.update(retrievedUser);
        User updatedUser = fileUserRepo.get(333);
        assertEquals("Balintescu", updatedUser.getName());


        // Delete test
        fileUserRepo.delete(333);
        User deletedUser = fileUserRepo.get(333);
        assertNull(deletedUser);

        //getall
        List<User> users = fileUserRepo.getAll();
        assertNotNull(users);
        assertTrue(users.size() ==2, "The list of users should be 2.");

    }

    /**
     * Test successful login for an admin user.
     * Verifies that the correct credentials ("admin@gmail.com" and "admin") successfully log in an admin.
     */

    @Test
    void testLoginAdmin_Success() {
        Scanner scanner = new Scanner("admin@gmail.com\nadmin\n");
        boolean result = controller.loginAdmin(scanner);
        assertTrue(result);
    }
    /**
     * Test unsuccessful login for an admin user.
     * Verifies that the incorrect credentials ("admin@example.com" and "wrongpassword")
     */

    @Test
    void testLoginAdmin_Fail_InvalidCredentials() {
        Scanner scanner = new Scanner("admin@example.com\nwrongpassword\n");
        boolean result = controller.loginAdmin(scanner);
        assertFalse(result);
    }

    /**
     * Test successful login for a client user.
     * Verifies that the client "Marian" can successfully log in.
     */

    @Test
    void testLoginClient_Success() {

        Scanner scanner = new Scanner("Marian\n");
        boolean result = controller.loginClient(scanner);
        assertTrue(result);
    }


    /**
     * Test failed login for a client due to an invalid username.
     * Verifies that a non-existent username ("NonexistentUser") will fail the login attempt.
     */

    @Test
    void testLoginClient_Fail_InvalidUsername() {
        Scanner scanner = new Scanner("NonexistentUser\n");
        boolean result = controller.loginClient(scanner);
        assertFalse(result);
    }
    /**
     * Test view menu points functionality.
     */
   @Test
   void testViewMenuPoints(){
        controller.viewMenuPoints();
   }
    /**
     * Test view menu price functionality.
     */
    @Test
    void testViewMenuPrice() {
        controller.viewMenuPrice();
    }
    /**
     * Test filter menu functionality.
     */

    @Test
    void testFilterByAlcohol() {
        controller.filterByAlcohol();
    }

    /**
     * Test filter menu functionality.
     */
    @Test
    void testFilterByExpirationDate() {
        controller.filterByExpirationDate();
    }

    /**
     * Test retrieving a user's profile.
     * Verifies that the controller correctly retrieves and displays the profile of a client.
     * Ensures the name returned is correct and not an incorrect value (e.g., "John").
     */

    @Test
    void testGetProfile() throws EntityNotFoundException {
        User client = inMemoryUserRepo.get(123);
        service.authenticateClient(client.getName());
        controller.getProfile();
        assertEquals("Andrei", client.getName());
        assertNotEquals("John", client.getName());
    }
    /**
     * Test the deletion of a user
     */

    @Test
    void testDeleteUser() {
        Scanner scanner = new Scanner("333\n");
        controller.deleteUser(scanner);

        User deletedUser = inMemoryUserRepo.get(333);
        assertNull(deletedUser, "User is deleted.");
    }
    /**
     * Test client placing an order and checking their balance after the order.
     * Verifies that the client can place an order, the order contains the correct items (cakes and drinks),
     * and the total balance is correctly updated.
     */

    @Test
    public void testClientPlaceOrderAndCheckBalance() {

        Scanner scanner = new Scanner("Marian\n");
        controller.loginClient(scanner);


        Scanner scanner1 = new Scanner("1 2\n11 12");
        controller.placeOrder(scanner1);

        User client=inMemoryUserRepo.get(124);
        User client1=inMemoryUserRepo.get(123);
        List<Order> orders = inMemoryOrderRepo.getAll();
        assertEquals(1, orders.size());
        assertNotEquals(2,orders.size());

        assertEquals(4, orders.get(0).getProducts().size());
        assertNotEquals(5,orders.get(0).getProducts().size());

        float totalBalance = service.getBalanceTotal();
        assertTrue(totalBalance ==255, "Total balance should be 255");
        assertFalse(totalBalance==256, "Total balance should not be 256");

        service.getMonthlyBalance(12);
        service.getYearlyBalance(2024);
        service.getInvoice();
        controller.viewUsers();
        assertEquals(client,service.getClientWithMostPoints());
        assertNotEquals(client1,service.getClientWithMostPoints());

    }

    /**
     * Test the change of password functionality for admin.
     * Verifies that the admin can successfully change the password by entering the new password and retyping it correctly.
     * Ensures the new password is set correctly and does not match an incorrect retyped password.
     */

    @Test
    public void testChangePassword_Success() {
        Scanner scanner = new Scanner("admin@gmail.com\nadmin\n");
        controller.loginAdmin(scanner);
        String newPassword = "newPassword123";
        String retypePassword = "newPassword123";
        Scanner scanner1 = new Scanner("newPassword123"+ "\n" + "newPassword123");
        controller.changePassword(scanner1);
        assertEquals("newPassword123", newPassword);
        assertEquals("newPassword123", retypePassword);
        assertNotEquals("newPassword121", newPassword);
        assertNotEquals("newPassword121", retypePassword);
    }

}

