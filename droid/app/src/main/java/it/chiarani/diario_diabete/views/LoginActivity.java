package it.chiarani.diario_diabete.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.databinding.ActivityLoginBinding;

import android.os.Bundle;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;

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
    }
}
