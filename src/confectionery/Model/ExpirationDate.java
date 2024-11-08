package confectionery.Model;

public class ExpirationDate {
    private int year;
    private Month month;
    private Day day;
    public ExpirationDate(int year, Month month, Day day) {
        this.year = year;
        this.month = month;
        this.day = day;

    }

    public int getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public Day getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "ExpirationDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
