package it.chiarani.diario_diabete.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.views.MainActivity;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {

    public Preference pref_key_theme;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey);

        pref_key_theme = findPreference("darkTheme");

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


    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "darkTheme": {
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
            default:
                return false;
        }
    }
}
