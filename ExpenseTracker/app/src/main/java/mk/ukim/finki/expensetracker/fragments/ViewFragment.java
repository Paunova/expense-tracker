package mk.ukim.finki.expensetracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import mk.ukim.finki.expensetracker.R;
import mk.ukim.finki.expensetracker.db.repository.CategoryRepository;
import mk.ukim.finki.expensetracker.db.repository.ExpenseRepository;
import mk.ukim.finki.expensetracker.db.repository.Repository;
import mk.ukim.finki.expensetracker.models.Category;
import mk.ukim.finki.expensetracker.models.Expense;
import mk.ukim.finki.expensetracker.utilities.StatisticsFacade;

public class ViewFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private ExpenseRepository expenseRepository;
    private Repository<Category> categoryRepository;
    MyViewListAdapter adapter;

    EditText inputSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenseRepository = ExpenseRepository.getInstance(getContext());
        categoryRepository = CategoryRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_layout,container,false);

        inputSearch = (EditText) v.findViewById(R.id.input_search);

        final Spinner spinner = (Spinner)v.findViewById(R.id.sort_by);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // When user changed the Text
                Category selectedCategory = (Category)spinner.getSelectedItem();
                adapter.filterExpenses(charSequence.toString(), selectedCategory.id);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ArrayAdapter<Category> filteringAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);

        Category categoryAll = new Category();
        categoryAll.name = getResources().getString(R.string.all_categories);
        categoryAll.id = Category.ALL_CATEGORIES_ID;
        filteringAdapter.add(categoryAll);
        filteringAdapter.addAll(categoryRepository.getAll());
        filteringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category selectedCategory = (Category)spinner.getSelectedItem();
                adapter.filterExpenses(inputSearch.getText().toString(), selectedCategory.id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setAdapter(filteringAdapter);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Expense> expenses = expenseRepository.getAll();
        adapter = new MyViewListAdapter(getContext(), R.layout.listview_layout, expenses);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            adapter.modifyExpensesList(expenseRepository.getAll());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // display a toast message with Item name which is being clicked
        Toast.makeText(getActivity(), "Item: " + i, Toast.LENGTH_SHORT)
                .show();
    }
}
