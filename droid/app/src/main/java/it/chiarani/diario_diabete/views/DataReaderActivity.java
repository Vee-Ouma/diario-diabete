package it.chiarani.diario_diabete.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

public class DataReaderActivity extends BaseActivity implements ListItemClickListener {

    private ActivityDataReaderBinding binding;
    private List<TagsEntity> mSelectedTags = new ArrayList<>();
    private List<TagsEntity> mItemTags = new ArrayList<>();
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
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String currentTheme = sharedPref.getString("theme", "light");
        if (currentTheme.equals("light")) {
            this.setTheme(R.style.AppTheme);
        } else {
            this.setTheme(R.style.AppDarkTheme);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.parseColor("#1C1C27"));

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
                        }

                        GridLayoutManager gridLayout = new GridLayoutManager(this, 4);

                        binding.activityDataReaderRVTags.setLayoutManager(gridLayout);

                        TagsAdapter adapterTags = new TagsAdapter(mItemTags, this::onItemClick);

                        binding.activityDataReaderRVTags.setAdapter(adapterTags);
                    }
                },throwable -> {
                    Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
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
                mDiabeteReadingEntity.setEaten2h(true);
            } else {
                mDiabeteReadingEntity.setFasting(false);
            }

        });

        binding.activityDataReaderSwitchFasting.setOnToggledListener((toggleableView, isOn) -> {
            if(isOn) {
                binding.activityDataReaderSwitchEatenBy.setOn(false);
                mDiabeteReadingEntity.setFasting(true);
            }
            else {
                mDiabeteReadingEntity.setEaten2h(false);
            }

        });

    }

    protected void setConfirmDiabeteReadingListener() {
        binding.activityDataReaderConfirm.setOnClickListener(v -> {

            if(binding.activityDataReaderInputRead.getText() != null && binding.activityDataReaderInputRead.getText().toString().isEmpty()) {
                Toast.makeText(this, "Inserisci prima un valore di glicemia", Toast.LENGTH_LONG).show();
                return;
            }

            if(!mDiabeteReadingEntity.isEaten2h() && !mDiabeteReadingEntity.isFasting()) {
                Toast.makeText(this, "Specifica una clausola digiuno prima", Toast.LENGTH_LONG).show();
                return;
            }

            if(binding.activityDataReaderInputRead.getText() != null) {
                mDiabeteReadingEntity.setValue(Float.parseFloat(binding.activityDataReaderInputRead.getText().toString()));
            }
            else {
                Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
            }

            mDisposable.add(mUserViewModel.getUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( userEntity -> {

                        if(userEntity == null) {
                            Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
                        }

                        // add reading

                        UserEntity tmpUser = userEntity;
                        List<DiabeteReadingEntity> tmp;

                        if(tmpUser.getReadings() != null) {
                            tmp = tmpUser.getReadings();
                        }
                        else {
                            tmp = new ArrayList<>();
                        }
                        mDiabeteReadingEntity.setTags(mSelectedTags);

                        tmp.add(mDiabeteReadingEntity);
                        tmpUser.setReadings(tmp);

                        if(blockObserver) {
                            // update user to ROOM
                            blockObserver = false;

                            mDisposable.add(mUserViewModel.insertUser(tmpUser)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe( () -> {
                                        // done

                                        onBackPressed();
                                    }, throwable -> {
                                        Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
                                    }));
                        }
                    }, throwable -> {
                        Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
                    }));
        });
    }
}
