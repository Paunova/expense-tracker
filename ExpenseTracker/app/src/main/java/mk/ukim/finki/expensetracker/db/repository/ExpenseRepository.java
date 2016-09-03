package mk.ukim.finki.expensetracker.db.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.joda.time.DateTime;

import mk.ukim.finki.expensetracker.models.Expense;
import mk.ukim.finki.expensetracker.utilities.Constants;

/**
 * Repository class for {@link mk.ukim.finki.expensetracker.models.Expense}.
 */
public class ExpenseRepository extends BaseRepository<Expense> {

    private String[] allColumns = {
            Constants.Table.Expenses.KEY_ID,
            Constants.Table.Expenses.CATEGORY_ID,
            Constants.Table.Expenses.AMOUNT,
            Constants.Table.Expenses.DESCRIPTION,
            Constants.Table.Expenses.DATE,
    };

    public ExpenseRepository(Context context) {
        super(context);
    }

    @Override
    public String getTableName() {
        return Constants.Table.Expenses.TABLE_NAME;
    }

    @Override
    public String[] getAllColumns() {
        return allColumns;
    }

    @Override
    public String getKeyColumn() {
        return Constants.Table.Expenses.KEY_ID;
    }

    @Override
    public Expense cursorToValue(Cursor cursor) {

        Expense expense = new Expense();
        expense.id = cursor.getLong(
                cursor.getColumnIndex(Constants.Table.Expenses.KEY_ID)
        );

        expense.categoryId = cursor.getLong(
                cursor.getColumnIndex(Constants.Table.Expenses.CATEGORY_ID)
        );

        expense.description = cursor.getString(
                cursor.getColumnIndex(Constants.Table.Expenses.DESCRIPTION)
        );

        expense.amount = cursor.getDouble(
                cursor.getColumnIndex(Constants.Table.Expenses.AMOUNT)
        );

        expense.dateTime = new DateTime(cursor.getString(
                cursor.getColumnIndex(Constants.Table.Expenses.DATE)
        ));

        return expense;
    }

    @Override
    public ContentValues valueToContentValues(Expense expense) {

        ContentValues values = new ContentValues();
        if (expense.id != null) {
            values.put(Constants.Table.Expenses.KEY_ID, expense.id);
        }
        values.put(Constants.Table.Expenses.CATEGORY_ID, expense.categoryId);
        values.put(Constants.Table.Expenses.AMOUNT, expense.amount);
        values.put(Constants.Table.Expenses.DESCRIPTION, expense.description);
        values.put(Constants.Table.Expenses.DATE, expense.dateTime.toString());
        return values;
    }
}