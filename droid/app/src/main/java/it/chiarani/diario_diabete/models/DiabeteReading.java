package it.chiarani.diario_diabete.models;

import java.util.List;

import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;

public interface DiabeteReading {

    /**
     * Return the id of the reading
     * @return the id of the reading
     */
    int getId();

    /**
     * Set the id of the reading
     * @param id the id of the reading
     */
    void setId(int id);

    /**
     * Return the timestamp of when the reading is created
     * @return the timestamp of when the reading is created
     */
    long getTimestamp();

    /**
     * Set the timestamp of when the reading is created
     * @param timestamp the timestamp of when the reading is created
     */
    void setTimestamp(long timestamp);

    /**
     * Return the time when the user read the value
     * @return the time when the user read the value
     */
    String getReadingDate();

    /**
     * Set the time on when the user read the value
     * @param readingDate the time on when the user read the value
     */
    void setReadingDate(String readingDate);

    /**
     * Return the timestamp of when the reading is created
     * @return the timestamp of when the reading is created
     */
    String getReadingHour();

    /**
     * Set the timestamp of when the reading is created
     * @param timestamp the timestamp of when the reading is created
     */
    void setReadingHour(String timestamp);

    /**
     * Return true if during the reading value the user is fasting. False otherwise.
     * @return true if during the reading value the user is fasting. False otherwise.
     */
    boolean isFasting();

    /**
     *  Set true if during the reading value the user is fasting. False otherwise.
     * @param fasting true if during the reading value the user is fasting. False otherwise.
     */
    void setFasting(boolean fasting);

    /**
     * Return the value of diabete reading
     * @return the value of diabete reading
     */
    float getValue();

    /**
     * Set the value of diabete reading
     * @param value the value of diabete reading
     */
    void setValue(float value);

    /**
     * Return extra notes to the reading
     * @return extra notes to the reading
     */
    String getNotes();
    /**
     * Set extra notes to the reading
     * @param notes extra notes to the reading
     */
    void setNotes(String notes);

    public List<TagsEntity> getTags();

    public void setTags(List<TagsEntity> tags);

    public boolean isEaten2h();

    public void setEaten2h(boolean eaten2h);
}
