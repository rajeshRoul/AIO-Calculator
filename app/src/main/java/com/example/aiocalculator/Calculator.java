package com.example.aiocalculator;

public class Calculator {
    private final int icon;
    private final String title;

    public Calculator(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }
    public String getTitle() {
        return title;
    }

}
