package mk.ukim.finki.expensetracker.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.expensetracker.R;
import mk.ukim.finki.expensetracker.models.Category;
import mk.ukim.finki.expensetracker.models.Expense;

public class MyViewListAdapter extends ArrayAdapter<Expense> {

    DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");

    private List<Expense> allExpenses;
    private List<Expense> filteredExpenses;

    public MyViewListAdapter(Context context, int resource, List<Expense> expenses) {
        super(context, resource, expenses);
        allExpenses = expenses;
        filteredExpenses = allExpenses;
    }

    public void filterExpenses(String text, Long categoryId) {
        if (categoryId.equals(Category.ALL_CATEGORIES_ID)) {
            filteredExpenses = allExpenses;
        }
        else {
            filteredExpenses = new ArrayList<>();
            for (Expense e : allExpenses) {
                if (e.categoryId == categoryId) {
                    filteredExpenses.add(e);
                }
            }
        }

        List<Expense> filteredByText = new ArrayList<>();
        for (Expense e : filteredExpenses) {
            if (e.description.toLowerCase().contains(text.toLowerCase())) {
                filteredByText.add(e);
            }
        }
        filteredExpenses = filteredByText;
        refresh();
    }

    public void modifyExpensesList(List<Expense> newExpenses) {
        allExpenses = newExpenses;
        filteredExpenses = allExpenses;
        refresh();
    }

    private void refresh() {
        super.clear();
        super.addAll(filteredExpenses);
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listview_layout, null);
        }

        Expense expense = getItem(position);

        if (expense != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.amount);
            TextView tt2 = (TextView) v.findViewById(R.id.description);
            TextView tt3 = (TextView) v.findViewById(R.id.date);
            TextView tt4 = (TextView) v.findViewById(R.id.category);

            if (tt1 != null) {
                tt1.setText(String.format("%d", expense.amount));
            }

            if (tt2 != null) {
                tt2.setText(expense.description);
            }

            if (tt3 != null) {
                tt3.setText(dtfOut.print(expense.dateTime));
            }

            if (tt4 != null) {
                tt4.setText(expense.category.name);
            }
        }
        return v;
    }
}
