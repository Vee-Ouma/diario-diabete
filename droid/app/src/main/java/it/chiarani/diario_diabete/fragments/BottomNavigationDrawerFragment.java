package it.chiarani.diario_diabete.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.databinding.FragmentBottomNavigationDrawerBinding;

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
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
