package com.github.connietruong.caloriemonitor;

import java.util.HashMap;

/**
 * Created by Connie on 1/24/2016.
 */
public class User {
    private int height; //in inches
    private int weight; //in pounds
    private int heightCM; //in centimeters
    private double weightKG; //in kilograms
    private int id;
    private String name;
    private String sex;
    private int age;
    private double bmr;

    public User(String name, String sex, int age, int height, int weight, int id) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.height = height;
        heightCM = (int)(height * 2.54); //changing in to cm
        this.weight = weight;
        weightKG = weight * 0.453; //changing lbs to kgs
        this.id = id;
        setBMR();
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeightFt() {
        return height / 12;
    }

    public int getHeightIn() {
        return height % 12;
    }

    public double getBMR() {
        return bmr;
    }

    public void setBMR() {
        if (sex == "Male") {
            bmr = (13.75 * weightKG) + (5 * heightCM) - (6.76 * age) + 66;
        } else {
            bmr = (9.56 * weightKG) + (1.85 * heightCM) - (4.68 * age) + 655;
        }
    }

    public void changeId(int id) { this.id = id;}

    public void changeName(String name) {
        this.name = name;
    }

    public void changeSex(String sex) {
        this.sex = sex;
        setBMR();
    }

    public void changeAge(int age) {
        this.age = age;
        setBMR();
    }

    public void changeHeight(int height) {
        this.height = height;
        heightCM = (int)(height * 2.54);
        setBMR();
    }

    public void changeWeight(int weight) {
        this.weight = weight;
        weightKG = weight * 0.453;
        setBMR();
    }

    public HashMap<String, Integer> initializeExerciseValues(User user) {
        HashMap<String, Integer> exerciseValues = new HashMap<String, Integer>();
        exerciseValues.put("Pushup", 350);
        exerciseValues.put("Situp", 200);
        exerciseValues.put("Squats", 225);
        exerciseValues.put("Leg-lift", 25);
        exerciseValues.put("Plank", 25);
        exerciseValues.put("Jumping jacks", 10);
        exerciseValues.put("Pullup", 100);
        exerciseValues.put("Cycling", 12);
        exerciseValues.put("Walking", 20);
        exerciseValues.put("Jogging", 12);
        exerciseValues.put("Swimming", 13);
        exerciseValues.put("Stair-Climbing", 15);
        return exerciseValues;
    }
}
