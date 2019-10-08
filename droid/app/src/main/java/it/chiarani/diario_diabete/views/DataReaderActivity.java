package it.chiarani.diario_diabete.views;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.adapters.TagsAdapter;
import it.chiarani.diario_diabete.databinding.ActivityDataReaderBinding;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        binding.activityDataReaderInputRead.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.activityDataReaderInputRead, InputMethodManager.SHOW_IMPLICIT);

        LinearLayoutManager linearLayoutManagerTags = new LinearLayoutManager(this);
        linearLayoutManagerTags.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.activityDataReaderRVTags.setLayoutManager(linearLayoutManagerTags);

        List<TagsEntity> itemsTags = new ArrayList<>();
        itemsTags.add(new TagsEntity(1, "Pranzo", ""));
        itemsTags.add(new TagsEntity(2, "Cena", ""));
        itemsTags.add(new TagsEntity(3, "Cioccolata", ""));
        TagsAdapter adapterTags = new TagsAdapter(itemsTags);

        binding.activityDataReaderRVTags.setAdapter(adapterTags);


        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        binding.activityDataReaderDatePicker.setText(currentDate);
        binding.activityDataReaderTimePicker.setText(currentTime);
    }
}
