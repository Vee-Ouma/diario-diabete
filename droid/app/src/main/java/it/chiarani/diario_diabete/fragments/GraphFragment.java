package it.chiarani.diario_diabete.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.databinding.FragmentGraphBinding;
import it.chiarani.diario_diabete.db.Injection;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;
import it.chiarani.diario_diabete.viewmodels.UserViewModel;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;

public class GraphFragment extends BottomSheetDialogFragment {

    FragmentGraphBinding binding;
    private UserViewModel mUserViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public GraphFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph, container, false);

        View view = binding.getRoot();

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(view.getContext());
        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);

        mDisposable.add(mUserViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( userEntity -> {

                    if(userEntity.getReadings() == null) {
                        return;
                    }

                    // build graph
                    // TODO: improve here
                    DataPoint[] dataPoints = new DataPoint[userEntity.getReadings().size()];

                    for (int i = 0; i < userEntity.getReadings().size(); i++) {
                        Calendar calendar = Calendar.getInstance();
                        String giorno   = userEntity.getReadings().get(i).getReadingDate().split("/")[0];
                        String mese     = userEntity.getReadings().get(i).getReadingDate().split("/")[1];
                        String anno     = userEntity.getReadings().get(i).getReadingDate().split("/")[2];
                        String ora      = userEntity.getReadings().get(i).getReadingHour().split(":")[0];
                        String min      = userEntity.getReadings().get(i).getReadingHour().split(":")[1];
                        calendar.set(Integer.parseInt(anno),Integer.parseInt(mese), Integer.parseInt(giorno), Integer.parseInt(ora), Integer.parseInt(min));
                        Date d1 = calendar.getTime();
                        // add new DataPoint object to the array for each of your list entries
                        dataPoints[i] = new DataPoint(d1, userEntity.getReadings().get(i).getValue());
                    }
                    LineGraphSeries<DataPoint> base = new LineGraphSeries<>(dataPoints);

                binding.fragmentGraphGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
                binding.fragmentGraphGraph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
                binding.fragmentGraphGraph.getGridLabelRenderer().setVerticalLabelsVisible(true);
                binding.fragmentGraphGraph.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#bab2ec")); // TODO: color
                binding.fragmentGraphGraph.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#bab2ec"));

                binding.fragmentGraphGraph.addSeries(base);

                // set date label formatter
                binding.fragmentGraphGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(view.getContext()));

                binding.fragmentGraphGraph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space


                }, throwable -> {
                    // update with default items
                }));

        return view;
    }
}
