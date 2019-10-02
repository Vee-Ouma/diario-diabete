package it.chiarani.diario_diabete.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.annotations.NonNull;
import it.chiarani.diario_diabete.db.UserDataSource;

/**
 * Factory for viewmodels
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final UserDataSource mUserDataSource;

    public  ViewModelFactory(UserDataSource userDataSource) {
        mUserDataSource = userDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(mUserDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}