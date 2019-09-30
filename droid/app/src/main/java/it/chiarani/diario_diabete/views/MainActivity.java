package it.chiarani.diario_diabete.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.databinding.ActivityMainBinding;

import android.os.Bundle;

import com.crashlytics.android.Crashlytics;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

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

//        binding.test.setText("ciao");
    }
}
