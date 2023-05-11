package com.example.myapplication;

import java.util.List;

public class User {
    int age, weight, gender, height, checked_water;
    double A, b, g, u, kal;
    String date, language;
    List<String> history;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getU() {
        return u;
    }

    public void setU(double u) {
        this.u = u;
    }

    public User() {

    }

    public double getKal() {
        return kal;
    }

    public void setKal(double kal) {
        this.kal = kal;
    }

    public User(int age, int weight, int gender, int height, int a, int checked_water, double b, double g, double u, int kal, String date, List<String> history, String language) {
        this.age = age;
        this.language=language;
        this.weight = weight;
        this.gender = gender;
        this.height = height;
        this.A = a;
        this.checked_water = checked_water;
        this.b=b;
        this.g=g;
        this.u=u;
        this.kal=kal;
        this.date=date;
        this.history=history;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getA() {
        return A;
    }

    public void setA(double a) {
        this.A = a;
    }

    public int getChecked_water() {
        return checked_water;
    }


    public void setChecked_water(int checked_water) {
        this.checked_water = checked_water;
    }
}

