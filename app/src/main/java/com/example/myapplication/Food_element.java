package com.example.myapplication;

public class Food_element {
    String name;
    int kal;
    double b,g,u;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKal() {
        return kal;
    }

    public void setKal(int kal) {
        this.kal = kal;
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

    public Food_element(String name, int kal, double b, double g, double u) {
        this.name = name;
        this.kal = kal;
        this.b = b;
        this.g = g;
        this.u = u;
    }
    public Food_element(){
    }
}
