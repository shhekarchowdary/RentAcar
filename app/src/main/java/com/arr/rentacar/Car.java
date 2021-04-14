package com.arr.rentacar;

import java.nio.file.DirectoryStream;

public class Car {
    private String make;
    private String model;
    private double dailyRate;
    private double currentMillage;

    public Car(String make, String model, double dailyRate, double currentMillage) {
        this.make = make;
        this.model = model;
        this.dailyRate = dailyRate;
        this.currentMillage = currentMillage;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public double getCurrentMillage() {
        return currentMillage;
    }

    public void setCurrentMillage(double currentMillage) {
        this.currentMillage = currentMillage;
    }
}
