package mk.ukim.finki.expensetracker.fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import mk.ukim.finki.expensetracker.R;
import mk.ukim.finki.expensetracker.db.repository.CategoryRepository;
import mk.ukim.finki.expensetracker.db.repository.Repository;
import mk.ukim.finki.expensetracker.models.Category;

public class AddCategory extends DialogFragment {

    Button save_ctg;
    Button close_btn;
    EditText ctg_name;
    Repository<Category> categoryRepository;

    private DialogInterface.OnDismissListener onDismissListener;

    static AddCategory newInstance() {
        return new AddCategory();
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryRepository = CategoryRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_category, container, false);

        save_ctg = (Button) v.findViewById(R.id.save_cgt_btn);
        close_btn = (Button) v.findViewById(R.id.close_btn);
        ctg_name = (EditText) v.findViewById(R.id.ctg_name);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        save_ctg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ctg_name.getText().toString();
                if (!name.equals("")) {
                    Category newCategory = new Category();
                    newCategory.name = name;
                    categoryRepository.insert(newCategory);

                    getDialog().dismiss();
                }
            }
        });
        return v;
    }
}