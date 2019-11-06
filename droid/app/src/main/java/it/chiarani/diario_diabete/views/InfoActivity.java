package it.chiarani.diario_diabete.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;

import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.adapters.TabAdapter;
import it.chiarani.diario_diabete.databinding.ActivityInfoBinding;
import it.chiarani.diario_diabete.fragments.InfoFragment;
import it.chiarani.diario_diabete.fragments.SettingsFragment;

public class InfoActivity extends BaseActivity {

    ActivityInfoBinding binding;
    private TabAdapter adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_info;
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

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String currentTheme = sharedPref.getString("theme", "light");
        if (currentTheme.equals("light")) {
            this.setTheme(R.style.AppTheme);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        } else {
            this.setTheme(R.style.AppDarkTheme);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.parseColor("#1C1C27"));
        }

        super.onCreate(savedInstanceState);

        adapter = new TabAdapter(getSupportFragmentManager());

        adapter.addFragment(new InfoFragment(), "info");
        adapter.addFragment(new SettingsFragment(), "impostazioni");
        binding.activityInfoViewPager.setAdapter(adapter);
        binding.activityInfoTabLayout.setupWithViewPager(binding.activityInfoViewPager);
    }
}
