package it.chiarani.diario_diabete.db.persistence.converters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;

/**
 * convert all class to json
 * Guide: https://android.jlelse.eu/room-persistence-library-typeconverters-and-database-migration-3a7d68837d6c
 */
public class DiabeteReadingEntityConverter {

    /**
     * Convert from json to DiabeteReadingEntity class
     */
    @TypeConverter
    public static List<DiabeteReadingEntity> fromString(String value) {
        Type listType = new TypeToken<List<DiabeteReadingEntity>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    /**
     * Convert DiabeteReadingEntity class to json
     */
    @TypeConverter
    public static String fromDiabeteReadingEntity(List<DiabeteReadingEntity> weatherForWeek) {
        Gson gson = new Gson();
        return gson.toJson(weatherForWeek);
    }
}
