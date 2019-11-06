package it.chiarani.diario_diabete.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.adapters.FragmentCallbackListener;
import it.chiarani.diario_diabete.adapters.ReadingsAdapter;
import it.chiarani.diario_diabete.adapters.TagsAdapter;
import it.chiarani.diario_diabete.databinding.FragmentReadingDetailBinding;
import it.chiarani.diario_diabete.db.Injection;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;
import it.chiarani.diario_diabete.viewmodels.UserViewModel;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;


public class ReadingDetailFragment extends BottomSheetDialogFragment {

    FragmentReadingDetailBinding binding;
    int pos;
    private UserViewModel mUserViewModel;
    private boolean blockObserver = true;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    FragmentCallbackListener mListener;

    public ReadingDetailFragment(int pos, FragmentCallbackListener listener) {
        this.pos = pos;
        this.mListener = listener;
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

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(view.getContext());
        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);

        mDisposable.add(mUserViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( userEntity -> {
                    Collections.reverse(userEntity.getReadings());
                    binding.fragmentReadingDetailValue.setText(String.format("%s", userEntity.getReadings().get(pos).getValue()));
                    binding.fragmentReadingDetailHour.setText(String.format("%s",  userEntity.getReadings().get(pos).getReadingHour()));
                    binding.fragmentReadingDetailTime.setText(String.format("%s",  userEntity.getReadings().get(pos).getReadingDate()));
                    binding.fragmentReadingDetailDescription.setText("Nella norma");

                    binding.fragmentReadingDetailFasting.setText("Digiuno");
                    if( userEntity.getReadings().get(pos).isEaten2h()) {
                        binding.fragmentReadingDetailFasting.setText("Mangiato da 2h");
                    }

                    if( userEntity.getReadings().get(pos).getValue() > 100 &&  userEntity.getReadings().get(pos).getValue() < 125) {
                        binding.fragmentReadingDetailDescription.setText("Pre-Diabete");

                    }
                    else if ( userEntity.getReadings().get(pos).getValue() >= 125) {
                        binding.fragmentReadingDetailDescription.setText("Diabete");
                    }


                    GridLayoutManager gridLayout = new GridLayoutManager(view.getContext(), 3);

                    binding.fragmentReadingDetailRvTags.setLayoutManager(gridLayout);

                    TagsAdapter adapterTags = new TagsAdapter( userEntity.getReadings().get(pos).getTags(), null);
                    binding.fragmentReadingDetailRvTags.setAdapter(adapterTags);

                }, throwable -> {
                    // Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
                }));

        binding.fragmentReadingDetailBtnDelete.setOnClickListener(v-> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Eliminare la lettura?");
            // Add the buttons
            builder.setPositiveButton("Si", (dialog, id) -> {

                mDisposable.add(mUserViewModel.getUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( userEntity -> {
                            if(blockObserver) {
                            Collections.reverse(userEntity.getReadings());

                            UserEntity tmp = userEntity;
                            userEntity.getReadings().remove(pos);
                            Collections.reverse(userEntity.getReadings());

                                // update user to ROOM
                                blockObserver = false;

                                mDisposable.add(mUserViewModel.insertUser(tmp)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe( () -> {
                                            mListener.onFragmentClosed();
                                            this.dismiss();
                                        }, throwable -> {
                                            // Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
                                        }));
                            }

                        }, throwable -> {
                            // Toast.makeText(this, getString(R.string.txtGenericError), Toast.LENGTH_LONG).show();
                        }));
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
