package it.chiarani.diario_diabete.db.persistence.entities;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import it.chiarani.diario_diabete.models.DiabeteReading;

@Entity(tableName = "diabeteReadingEntity")
public class DiabeteReadingEntity implements DiabeteReading {

    /**
     * Private fields
     */
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long timestamp;
    private long readingDate;
    private long readingHour;
    private boolean isFasting;
    private float value;
    private String notes;
    private List<TagsEntity> tags;

    /**
     * Empty constructor
     */
    @Ignore
    public DiabeteReadingEntity() {

    }

    public DiabeteReadingEntity(int id, long timestamp, long readingDate, long readingHour, boolean isFasting, float value, String notes, List<TagsEntity> tags) {
        this.id = id;
        this.timestamp = timestamp;
        this.readingDate = readingDate;
        this.readingHour = readingHour;
        this.isFasting = isFasting;
        this.value = value;
        this.notes = notes;
        this.tags = tags;
    }

    public List<TagsEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagsEntity> tags) {
        this.tags = tags;
    }

    @Override
    public long getReadingHour() {
        return readingHour;
    }

    @Override
    public void setReadingHour(long readingHour) {
        this.readingHour = readingHour;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public long getReadingDate() {
        return readingDate;
    }

    @Override
    public void setReadingDate(long readingDate) {
        this.readingDate = readingDate;
    }

    @Override
    public boolean isFasting() {
        return isFasting;
    }

    @Override
    public void setFasting(boolean fasting) {
        isFasting = fasting;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
