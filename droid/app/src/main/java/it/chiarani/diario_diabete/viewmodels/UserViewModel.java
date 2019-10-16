package it.chiarani.diario_diabete.viewmodels;


import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import it.chiarani.diario_diabete.db.UserDataSource;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;

/**
 * ViewModel for user
 */
public class UserViewModel extends ViewModel {
    private final UserDataSource mUserDataSource;

    public UserViewModel(UserDataSource userDataSource) {
        mUserDataSource = userDataSource;
    }

    public Single<UserEntity> getUser() {
        return mUserDataSource.getUser();
    }

    public Completable insertUser(UserEntity user) {
        return mUserDataSource.insertOrUpdateUser(user);
    }
}