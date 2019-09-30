package it.chiarani.diario_diabete.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.databinding.ActivityMainBinding;
import it.chiarani.diario_diabete.fragments.BottomNavigationDrawerFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    // https://medium.com/over-engineering/hands-on-with-material-components-for-android-bottom-app-bar-28835a1feb82

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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       this.setSupportActionBar(binding.bottomAppBar);

        binding.bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open bottom sheet
                BottomNavigationDrawerFragment bottomSheetDialogFragment = new BottomNavigationDrawerFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }

}
