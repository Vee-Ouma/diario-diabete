package it.chiarani.diario_diabete.views;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.adapters.ListItemClickListener;
import it.chiarani.diario_diabete.adapters.TagsAdapter;
import it.chiarani.diario_diabete.databinding.ActivityDataReaderBinding;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataReaderActivity extends BaseActivity implements ListItemClickListener {

    ActivityDataReaderBinding binding;
    List<TagsEntity> selectedTags = new ArrayList<>();
    List<TagsEntity> itemsTags = new ArrayList<>();


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


        itemsTags.add(new TagsEntity(1, "Pranzo", ""));
        itemsTags.add(new TagsEntity(2, "Cena", ""));
        itemsTags.add(new TagsEntity(3, "Cioccolata", ""));
        TagsAdapter adapterTags = new TagsAdapter(itemsTags, this::onItemClick);

        binding.activityDataReaderRVTags.setAdapter(adapterTags);


        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        binding.activityDataReaderDatePicker.setText(currentDate);
        binding.activityDataReaderTimePicker.setText(currentTime);

        binding.activityDataReaderDatePicker.setOnClickListener(v -> {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            binding.activityDataReaderDatePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        binding.activityDataReaderTimePicker.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            binding.activityDataReaderTimePicker.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        });

        binding.activityDataReaderSwitchEatenBy.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn) {
                    binding.activityDataReaderSwitchFasting.setOn(false);
                }
            }
        });

        binding.activityDataReaderSwitchFasting.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn) {
                    binding.activityDataReaderSwitchEatenBy.setOn(false);
                }
            }
        });

        binding.activityDataReaderConfirm.setOnClickListener(v -> {
            int x = 1;
        });
    }

    @Override
    public void onItemClick(int position) {
        if(selectedTags.contains(itemsTags.get(position))) {
            int index = selectedTags.indexOf(itemsTags.get(position));
            selectedTags.remove(index);
        } else {
            selectedTags.add(itemsTags.get(position));
        }
    }
}
