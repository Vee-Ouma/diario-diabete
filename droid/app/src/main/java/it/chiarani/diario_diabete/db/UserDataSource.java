package it.chiarani.diario_diabete.db;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;

public interface UserDataSource {
    /**
     * Get user from database.
     * @return user from database
     */
    Single<UserEntity> getUser();

    /**
     * Insert user to database, or, if already exists override it.
     * @param user to be insered or update to database
     */
    Completable insertOrUpdateUser(UserEntity user);

    /**
     * Remove all users from database
     */
    void deleteAllUsers();
}