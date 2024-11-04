package confectionery.Model;

import java.time.Month;
import java.time.Year;

public class Admin extends User {
    private final String password;
    private final String email;
    private final Integer id;
    private Balance balance;

    public Admin(String password, String email, Integer id, String name, String address, Balance balance) {
        super(name, address);
        this.password = password;
        this.email = email;
        this.id = id;
        this.balance = balance;

    }

    @Override
    public Integer getID() {
        return this.id;
    }

    @Override
    public boolean login() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.print("Enter email: ");
        String inputEmail = scanner.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        return inputEmail.equals(this.email) && inputPassword.equals(this.password);
    }

    public float getMonthlyBalance(Month month) {
        return balance.monthlyBalance(month);
    }

    public float getYearlyBalance(Year year) {
        return balance.yearlyBalance(year);
    }

    public float getBalance() {
        return balance.calculateTotalBalance();
    }
}
