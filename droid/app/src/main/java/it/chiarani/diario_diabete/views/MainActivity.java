package it.chiarani.diario_diabete.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import it.chiarani.diario_diabete.adapters.FragmentCallbackListener;
import it.chiarani.diario_diabete.adapters.ReadingItemClickListener;
import it.chiarani.diario_diabete.adapters.ReadingsAdapter;
import it.chiarani.diario_diabete.adapters.ReadingsDetailsAdapter;
import it.chiarani.diario_diabete.databinding.ActivityMainBinding;
import it.chiarani.diario_diabete.db.Injection;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;
import it.chiarani.diario_diabete.fragments.BottomNavigationDrawerFragment;
import it.chiarani.diario_diabete.fragments.GraphFragment;
import it.chiarani.diario_diabete.fragments.ReadingDetailFragment;
import it.chiarani.diario_diabete.viewmodels.UserViewModel;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;

public class MainActivity extends BaseActivity implements ReadingItemClickListener, FragmentCallbackListener {

    private ActivityMainBinding binding;
    private UserViewModel mUserViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private final int MAX_VIEW_READINGS = 15;

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

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String currentTheme = sharedPref.getString("theme", "light");
        if (currentTheme.equals("light")) {
            this.setTheme(R.style.AppTheme);
        } else {
            this.setTheme(R.style.AppDarkTheme);
        }

        super.onCreate(savedInstanceState);

        this.setSupportActionBar(binding.bottomAppBar);

        setBottomAppBarHamburgerListener();

        setUI();

        // btn listeners
        binding.activityMainTxtViewAllReadings.setOnClickListener(view -> launchActivity(ReadingDetailActivity.class) );
        binding.fab.setOnClickListener(view -> launchActivity(DataReaderActivity.class) );

        binding.activityMainTxtGraph.setOnClickListener( view -> {
            GraphFragment bottomSheetDialogFragment = new GraphFragment();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), "graph_fragment");
        });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.parseColor("#1C1C27"));

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
                ReadingDetailFragment fragment = new ReadingDetailFragment(position, this::onFragmentClosed);
                fragment.show(getSupportFragmentManager(), "bottom_nav_sheet_dialog1");

            }, throwable -> {
                Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
            }));
    }

    private void setBottomAppBarHamburgerListener() {
        binding.bottomAppBar.setNavigationOnClickListener(view -> {
            BottomNavigationDrawerFragment bottomSheetDialogFragment = new BottomNavigationDrawerFragment();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), "bottom_nav_sheet_dialog");
        });
    }


    @Override
    public void onBackPressed() {
        // nothing
    }

    private void setUI() {

        mDisposable.add(mUserViewModel.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( userEntity -> {

                if(userEntity.getReadings() == null) {
                    return;
                }

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
        linearLayoutManagerTags.setOrientation(RecyclerView.VERTICAL);

        binding.activityMainRvReadings.setLayoutManager(linearLayoutManagerTags);

        Collections.reverse(userEntity.getReadings());
        ReadingsDetailsAdapter adapterTags;

        if(userEntity.getReadings().size() > MAX_VIEW_READINGS) {
            adapterTags = new ReadingsDetailsAdapter(userEntity.getReadings().subList(0,MAX_VIEW_READINGS-1), this::onItemClick);
        } else {
            adapterTags = new ReadingsDetailsAdapter(userEntity.getReadings(), this::onItemClick);
        }

        binding.activityMainRvReadings.setAdapter(adapterTags);
    }

    @Override
    public void onFragmentClosed() {
        this.onResume();
    }
}

// https://medium.com/over-engineering/hands-on-with-material-components-for-android-bottom-app-bar-28835a1feb82