package it.chiarani.diario_diabete.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.databinding.ActivityMainBinding;
import it.chiarani.diario_diabete.db.Injection;
import it.chiarani.diario_diabete.fragments.BottomNavigationDrawerFragment;
import it.chiarani.diario_diabete.viewmodels.UserViewModel;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private ViewModelFactory mViewModelFactory;
    private UserViewModel mUserViewModel;

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

}


// https://medium.com/over-engineering/hands-on-with-material-components-for-android-bottom-app-bar-28835a1feb82
