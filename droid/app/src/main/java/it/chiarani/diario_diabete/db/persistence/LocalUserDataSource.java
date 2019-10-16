package it.chiarani.diario_diabete.db.persistence;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import it.chiarani.diario_diabete.db.UserDataSource;
import it.chiarani.diario_diabete.db.persistence.DAO.UserDao;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;

/**
 * Use room database as data source
 */
public class LocalUserDataSource implements UserDataSource {

    private final UserDao mUserDao;

    public LocalUserDataSource(UserDao userDao) {
        mUserDao = userDao;
    }

    @Override
    public Single<UserEntity> getUser() {
        return mUserDao.getUser();
    }

    @Override
    public Completable insertOrUpdateUser(UserEntity user) {
        return mUserDao.insert(user);
    }

    @Override
    public void deleteAllUsers() {
        mUserDao.deleteAll();
    }
}
