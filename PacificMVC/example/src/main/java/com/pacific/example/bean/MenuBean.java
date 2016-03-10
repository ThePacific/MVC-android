package com.pacific.example.bean;

public class MenuBean {
    private int iconResId;
    private String description;

    public MenuBean(int iconResId, String description) {
        this.iconResId = iconResId;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResId() {
        return iconResId;
    }
}
