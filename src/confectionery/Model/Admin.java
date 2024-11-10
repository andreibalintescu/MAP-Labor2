package confectionery.Model;

import java.time.Month;
import java.time.Year;
import java.util.Scanner;

/**
 * The Admin class represents an administrator user in the system.
 * An admin can log in, view balance details, and perform other administrative tasks.
 */
public class Admin extends User {

    private final String password;
    private final String email;
    private final Integer id;
    private Balance balance; // TBD

    /**
     *
     * @param password for the admin
     * @param email for the admin
     * @param id for the admin
     * @param name for the admin
     * @param address for the admin
     */
    public Admin(String password, String email, Integer id, String name, String address) {
        super(name, address);
        this.password = password;
        this.email = email;
        this.id = id;

    }

    /**
     * Gets the unique identifier of the admin.
     * @return the admin id
     */

    @Override
    public Integer getID() {
        return this.id;
    }

    /**
     * Authenticates the admin login using the email and password.
     * @param scanner  A Scanner object to read the input from the user.
     * @return True if the entered email and password match the admin's credentials, false otherwise.
     */
    @Override
    public boolean login(Scanner scanner) {
        System.out.print("Enter email: ");
        String inputEmail = scanner.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        return inputEmail.equals(this.email) && inputPassword.equals(this.password);
    }


    /**
     * @return the admin email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the admin password
     */
    public String getPassword() {
        return password;
    }

    /**
     * gets the balance for a month
     * @param month
     * @return balance for a month
     */

    public float getMonthlyBalance(Month month) {
        System.out.println("Monthly balance for month " + month);
        return balance.monthlyBalance(month);
    }

    /**
     * gets the balance for a year
     * @param year
     * @return balance for a year
     */

    public float getYearlyBalance(int year) {
        System.out.println("Yearly balance for year " + year);
        return balance.yearlyBalance(year);
    }

    /**
     * gets the total balance
     * @return total balance
     */
    public float getBalance() {
        System.out.println("Total balance:");
        return balance.calculateTotalBalance();
    }

    /**
     *
     * @return a string containing the admin's ID and email.
     *
     */
    public String toString() {
        return "Admin:" + "id" + " " + id + "," + " " + "email" + " " + email;

    }
}
