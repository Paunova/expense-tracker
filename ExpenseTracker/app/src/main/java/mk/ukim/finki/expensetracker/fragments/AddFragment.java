package mk.ukim.finki.expensetracker.fragments;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.joda.time.DateTime;

import mk.ukim.finki.expensetracker.R;
import mk.ukim.finki.expensetracker.db.repository.CategoryRepository;
import mk.ukim.finki.expensetracker.db.repository.ExpenseRepository;
import mk.ukim.finki.expensetracker.db.repository.Repository;
import mk.ukim.finki.expensetracker.models.Category;
import mk.ukim.finki.expensetracker.models.Expense;

public class AddFragment extends Fragment {

    Repository<Category> categoryRepository;
    Repository<Expense> expenseRepository;
    DateTime selectedDate = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryRepository = CategoryRepository.getInstance(getContext());
        expenseRepository = ExpenseRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.add_layout,container,false);
        final EditText amount = (EditText) fragmentView.findViewById(R.id.add_amount);
        final EditText description = (EditText) fragmentView.findViewById(R.id.expense_description);
        final Spinner spinner = (Spinner)fragmentView.findViewById(R.id.choose_category);

        final ArrayAdapter<Category> categoriesAdapter =
                new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_item);

        spinner.setAdapter(categoriesAdapter);
        categoriesAdapter.addAll(categoryRepository.getAll());

        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Button confirm = (Button)fragmentView.findViewById(R.id.button_add);
        Button addCtg = (Button) fragmentView.findViewById(R.id.addCtg);
        Button rmvCrg = (Button) fragmentView.findViewById(R.id.rmvCtg);
        final TextView dateView = (TextView) fragmentView.findViewById(R.id.date_view);
        dateView.setText("Set Date");

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment date = new DatePickerFragment();

                date.setCallBack(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateView.setText(i2 + "." + i1 + "." + i);
                        DateTime selection = new DateTime(i, i1, i2, 1 ,1);
                        selectedDate = selection;
                    }
                });
                date.show(getActivity().getFragmentManager(), "datePicker");
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Expense expense = new Expense();
                expense.description = description.getText().toString();
                expense.amount = Integer.parseInt(amount.getText().toString());
                if (selectedDate == null) {
                    expense.dateTime = DateTime.now();
                } else {
                    DateTime selection = selectedDate;
                    expense.dateTime = selection;
                }
                Category c = (Category) spinner.getSelectedItem();
                expense.categoryId = c.id;
                String message;
                if (expenseRepository.insert(expense)) {
                    message = getResources().getString(R.string.success);
                } else {
                    message = getResources().getString(R.string.error);
                }
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        addCtg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = AddCategory.newInstance();
                AddCategory newCategory = new AddCategory();
                newCategory.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        categoriesAdapter.clear();
                        categoriesAdapter.addAll(categoryRepository.getAll());
                        categoriesAdapter.notifyDataSetChanged();
                        spinner.setSelection(spinner.getCount() - 1);
                    }
                });
                newCategory.show(getActivity().getFragmentManager(), "dialog");
            }
        });

        rmvCrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category selectedCategory = (Category) spinner.getSelectedItem();
                categoryRepository.deleteEntry(selectedCategory);
                categoriesAdapter.clear();
                categoriesAdapter.addAll(categoryRepository.getAll());
                categoriesAdapter.notifyDataSetChanged();
            }
        });

        return fragmentView;
    }
}