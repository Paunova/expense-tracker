package mk.ukim.finki.expensetracker.db.repository;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import mk.ukim.finki.expensetracker.models.BaseEntity;

/**
 * Data access repository.
 */
public interface Repository<T extends BaseEntity> {

    T get(Long id);
    List<T> getAll();
    boolean insert(T t);
    boolean update(T t);
    void delete(Long id);
    void deleteEntry(T t);

    void open();
    void close();

    T cursorToValue(Cursor c);
    ContentValues valueToContentValues(T t);
}
