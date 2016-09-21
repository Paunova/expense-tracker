package mk.ukim.finki.expensetracker.utilities;

import android.content.Context;

import java.util.ArrayList;

import mk.ukim.finki.expensetracker.db.repository.CategoryRepository;
import mk.ukim.finki.expensetracker.db.repository.ExpenseRepository;
import mk.ukim.finki.expensetracker.db.repository.Repository;
import mk.ukim.finki.expensetracker.models.Category;
import mk.ukim.finki.expensetracker.models.Expense;
import mk.ukim.finki.expensetracker.viewModels.CategoryView;

public class StatisticsFacade {

    private static StatisticsFacade statisticsFacade;

    private Repository<Category> categoryRepository;
    private Repository<Expense> expenseRepository;

    private StatisticsFacade(Context context) {
        categoryRepository = CategoryRepository.getInstance(context);
        expenseRepository = ExpenseRepository.getInstance(context);
    }

    public static StatisticsFacade getInstance(Context context) {
        if (statisticsFacade == null) {
            statisticsFacade = new StatisticsFacade(context);
        }
        return statisticsFacade;
    }

    public ArrayList<String> getAllCategoriesNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Category c : categoryRepository.getAll()) {
            names.add(c.name);
        }
        return names;
    }

    public ArrayList<CategoryView> getTotalAmountsOfCategories() {

        ArrayList<CategoryView> getAll = new ArrayList<>();

        for (Category c : categoryRepository.getAll()) {
            float totalAmount = 0;
            for (Expense e : expenseRepository.getAll()) {
                if (c.id == e.categoryId) {
                    totalAmount += e.amount;
                }
            }
            CategoryView cv = new CategoryView();
            cv.setName(c.getName());
            cv.setTotalAmount(totalAmount);

            if (cv.getTotalAmount() != 0) {
                getAll.add(cv);
            }
        }
        return getAll;
    }
}