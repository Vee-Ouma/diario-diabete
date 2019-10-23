package it.chiarani.diario_diabete.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.adapters.ReadingItemClickListener;
import it.chiarani.diario_diabete.adapters.ReadingsAdapter;
import it.chiarani.diario_diabete.databinding.ActivityMainBinding;
import it.chiarani.diario_diabete.db.Injection;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;
import it.chiarani.diario_diabete.fragments.BottomNavigationDrawerFragment;
import it.chiarani.diario_diabete.fragments.ReadingDetailFragment;
import it.chiarani.diario_diabete.viewmodels.UserViewModel;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;

public class MainActivity extends BaseActivity implements ReadingItemClickListener {

    private ActivityMainBinding binding;
    private UserViewModel mUserViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private final int MAX_VIEW_READINGS = 7;


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
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setSupportActionBar(binding.bottomAppBar);

        setBottomAppBarHamburgerListener();

        setUI();

        // btn listeners
        binding.activityMainTxtViewAllReadings.setOnClickListener(view -> launchActivity(ReadingDetailActivity.class) );
        binding.fab.setOnClickListener(view -> launchActivity(DataReaderActivity.class) );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }

    private void setBottomAppBarHamburgerListener() {
        binding.bottomAppBar.setNavigationOnClickListener(view -> {
            BottomNavigationDrawerFragment bottomSheetDialogFragment = new BottomNavigationDrawerFragment();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), "bottom_nav_sheet_dialog");
        });
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

        mDisposable.add(mUserViewModel.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( userEntity -> {

                if(userEntity.getReadings() == null) {
                    Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
                    return;
                }

                // set list of recent readings

                setReadingList(userEntity);
            }, throwable -> {
                Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
            }));
    }


    @Override
    public void onItemClick(int position) {

        mDisposable.add(mUserViewModel.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( userEntity -> {

                Collections.reverse(userEntity.getReadings());
                ReadingDetailFragment fragment = new ReadingDetailFragment(userEntity.getReadings().get(position));
                fragment.show(getSupportFragmentManager(), "bottom_nav_sheet_dialog1");

            }, throwable -> {
                Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
            }));
    }

    private void setUI() {

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

                binding.activityMainGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
                binding.activityMainGraph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
                binding.activityMainGraph.getGridLabelRenderer().setVerticalLabelsVisible(true);
                binding.activityMainGraph.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#bab2ec")); // TODO: color
                binding.activityMainGraph.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#bab2ec"));

                binding.activityMainGraph.addSeries(base);

                // set date label formatter
                binding.activityMainGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));

                binding.activityMainGraph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space


                // set list of recent readings
                setReadingList(userEntity);

            }, throwable -> {
                // update with default items
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

                mDisposable.add(mUserViewModel.insertUser(test)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( () -> {
                            // done
                        }));
            }));

    }

    private void launchActivity(Class<?> activityClassToLaunch) {
        Intent intent = new Intent(this, activityClassToLaunch);
        startActivity(intent);
    }

    private void setReadingList(UserEntity userEntity) {
        LinearLayoutManager linearLayoutManagerTags = new LinearLayoutManager(this);
        linearLayoutManagerTags.setOrientation(RecyclerView.HORIZONTAL);

        binding.activityMainRvReadings.setLayoutManager(linearLayoutManagerTags);

        Collections.reverse(userEntity.getReadings());
        ReadingsAdapter adapterTags;

        if(userEntity.getReadings().size() > MAX_VIEW_READINGS) {
            adapterTags = new ReadingsAdapter(userEntity.getReadings().subList(0,MAX_VIEW_READINGS-1), this);
        } else {
            adapterTags = new ReadingsAdapter(userEntity.getReadings(), this);
        }

        binding.activityMainRvReadings.setAdapter(adapterTags);
    }
}


// https://medium.com/over-engineering/hands-on-with-material-components-for-android-bottom-app-bar-28835a1feb82
