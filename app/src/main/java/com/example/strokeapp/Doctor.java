package com.example.strokeapp;

public class Doctor {

    private String name;
    private String email;
    private String type;

    public Doctor() {
        name = "";
        email = "";
        type = "";
    }

    public Doctor(String name, String email, String type) {
        this.name = capitalize(name);
        this.email = email;
        this.type = type;
        if(!type.equals("")) this.type = capitalize(type);
    }

    public void setName(String name) {
        this.name = capitalize(name);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
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
