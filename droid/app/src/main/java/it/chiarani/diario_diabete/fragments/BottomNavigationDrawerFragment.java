package it.chiarani.diario_diabete.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.databinding.FragmentBottomNavigationDrawerBinding;
import it.chiarani.diario_diabete.db.Injection;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;
import it.chiarani.diario_diabete.viewmodels.UserViewModel;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;
import it.chiarani.diario_diabete.views.InfoActivity;
import it.chiarani.diario_diabete.views.MainActivity;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    FragmentBottomNavigationDrawerBinding binding;
    private UserViewModel mUserViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public BottomNavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_navigation_drawer, container, false);
        View view = binding.getRoot();

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(view.getContext());
        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);

        binding.fragmentBottomNavigationDrawerNavView.setNavigationItemSelectedListener( v-> {
            switch (v.getItemId()) {
                case R.id.bottom_theme: {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                    SharedPreferences.Editor editor = sharedPref.edit();

                    if (sharedPref.getString("theme", "light").equals("light")) {
                        editor.putString("theme","dark");
                    } else {
                        editor.putString("theme","light");
                    }
                    editor.apply();

                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                    this.dismiss();
                    return true;
                }
                case R.id.bottom_menu_info : {
                    Intent intent = new Intent(getContext(), InfoActivity.class);
                    startActivity(intent);
                    this.dismiss();
                    return true;
                }
                case R.id.bottom_menu_export_data : {
                    runExportData();
                    return true;
                }
                default: return true;
            }

        });

        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void runExportData() {
        mDisposable.add(mUserViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( userEntity -> {

                    if (userEntity.getReadings() == null) {
                        Toast.makeText(this.getContext(), getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("%id;%timestamp;%date;%hour;%fasting;%eating;%value;%notes\n");

                    for(DiabeteReadingEntity readingEntity : userEntity.getReadings()) {
                        stringBuilder.append(String.format("%s;%s;%s;%s;%s;%s;%s;%s\n", readingEntity.getId() + "", readingEntity.getTimestamp() + "", readingEntity.getReadingDate(), readingEntity.getReadingHour(), readingEntity.isFasting() + "", readingEntity.isEaten2h() + "", readingEntity.getValue() + "", readingEntity.getNotes()) );
                    }

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);

            }, throwable -> {
                Toast.makeText(this.getContext(), getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
            }));

    }


}
