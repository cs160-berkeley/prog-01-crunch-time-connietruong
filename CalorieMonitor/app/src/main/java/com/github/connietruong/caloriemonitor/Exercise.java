package com.github.connietruong.caloriemonitor;

import java.util.ArrayList;

/**
 * Created by Connie on 1/24/2016.
 */

public class Exercise {
    private String name;
    private Integer inputValue;
    private boolean isRep = false;

    public Exercise(String name, Integer inputValue, Boolean isRep) {
        this.name = name;
        this.inputValue = inputValue;
        if (isRep) {
            this.isRep = true;
        }
    }

    public String getName() { return name; }

    /** Returns if the exercise units is in reps or not.*/
    public boolean isRep() {
        return isRep;
    }

    /** Changes the value to an exercise if the input changes the number of reps/exercise.*/
    public void changeInputValue(Integer newInput) {
        inputValue = newInput;
    }

    /** Tells how many reps/minutes are needed to burn the set number of calories. */
    private double timeToBurn(User user, int calories) {
        return calories * 24 / user.getBMR() / ExerciseDatabase.getMETValue(name);
    }

    private int convertUnits(User user, double time) {
        if (isRep()) {
            if (user.getSex().equals("Male")) {
                return (int) Math.round(time * 60 * ExerciseDatabase.getRepUnitM(name, user.getAge()));
            }
            return (int) Math.round(time * 60 * ExerciseDatabase.getRepUnitF(name, user.getAge()));
        }
        return (int) Math.round(time * 60);
    }

    public int exerciseNeeded(User user, int calories) {
        return convertUnits(user, timeToBurn(user, calories));
    }

    /** Converts the inputValue and correctly converts the same amount of calorie burn for another
     * exercise.*/
    public static int convertValues(User user, Exercise from, Exercise to) {
        int fromCal = from.caloriesBurned(user);
        return to.convertUnits(user, to.timeToBurn(user, fromCal));
    }

    /** Calculates the amount of calories burned.*/
    public int caloriesBurned(User user) {
        double time;
        if (isRep()) {
            if (user.getSex().equals("Male")) {
                time = (double) inputValue / ExerciseDatabase.getRepUnitM(name, user.getAge()) / 60.0;
            } else {
                time = (double) inputValue / ExerciseDatabase.getRepUnitF(name, user.getAge()) / 60.0;
            }
        } else {
            time = (double) inputValue / 60.0;
        }
        return (int)Math.round(user.getBMR() / 24.0 * ExerciseDatabase.getMETValue(name) * time);
    }

    /** Calculates total calories of all exercises.*/
    public static int totalCalories(User user, ArrayList<Exercise> exercises) {
        int totalCal = 0;

        for (Exercise exercise : exercises) {
            totalCal += exercise.caloriesBurned(user);
        }
        return totalCal;
    }
}


