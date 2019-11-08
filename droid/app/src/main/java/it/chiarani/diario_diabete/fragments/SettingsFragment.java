package it.chiarani.diario_diabete.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import it.chiarani.diario_diabete.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String currentTheme = sharedPref.getString("theme", "light");
        if (currentTheme.equals("light")) {
            getActivity().setTheme(R.style.PreferenceScreenLight);
        } else {
            getActivity().setTheme(R.style.PreferenceScreenDark);
        }
    }

}
