package com.pacific.data;

public class User {
    public final String firstName;
    public final String secondName;
    public final String birthDate;
    public final String birthPlace;
    public final int level;

    public User(String firstName,
                String secondName,
                String birthDate,
                String birthPlace,
                int level) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.level = level;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", level=" + level +
                '}';
    }
}
