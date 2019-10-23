package it.chiarani.diario_diabete.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.adapters.ReadingsAdapter;
import it.chiarani.diario_diabete.adapters.ReadingsDetailsAdapter;
import it.chiarani.diario_diabete.databinding.ActivityMainBinding;
import it.chiarani.diario_diabete.databinding.ActivityReadingDetailBinding;
import it.chiarani.diario_diabete.db.Injection;
import it.chiarani.diario_diabete.fragments.ReadingDetailFragment;
import it.chiarani.diario_diabete.viewmodels.UserViewModel;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;

public class ReadingDetailActivity extends BaseActivity {

    private UserViewModel mUserViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    ActivityReadingDetailBinding binding;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_reading_detail;
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

        binding.activityReadingDetailBtnBack.setOnClickListener( v-> this.onBackPressed());

        mDisposable.add(mUserViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( userEntity -> {

                    if(userEntity.getReadings() == null) {
                        Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
                        return;
                    }

                    // reverse for get the latest element as first
                    Collections.reverse(userEntity.getReadings());

                    LinearLayoutManager linearLayoutManagerTags = new LinearLayoutManager(this);
                    linearLayoutManagerTags.setOrientation(RecyclerView.VERTICAL);

                    binding.activityReadingDetailRvReadings.setLayoutManager(linearLayoutManagerTags);

                    Collections.reverse(userEntity.getReadings());
                    ReadingsDetailsAdapter adapterTags = new ReadingsDetailsAdapter(userEntity.getReadings(), null);
                    binding.activityReadingDetailRvReadings.setAdapter(adapterTags);
                    binding.activityReadingDetailRvReadings.setNestedScrollingEnabled(false);

                }, throwable -> {
                    Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
                }));

    }

    @Override
    protected void onStop() {
        super.onStop();
        mDisposable.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mDisposable.clear();
    }
}
