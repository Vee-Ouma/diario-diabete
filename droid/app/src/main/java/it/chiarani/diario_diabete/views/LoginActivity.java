package it.chiarani.diario_diabete.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.databinding.ActivityLoginBinding;

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
