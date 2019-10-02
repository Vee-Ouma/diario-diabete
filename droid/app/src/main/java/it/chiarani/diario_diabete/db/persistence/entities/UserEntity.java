package it.chiarani.diario_diabete.db.persistence.entities;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import it.chiarani.diario_diabete.models.User;

@Entity(tableName = "userEntity")
public class UserEntity implements User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private List<DiabeteReadingEntity> readings;
    private String name;
    private String surname;
    private int height;
    private int weight;
    private boolean sex;
    private int age;

    public UserEntity(List<DiabeteReadingEntity> readings, String name, String surname, int height, int weight, boolean sex, int age) {
        this.readings = readings;
        this.name = name;
        this.surname = surname;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.age = age;
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
    public List<DiabeteReadingEntity> getReadings() {
        return readings;
    }

    @Override
    public void setReadings(List<DiabeteReadingEntity> readings) {
        this.readings = readings;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean isSex() {
        return sex;
    }

    @Override
    public void setSex(boolean sex) {
        this.sex = sex;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }
}
