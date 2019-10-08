package it.chiarani.diario_diabete.db.persistence.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import it.chiarani.diario_diabete.models.Tags;

@Entity(tableName = "tagsEntity")
public class TagsEntity implements Tags {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String value;
    private String colorHEX;

    public TagsEntity(int id, String value, String colorHEX) {
        this.id = id;
        this.value = value;
        this.colorHEX = colorHEX;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColorHEX() {
        return colorHEX;
    }

    public void setColorHEX(String colorHEX) {
        this.colorHEX = colorHEX;
    }
}