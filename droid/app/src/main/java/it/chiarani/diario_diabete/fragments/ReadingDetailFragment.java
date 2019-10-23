package it.chiarani.diario_diabete.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Collections;

import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.adapters.ReadingsAdapter;
import it.chiarani.diario_diabete.adapters.TagsAdapter;
import it.chiarani.diario_diabete.databinding.FragmentReadingDetailBinding;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;


public class ReadingDetailFragment extends BottomSheetDialogFragment {

    FragmentReadingDetailBinding binding;
    DiabeteReadingEntity mReading;

    public ReadingDetailFragment(DiabeteReadingEntity reading) {
        this.mReading = reading;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reading_detail, container, false);
        View view = binding.getRoot();


        binding.fragmentReadingDetailValue.setText(String.format("%s", mReading.getValue()));
        binding.fragmentReadingDetailHour.setText(String.format("%s", mReading.getReadingHour()));
        binding.fragmentReadingDetailTime.setText(String.format("%s", mReading.getReadingDate()));
        binding.fragmentReadingDetailDescription.setText("Nella norma");

        binding.fragmentReadingDetailFasting.setText("Digiuno");
        if(mReading.isEaten2h()) {
            binding.fragmentReadingDetailFasting.setText("Mangiato da 2h");
        }

        if(mReading.getValue() > 100 && mReading.getValue() < 125) {
            binding.fragmentReadingDetailDescription.setText("Pre-Diabete");

        }
        else if (mReading.getValue() >= 125) {
            binding.fragmentReadingDetailDescription.setText("Diabete");
        }


        GridLayoutManager gridLayout = new GridLayoutManager(view.getContext(), 3);

        binding.fragmentReadingDetailRvTags.setLayoutManager(gridLayout);

        TagsAdapter adapterTags = new TagsAdapter(mReading.getTags(), null);
        binding.fragmentReadingDetailRvTags.setAdapter(adapterTags);


        binding.fragmentReadingDetailBtnDelete.setOnClickListener(v-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Eliminare la lettura?");
            // Add the buttons
            builder.setPositiveButton("Si", (dialog, id) -> {
                // User clicked OK button
            });
            builder.setNegativeButton("No", (dialog, id) -> {
                // User cancelled the dialog
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return  view;
    }

}
