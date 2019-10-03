package it.chiarani.diario_diabete.views;

import androidx.databinding.DataBindingUtil;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.databinding.ActivityDataReaderBinding;

import android.os.Bundle;

public class DataReaderActivity extends BaseActivity {

    ActivityDataReaderBinding binding;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_data_reader;
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
