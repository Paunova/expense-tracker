package mk.ukim.finki.expensetracker.db.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.expensetracker.db.DatabaseHelper;
import mk.ukim.finki.expensetracker.models.BaseEntity;
import mk.ukim.finki.expensetracker.utilities.Constants;

abstract public class BaseRepository<T extends BaseEntity> implements Repository<T> {

    // Database fields
    private SQLiteDatabase database;
    private static DatabaseHelper dbHelper;

    public BaseRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    abstract public String getTableName();
    abstract public String[] getAllColumns();
    abstract public String getKeyColumn();

    @Override
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public static void closeDb() {
        dbHelper.closeDB();
    }

    @Override
    public List<T> getAll() {

        List<T> items = new ArrayList<>();

        Cursor cursor = database.query(getTableName(), getAllColumns(),
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                items.add(cursorToValue(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    @Override
    public T get(Long id) {

        Cursor cursor = database.query(getTableName(), getAllColumns(),
                Constants.Table.Categories.KEY_ID + " = " + id, null, null,
                null, null);
        try {
            if (cursor.moveToFirst()) {
                return cursorToValue(cursor);
            } else {
                // no items found
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean insert(T t) {

        long insertId = database.insert(getTableName(), null,
                valueToContentValues(t));

        if (insertId > 0) {
            t.id = insertId;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(T item) {
        long numRowsAffected = database.update(getTableName(),
                valueToContentValues(item), getKeyColumn() + " = "
                        + item.id, null);
        return numRowsAffected > 0;
    }

    @Override
    public void delete(Long id) {
        database.delete(getTableName(),
                getKeyColumn() + " = " + id, null);
    }

    @Override
    public void deleteEntry(T t) {
        delete(t.id);
    }
}
