package confectionery.Model;

/**
 * Represents an expiration date, which includes a year, month, and day.
 */
public class ExpirationDate {
    private int year;
    private Month month;
    private Day day;

    /**
     *
     * @param year the year of expiration date
     * @param month the month of expiration date
     * @param day the day of expiration date
     */
    public ExpirationDate(int year, Month month, Day day) {
        this.year = year;
        this.month = month;
        this.day = day;

    }

    /**
     * @return the year of the expiration date
     */
    public int getYear() {
        return year;
    }
    /**
     * @return the month of the expiration date
     */

    public Month getMonth() {
        return month;
    }
    /**
     * @return the day of the expiration date
     */
    public Day getDay() {
        return day;
    }

    /**
     *
     * @return a toString Method with the ExpirationDate - year,month and day
     */
    @Override
    public String toString() {
        return "ExpirationDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
