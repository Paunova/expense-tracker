package mk.ukim.finki.expensetracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import mk.ukim.finki.expensetracker.R;

public class AddFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.add_layout,container,false);

        final EditText amount = (EditText) fragmentView.findViewById(R.id.add_amount);
        final EditText description = (EditText) fragmentView.findViewById(R.id.expense_description);
        final Spinner spinner = (Spinner)fragmentView.findViewById(R.id.choose_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories, android.R.layout.simple_spinner_item);
        // when creating the adapter, instead of reading from R.array.categories
        // we should read from the database
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button confirm = (Button)fragmentView.findViewById(R.id.button_add);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on click of this button should create an object of type Expense(tbd)
                // and save it in the database
                // and toast display success
                String category = (String) spinner.getSelectedItem();
                String s = "";
                s += amount.getText().toString() + " "
                        + category + " " + description.getText().toString();
                Log.d("ADD_AMOUNT", s);
            }
        });

        return fragmentView;
    }
}
