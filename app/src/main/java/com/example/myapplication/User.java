package com.example.myapplication;

import java.util.List;

public class User {
    int age, weight, gender, height, checked_water;
    double A, b, g, u, kal;
    String date;
    List<String> history;

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

    public User(int age, int weight, int gender, int height, int a, int checked_water, double b, double g, double u, int kal, String date, List<String> history) {
        this.age = age;
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



/*

+----------------------------------+
|             <<class>>            |
|               User               |
+----------------------------------+
| - age: int                       |
| - weight: int                    |
| - gender: int                    |
| - height: int                    |
| - A: double                      |
| - b: double                      |
| - g: double                      |
| - u: double                      |
| - kal: double                    |
| - date: String                   |
| - checked_water: int             |
| - history: List<String>          |


+--------------------------------------------------+
|             <<class>>                            |
|               User                               |
+--------------------------------------------------+
| + getHistory(): List<String>                     |
| + setHistory(history: List<String>): void        |
| + getDate(): String                              |
| + setDate(date: String): void                    |
| + getB(): double                                 |
| + setB(b: double): void                          |
| + getG(): double                                 |
| + setG(g: double): void                          |
| + getU(): double                                 |
| + setU(u: double): void                          |
| + getKal(): double                               |
| + setKal(kal: double): void                      |
| + getAge(): int                                  |
| + setAge(age: int): void                         |
| + getWeight(): int                               |
| + setWeight(weight: int): void                   |
| + getGender(): int                               |
| + setGender(gender: int): void                   |
| + getHeight(): int                               |
| + setHeight(height: int): void                   |
| + getA(): double                                 |
| + setA(a: double): void                          |
| + getChecked_water(): int                        |
| + setChecked_water(checked_water: int): void     |
+--------------------------------------------------+

 */