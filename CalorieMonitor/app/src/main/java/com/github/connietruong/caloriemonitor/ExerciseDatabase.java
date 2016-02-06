package com.github.connietruong.caloriemonitor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Connie on 1/26/2016.
 */
public class ExerciseDatabase {
    private static HashMap<String, Double> METValues = new HashMap<String, Double>();
    private static HashMap<String, ArrayList<Integer>> repUnitsM = new HashMap<String, ArrayList<Integer>>();
    private static HashMap<String, ArrayList<Integer>> repUnitsF = new HashMap<String, ArrayList<Integer>>();

    public static ArrayList<Exercise> initializeExercise() {
        ArrayList<Exercise> exerciseObjects = new ArrayList<>();
        exerciseObjects.add(new Exercise("Pushup", 0, true));
        exerciseObjects.add(new Exercise("Situp", 0, true));
        exerciseObjects.add(new Exercise("Squats", 0, true));
        exerciseObjects.add(new Exercise("Leg-lift", 0, false));
        exerciseObjects.add(new Exercise("Plank", 0, false));
        exerciseObjects.add(new Exercise("Jumping Jacks", 0, false));
        exerciseObjects.add(new Exercise("Pullup", 0, true));
        exerciseObjects.add(new Exercise("Cycling", 0, false));
        exerciseObjects.add(new Exercise("Walking", 0, false));
        exerciseObjects.add(new Exercise("Jogging", 0, false));
        exerciseObjects.add(new Exercise("Swimming", 0, false));
        exerciseObjects.add(new Exercise("Stair-climbing", 0, false));
        return exerciseObjects;
    }

    public static void initializeMETValues() {
        METValues.put("Pushup", 5.512);
        METValues.put("Situp", 15.113);
        METValues.put("Squats", 10.44);
        METValues.put("Pullup", 6.711);
        METValues.put("Leg-lift", 3.355);
        METValues.put("Plank", 3.355);
        METValues.put("Jumping Jacks", 8.391);
        METValues.put("Cycling", 6.99);
        METValues.put("Walking", 4.194);
        METValues.put("Jogging", 6.99);
        METValues.put("Swimming", 6.454);
        METValues.put("Stair-climbing", 5.592);
    }

    public static double getMETValue(String name) {
        return METValues.get(name);
    }

    /** Initialize the average reps per minute. Currently this is for the average 20yo male. Might need to find calculations*/
    public static void initializeRepUnitsMale() {
        ArrayList<Integer> pushUpAge = new ArrayList<Integer>();
        ArrayList<Integer> sitUpAge = new ArrayList<Integer>();
        ArrayList<Integer> squatAge = new ArrayList<Integer>();
        ArrayList<Integer> pullUpAge = new ArrayList<Integer>();

        pushUpAge.add(23);
        pushUpAge.add(19);
        pushUpAge.add(16);
        pushUpAge.add(13);
        pushUpAge.add(11);
        repUnitsM.put("Pushup", pushUpAge);

        sitUpAge.add(36);
        sitUpAge.add(28);
        sitUpAge.add(23);
        sitUpAge.add(19);
        sitUpAge.add(17);
        repUnitsM.put("Situp", sitUpAge);

        squatAge.add(28);
        squatAge.add(25);
        squatAge.add(22);
        squatAge.add(19);
        squatAge.add(16);
        repUnitsM.put("Squats", squatAge);

        pullUpAge.add(8);
        pullUpAge.add(6);
        pullUpAge.add(4);
        pullUpAge.add(2);
        pullUpAge.add(1);
        repUnitsM.put("Pullup", pullUpAge);
    }

    public static void initializeRepUnitsFemale() {
        ArrayList<Integer> pushUpAge = new ArrayList<Integer>();
        ArrayList<Integer> sitUpAge = new ArrayList<Integer>();
        ArrayList<Integer> squatAge = new ArrayList<Integer>();
        ArrayList<Integer> pullUpAge = new ArrayList<Integer>();

        pushUpAge.add(17);
        pushUpAge.add(15);
        pushUpAge.add(12);
        pushUpAge.add(10);
        pushUpAge.add(8);
        repUnitsF.put("Pushup", pushUpAge);

        sitUpAge.add(29);
        sitUpAge.add(25);
        sitUpAge.add(19);
        sitUpAge.add(15);
        sitUpAge.add(12);
        repUnitsF.put("Situp", sitUpAge);

        squatAge.clear();
        squatAge.add(22);
        squatAge.add(19);
        squatAge.add(16);
        squatAge.add(13);
        squatAge.add(10);
        repUnitsF.put("Squats", squatAge);

        pullUpAge.clear();
        pullUpAge.add(5);
        pullUpAge.add(4);
        pullUpAge.add(3);
        pullUpAge.add(2);
        pullUpAge.add(1);
        repUnitsF.put("Pullup", pullUpAge);
    }

    public static boolean isRep(String name) {
        return repUnitsM.containsKey(name);
    }

    public static int getRepUnitM(String name, int age) {
        ArrayList<Integer> exercise = repUnitsM.get(name);
        if (age < 30) {
            return exercise.get(0);
        }
        else if (age < 40) {
            return exercise.get(1);
        }
        else if (age < 50) {
            return exercise.get(2);
        }
        else if (age < 60) {
            return exercise.get(3);
        } else {
            return exercise.get(4);
        }
    }

    public static int getRepUnitF(String name, int age) {
        ArrayList<Integer> exercise = repUnitsF.get(name);
        if (age < 30) {
            return exercise.get(0);
        }
        else if (age < 40) {
            return exercise.get(1);
        }
        else if (age < 50) {
            return exercise.get(2);
        }
        else if (age < 60) {
            return exercise.get(3);
        } else {
            return exercise.get(4);
        }
    }
}
