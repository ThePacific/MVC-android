package com.pacific.example.bean;

public class Bean {

    private int iconResId;
    private String name;
    private String description;

    public Bean(int iconResId, String name, String description) {
        this.iconResId = iconResId;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResId() {
        return iconResId;
    }
}
