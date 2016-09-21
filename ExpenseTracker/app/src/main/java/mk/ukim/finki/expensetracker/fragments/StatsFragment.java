package mk.ukim.finki.expensetracker.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import mk.ukim.finki.expensetracker.R;
import mk.ukim.finki.expensetracker.utilities.StatisticsFacade;
import mk.ukim.finki.expensetracker.viewModels.CategoryView;

public class StatsFragment extends Fragment {

    private StatisticsFacade statisticsFacade;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statisticsFacade = StatisticsFacade.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.stats_layout,container,false);

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        int index = 0;
        for (CategoryView cv : statisticsFacade.getTotalAmountsOfCategories()) {
            entries.add(new Entry(cv.getTotalAmount(), index));
            labels.add(cv.getName());
            index++;
        }

        PieDataSet dataset = new PieDataSet(entries, "Expences by categories");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        PieChart chart = (PieChart) fragmentView.findViewById(R.id.chart);
        chart.animateY(2000);
        PieData data = new PieData(labels, dataset);
        chart.setData(data);
        chart.setDescription("Check out total amount of each category");
        return fragmentView;
    }
}