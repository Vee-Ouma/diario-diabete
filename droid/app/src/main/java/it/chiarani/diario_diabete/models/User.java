package it.chiarani.diario_diabete.models;

import java.util.List;

import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;

public interface User {

    int getId();

    void setId(int id);

    List<DiabeteReadingEntity> getReadings();

    void setReadings(List<DiabeteReadingEntity> readings);

    String getName();

    void setName(String name);

    String getSurname();

    void setSurname(String surname);

    int getHeight();

    void setHeight(int height);

    int getWeight();

    void setWeight(int weight);

    boolean isSex();

    void setSex(boolean sex);

    int getAge();

    void setAge(int age);
}
