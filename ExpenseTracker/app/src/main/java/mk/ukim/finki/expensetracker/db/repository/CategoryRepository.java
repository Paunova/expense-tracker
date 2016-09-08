package mk.ukim.finki.expensetracker.db.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import mk.ukim.finki.expensetracker.models.Category;
import mk.ukim.finki.expensetracker.utilities.Constants;

/**
 * Repository class for {@link Category}.
 */
public class CategoryRepository extends BaseRepository<Category> {

    private static CategoryRepository categoryRepository;

    public static CategoryRepository getInstance(Context context) {
        if (categoryRepository == null) {
            categoryRepository = new CategoryRepository(context);
            categoryRepository.open();
        }
        return categoryRepository;
    }

    private String[] allColumns = {
            Constants.Table.Categories.KEY_ID,
            Constants.Table.Categories.NAME
    };

    private CategoryRepository(Context context) {
        super(context);
    }

    @Override
    public boolean insert(Category category) {
        boolean result = super.insert(category);
        ExpenseRepository.refreshCategories();
        return result;
    }

    @Override
    public String getTableName() {
        return Constants.Table.Categories.TABLE_NAME;
    }

    @Override
    public String[] getAllColumns() {
        return allColumns;
    }

    @Override
    public String getKeyColumn() {
        return Constants.Table.Categories.KEY_ID;
    }

    @Override
    public Category cursorToValue(Cursor cursor) {
        Category category = new Category();
        category.id = cursor.getLong(
                cursor.getColumnIndex(Constants.Table.Categories.KEY_ID)
        );

        category.name = cursor.getString(
                cursor.getColumnIndex(Constants.Table.Categories.NAME)
        );

        return category;
    }

    @Override
    public ContentValues valueToContentValues(Category category) {
        ContentValues values = new ContentValues();
        if (category.id != null) {
            values.put(Constants.Table.Categories.KEY_ID, category.id);
        }
        values.put(Constants.Table.Categories.NAME, category.name);
        return values;
    }
}
