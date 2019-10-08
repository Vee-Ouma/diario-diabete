package it.chiarani.diario_diabete.db.persistence.converters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;

public class TagsConverter {

    /**
     * Convert from json to DiabeteReadingEntity class
     */
    @TypeConverter
    public static List<TagsEntity> fromString(String value) {
        Type listType = new TypeToken<List<TagsEntity>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    /**
     * Convert DiabeteReadingEntity class to json
     */
    @TypeConverter
    public static String fromTagsEntity(List<TagsEntity> tags) {
        Gson gson = new Gson();
        return gson.toJson(tags);
    }
}
