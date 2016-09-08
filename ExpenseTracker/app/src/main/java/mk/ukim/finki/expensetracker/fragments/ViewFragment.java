package mk.ukim.finki.expensetracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import mk.ukim.finki.expensetracker.R;
import mk.ukim.finki.expensetracker.db.repository.ExpenseRepository;
import mk.ukim.finki.expensetracker.models.Expense;

public class ViewFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private ExpenseRepository expenseRepository;
    ArrayAdapter<Expense> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenseRepository = ExpenseRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_layout,container,false);
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
            adapter.clear();
            adapter.addAll(expenseRepository.getAll());
            adapter.notifyDataSetChanged();
            // this is not the best way
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // display a toast message with Item name which is being clicked
        Toast.makeText(getActivity(), "Item: " + i, Toast.LENGTH_SHORT)
                .show();
    }
}
