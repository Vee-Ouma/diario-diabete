package it.chiarani.diario_diabete.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.adapters.ReadingsAdapter;
import it.chiarani.diario_diabete.adapters.TagsAdapter;
import it.chiarani.diario_diabete.databinding.ActivityMainBinding;
import it.chiarani.diario_diabete.db.Injection;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;
import it.chiarani.diario_diabete.fragments.BottomNavigationDrawerFragment;
import it.chiarani.diario_diabete.viewmodels.UserViewModel;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private ViewModelFactory mViewModelFactory;
    private UserViewModel mUserViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutID());
    }

    @Override
    protected void setViewModel() {
        mViewModelFactory = Injection.provideViewModelFactory(this);
        mUserViewModel    = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setSupportActionBar(binding.bottomAppBar);
        setBottomAppBarHamburgerListener();



        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 0),
                new DataPoint(7, 6)
        });
        graph.addSeries(series);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#bab2ec"));
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#bab2ec"));


        mUserViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( userEntity -> {
                    // done
                    int x = 1;

                    LinearLayoutManager linearLayoutManagerTags = new LinearLayoutManager(this);
                    linearLayoutManagerTags.setOrientation(RecyclerView.VERTICAL);



                    binding.rv.setLayoutManager(linearLayoutManagerTags);
                    userEntity.getReadings();

                    Collections.reverse(userEntity.getReadings());
                    ReadingsAdapter adapterTags = new ReadingsAdapter(userEntity.getReadings(), null);
                //    TagsAdapter adapterTags = new TagsAdapter(userEntity.getReadings().get(2).getTags(), null);

                    binding.rv.setAdapter(adapterTags);
                }, throwable -> {
                    List<TagsEntity> itemsTags = new ArrayList<>();
                    itemsTags.add(new TagsEntity(1, "Mattina", ""));
                    itemsTags.add(new TagsEntity(2, "Prima del pasto", ""));
                    itemsTags.add(new TagsEntity(3, "Dopo il pasto", ""));
                    itemsTags.add(new TagsEntity(4, "Sera", ""));
                    itemsTags.add(new TagsEntity(5, "Camminare", ""));
                    itemsTags.add(new TagsEntity(6, "Sport", ""));
                    itemsTags.add(new TagsEntity(7, "Pranzo", ""));
                    itemsTags.add(new TagsEntity(8, "Cena", ""));
                    itemsTags.add(new TagsEntity(9, "Spuntino", ""));
                    itemsTags.add(new TagsEntity(10, "Merenda", ""));
                    UserEntity test = new UserEntity(1, null, "fabio", "chiarani", 11, 11, true, 11, itemsTags);

                    mUserViewModel.insertUser(test)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe( () -> {
                                // done
                                int x = 2;
                            });
                });



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }



        });
      /*  mDisposable.add(mUserViewModel.insertUser(test)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( () -> {
                    // done
                    int x = 2;
                }, throwable -> {
                   int x = 1;
                }));*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }

    private void setBottomAppBarHamburgerListener() {
        binding.bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open bottom sheet
                BottomNavigationDrawerFragment bottomSheetDialogFragment = new BottomNavigationDrawerFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "bottom_nav_sheet_dialog");


            }
        });
    }

    private void next() {


        Intent intent = new Intent(this, DataReaderActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mUserViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( userEntity -> {
                    // done
                    int x = 1;

                    LinearLayoutManager linearLayoutManagerTags = new LinearLayoutManager(this);
                    linearLayoutManagerTags.setOrientation(RecyclerView.VERTICAL);



                    binding.rv.setLayoutManager(linearLayoutManagerTags);
                    userEntity.getReadings();
                    Collections.reverse(userEntity.getReadings());
                    ReadingsAdapter adapterTags = new ReadingsAdapter(userEntity.getReadings(), null);
                    //    TagsAdapter adapterTags = new TagsAdapter(userEntity.getReadings().get(2).getTags(), null);

                    binding.rv.setAdapter(adapterTags);
                }, throwable -> {

                });
    }
}


// https://medium.com/over-engineering/hands-on-with-material-components-for-android-bottom-app-bar-28835a1feb82
