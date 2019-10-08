package it.chiarani.diario_diabete.models;

public class HourlyList {
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public HourlyList(int value, String display) {
        this.value = value;
        this.display = display;
    }

    private int value;
    private String display;
}
