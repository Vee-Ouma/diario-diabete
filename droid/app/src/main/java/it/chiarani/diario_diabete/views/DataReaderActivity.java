package it.chiarani.diario_diabete.views;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.adapters.ListItemClickListener;
import it.chiarani.diario_diabete.adapters.TagsAdapter;
import it.chiarani.diario_diabete.databinding.ActivityDataReaderBinding;
import it.chiarani.diario_diabete.db.Injection;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;
import it.chiarani.diario_diabete.viewmodels.UserViewModel;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.TimePicker;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataReaderActivity extends BaseActivity implements ListItemClickListener {

    private ActivityDataReaderBinding binding;
    private List<TagsEntity> mSelectedTags = new ArrayList<>();
    private List<TagsEntity> mItemTags = new ArrayList<>();
    private ViewModelFactory mViewModelFactory;
    private UserViewModel mUserViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private boolean blockObserver = true;
    private DiabeteReadingEntity mDiabeteReadingEntity = new DiabeteReadingEntity();

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
        mViewModelFactory = Injection.provideViewModelFactory(this);
        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestFocusOnTextInput();

        setTagsRecyclerView();

        setCurrentDateTime();

        setDateAndTimeClickListeners();

        setTogglesListeners();

        setConfirmDiabeteReadingListener();
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

    @Override
    public void onItemClick(int position) {
        if(mSelectedTags.contains(mItemTags.get(position))) {
            int index = mSelectedTags.indexOf(mItemTags.get(position));
            mSelectedTags.remove(index);
        } else {
            mSelectedTags.add(mItemTags.get(position));
        }
    }


    protected void requestFocusOnTextInput() {
        binding.activityDataReaderInputRead.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.activityDataReaderInputRead, InputMethodManager.SHOW_IMPLICIT);
    }

    protected void setTagsRecyclerView() {
        mDisposable.add(mUserViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( userEntity -> {
                    if(userEntity != null) {
                        if(userEntity.getAvailableTags() != null) {
                            mItemTags.addAll(userEntity.getAvailableTags());
                        } else {
                            mItemTags.add(new TagsEntity(1, "Pranzo", ""));
                            mItemTags.add(new TagsEntity(2, "Cena", ""));
                            mItemTags.add(new TagsEntity(3, "Cioccolata", ""));
                        }

                        GridLayoutManager gridLayout = new GridLayoutManager(this, 4);

                        binding.activityDataReaderRVTags.setLayoutManager(gridLayout);

                        TagsAdapter adapterTags = new TagsAdapter(mItemTags, this::onItemClick);

                        binding.activityDataReaderRVTags.setAdapter(adapterTags);
                    }
                },throwable -> {
                    // error
                }));
    }

    protected void setCurrentDateTime() {
        Date date = new Date();
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(date);

        binding.activityDataReaderDatePicker.setText(currentDate);
        binding.activityDataReaderTimePicker.setText(currentTime);

        mDiabeteReadingEntity.setReadingDate(currentDate);
        mDiabeteReadingEntity.setReadingHour(currentTime);
    }

    protected void setDateAndTimeClickListeners() {
        binding.activityDataReaderDatePicker.setOnClickListener(v -> {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.activityDataReaderDatePicker.setText(date);
                        mDiabeteReadingEntity.setReadingDate(date);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        });

        binding.activityDataReaderTimePicker.setOnClickListener(v -> {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> {
                        String time = hourOfDay + ":" + minute;
                        binding.activityDataReaderTimePicker.setText(time);
                        mDiabeteReadingEntity.setReadingHour(time);
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        });
    }

    protected void setTogglesListeners() {
        binding.activityDataReaderSwitchEatenBy.setOnToggledListener((toggleableView, isOn) -> {
            if(isOn) {
                binding.activityDataReaderSwitchFasting.setOn(false);
                mDiabeteReadingEntity.setFasting(true);
            }
            mDiabeteReadingEntity.setEaten2h(false);
        });

        binding.activityDataReaderSwitchFasting.setOnToggledListener((toggleableView, isOn) -> {
            if(isOn) {
                binding.activityDataReaderSwitchEatenBy.setOn(false);
                mDiabeteReadingEntity.setEaten2h(true);
            }
            mDiabeteReadingEntity.setFasting(false);
        });

    }

    protected void setConfirmDiabeteReadingListener() {
        binding.activityDataReaderConfirm.setOnClickListener(v -> {
            if(binding.activityDataReaderInputRead.getText() != null) {
                mDiabeteReadingEntity.setValue(Float.parseFloat(binding.activityDataReaderInputRead.getText().toString()));
            }
            else {
                // todo: message
            }

            mUserViewModel.getUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( userEntity -> {

                        UserEntity user = userEntity;
                        List<DiabeteReadingEntity> tmp;

                        if(user.getReadings() != null) {
                            tmp = user.getReadings();
                        }
                        else {
                            tmp = new ArrayList<>();
                        }
                        mDiabeteReadingEntity.setTags(mSelectedTags);

                        tmp.add(mDiabeteReadingEntity);
                        user.setReadings(tmp);

                        if(blockObserver) {
                            blockObserver = false;

                            mUserViewModel.insertUser(user)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe( () -> {
                                        // done

                                        onBackPressed();
                                    }, throwable -> {
                                        // Log.e(TAG, "Unable to update username", throwable);
                                    });
                        }else {
                            int k = 1;
                        }
                    }, throwable -> {
                        int x = 1;
                    });
        });
    }
}

/**
 mItemTags.add(new TagsEntity(1, "Pranzo", ""));
 mItemTags.add(new TagsEntity(2, "Cena", ""));
 mItemTags.add(new TagsEntity(3, "Cioccolata", ""));
 */