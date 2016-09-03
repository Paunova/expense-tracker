package mk.ukim.finki.expensetracker.models;

/**
 * Model representing a category.
 */
public class Category extends BaseEntity {

    public String name;

    @Override
    public String toString() {
        return name;
    }
}
