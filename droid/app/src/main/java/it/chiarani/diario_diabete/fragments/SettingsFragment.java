package it.chiarani.diario_diabete.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.db.Injection;
import it.chiarani.diario_diabete.viewmodels.UserViewModel;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;
import it.chiarani.diario_diabete.views.MainActivity;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {

    private UserViewModel mUserViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    public Preference pref_key_theme;
    public Preference pref_key_bugReport;
    public Preference pref_key_contactsWebsite;
    public Preference pref_key_donations;




    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);

        setPreferencesFromResource(R.xml.fragment_settings, rootKey);

        pref_key_theme = findPreference("pref_key_darkTheme");
        pref_key_bugReport = findPreference("pref_key_bugReport");
        pref_key_contactsWebsite = findPreference("pref_key_contactsWebsite");
        pref_key_donations = findPreference("pref_key_donations");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String currentTheme = sharedPref.getString("theme", "light");
        if (currentTheme.equals("light")) {
            getActivity().setTheme(R.style.PreferenceScreenLight);
            pref_key_theme.setDefaultValue(false);
        } else {
            getActivity().setTheme(R.style.PreferenceScreenDark);
            pref_key_theme.setDefaultValue(true);
        }


        pref_key_theme.setOnPreferenceClickListener(this);
        pref_key_bugReport.setOnPreferenceClickListener(this);
        pref_key_contactsWebsite.setOnPreferenceClickListener(this);
        pref_key_donations.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "pref_key_darkTheme": {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                if (sharedPref.getString("theme", "light").equals("light")) {
                    editor.putString("theme","dark");
                } else {
                    editor.putString("theme","light");
                }
                editor.apply();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                //this.dismiss();
                return true;
            }
            case "pref_key_bugReport": {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:fabio@chiarani.it"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[DIARIO-DIABETE-APP]");
                startActivity(emailIntent);
                return true;
            }
            case "pref_key_contactsWebsite": {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chiarani.it/"));
                startActivity(browserIntent);
                return true;
            }
            case "pref_key_donations": {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.me/fabiochiarani"));
                startActivity(browserIntent);
                return true;
            }
            default:
                return false;
        }
    }
}
