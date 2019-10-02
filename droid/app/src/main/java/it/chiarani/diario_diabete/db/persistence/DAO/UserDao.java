package it.chiarani.diario_diabete.db.persistence.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;

@Dao
public interface UserDao {
    @Query("SELECT * FROM userEntity")
    Flowable<UserEntity> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(UserEntity entity);

    @Query("DELETE FROM userEntity")
    void deleteAll();
}
