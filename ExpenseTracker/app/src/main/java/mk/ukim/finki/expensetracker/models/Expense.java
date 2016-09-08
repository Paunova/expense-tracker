package mk.ukim.finki.expensetracker.models;

import org.joda.time.DateTime;

/**
 * Model representing an expense.
 */
public class Expense extends BaseEntity{

    public long categoryId;
    public Category category;
    public DateTime dateTime;
    public int amount;
    public String description;

    @Override
    public String toString() {
        return id + " " + categoryId + " " + amount + " " + description + " " + dateTime;
    }
}
