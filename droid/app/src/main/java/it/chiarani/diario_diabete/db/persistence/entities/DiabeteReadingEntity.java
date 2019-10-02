package it.chiarani.diario_diabete.db.persistence.entities;

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
    private long createdAt;
    private long datetime;
    private boolean isFasting;
    private float value;
    private String notes;


    /**
     * Empty constructor
     */
    @Ignore
    public DiabeteReadingEntity() {

    }

    /**
     * Full cunstructor
     */
    public DiabeteReadingEntity(long createdAt, long datetime, boolean isFasting, float value, String notes) {
        this.createdAt = createdAt;
        this.datetime = datetime;
        this.isFasting = isFasting;
        this.value = value;
        this.notes = notes;
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
    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public long getDatetime() {
        return datetime;
    }

    @Override
    public void setDatetime(long datetime) {
        this.datetime = datetime;
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
