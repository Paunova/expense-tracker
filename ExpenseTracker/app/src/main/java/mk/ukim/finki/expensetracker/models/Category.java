package mk.ukim.finki.expensetracker.models;

/**
 * Model representing a category.
 */
public class Category extends BaseEntity {

    public static final Long ALL_CATEGORIES_ID = Long.valueOf(-1);

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
