package mk.ukim.finki.expensetracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mk.ukim.finki.expensetracker.utilities.Constants;

/**
 * Default database helper implementation.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Name
    private static final String DATABASE_NAME = "expense_tracker.db";

    // Table Create Statements
    private static final String CREATE_TABLE_CATEGORIES = String.format("CREATE TABLE %s " +
            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s INT(20));" ,
            Constants.Table.Categories.TABLE_NAME,
            Constants.Table.Categories.KEY_ID,
            Constants.Table.Categories.NAME);

    private static final String CREATE_TABLE_EXPENSES = String.format("CREATE TABLE %s " +
            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s INTEGER," +
            " %s DECIMAL(20)," +
            " %s VARCHAR(255)," +
            " %s VARCHAR(255)," +
            " FOREIGN KEY (%s) REFERENCES %s(%s));",
            Constants.Table.Expenses.TABLE_NAME,
            Constants.Table.Expenses.KEY_ID,
            Constants.Table.Expenses.CATEGORY_ID,
            Constants.Table.Expenses.AMOUNT,
            Constants.Table.Expenses.DESCRIPTION,
            Constants.Table.Expenses.DATE,
            Constants.Table.Expenses.CATEGORY_ID,
            Constants.Table.Categories.TABLE_NAME,
            Constants.Table.Categories.KEY_ID);

    private static final String ADD_DEFAULT_CATEGORIES = String.format("INSERT INTO %s " +
            "(id, name) " +
            "VALUES " +
            "(1, 'Grocery'), " +
            "(2, 'Food'), " +
            "(3, 'Clothes'), " +
            "(4, 'Travel'), " +
            "(5, 'Books'), " +
            "(6, 'Bills'), " +
            "(7, 'Health'), " +
            "(8, 'Entertainment');",
            Constants.Table.Categories.TABLE_NAME
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_EXPENSES);
        db.execSQL(ADD_DEFAULT_CATEGORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Table.Categories.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Table.Expenses.TABLE_NAME);

        // recreate tables
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}