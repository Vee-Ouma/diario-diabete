package it.chiarani.diario_diabete.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.databinding.FragmentBottomNavigationDrawerBinding;
import it.chiarani.diario_diabete.views.MainActivity;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    FragmentBottomNavigationDrawerBinding binding;

    public BottomNavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_navigation_drawer, container, false);
        View view = binding.getRoot();

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


}
