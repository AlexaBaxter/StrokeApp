package com.example.strokeapp;

public class Doctor {

    private final String name;
    private final String email;
    private String type;

    public Doctor(String name, String email, String type) {
        this.name = capitalize(name);
        this.email = email;
        this.type = type;
        if(!type.equals("")) this.type = capitalize(type);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    private String capitalize(String str) {
        String [] arr = str.split(" ");
        String newStr = "";
        for(String s : arr) {
            char first = s.charAt(0);
            if('a' <= first && first <='z' || 'A' <= first && first <= 'Z') {
                s = ("" + first).toUpperCase() + s.substring(1);
            }
            newStr += s + " ";
        }
        return newStr.trim();
    }
}
