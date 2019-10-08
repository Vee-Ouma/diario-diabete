package it.chiarani.diario_diabete.db.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import it.chiarani.diario_diabete.db.persistence.DAO.UserDao;
import it.chiarani.diario_diabete.db.persistence.converters.DiabeteReadingEntityConverter;
import it.chiarani.diario_diabete.db.persistence.converters.TagsConverter;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;
import it.chiarani.diario_diabete.db.persistence.entities.UserEntity;

@Database(entities = {UserEntity.class, DiabeteReadingEntity.class}, version = 1, exportSchema = false)
@TypeConverters({ DiabeteReadingEntityConverter.class, TagsConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "userData.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
