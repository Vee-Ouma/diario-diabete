package it.chiarani.diario_diabete.models;

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
    long getCreatedAt();

    /**
     * Set the timestamp of when the reading is created
     * @param createdAt the timestamp of when the reading is created
     */
    void setCreatedAt(long createdAt);

    /**
     * Return the time when the user read the value
     * @return the time when the user read the value
     */
    long getDatetime();

    /**
     * Set the time on when the user read the value
     * @param datetime the time on when the user read the value
     */
    void setDatetime(long datetime);

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
}
