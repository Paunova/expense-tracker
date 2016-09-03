package mk.ukim.finki.expensetracker.utilities;

/**
 * Utility class for holding constants.
 */
public class Constants {
    public class Table {

        public class Categories {
            public static final String TABLE_NAME = "categories";
            public static final String KEY_ID = "id";
            public static final String NAME = "name";
        }

        public class Expenses {
            public static final String TABLE_NAME = "expenses";
            public static final String KEY_ID = "id";
            public static final String CATEGORY_ID = "category_id";
            public static final String AMOUNT = "amount";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
        }
    }
}
