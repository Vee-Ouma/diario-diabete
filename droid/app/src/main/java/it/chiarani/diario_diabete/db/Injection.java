package it.chiarani.diario_diabete.db;

import android.content.Context;

import it.chiarani.diario_diabete.db.UserDataSource;
import it.chiarani.diario_diabete.db.persistence.AppDatabase;
import it.chiarani.diario_diabete.db.persistence.LocalUserDataSource;
import it.chiarani.diario_diabete.viewmodels.ViewModelFactory;

public class Injection {
    /**
     * create istance of database
     * @param context context
     * @return user data source
     */
    public static UserDataSource provideUserDataSource(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());
    }

    /**
     * create viewmodelfactory
     * @param context context
     * @return new istance of viewmodelfactory*/
    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserDataSource userDataSource = provideUserDataSource(context);
        return new ViewModelFactory(userDataSource);
    }
}
