package com.github.connietruong.caloriemonitor;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Connie on 1/26/2016.
 */
public class ExerciseTest {

    @Test
    public void testCaloriesBurned() {
        ExerciseDatabase.initializeRepUnitsMale();
        ExerciseDatabase.initializeMETValues();
        User john = new User("John", "Male", 20, 67, 150, 0);
        Exercise pushup = new Exercise("Pushup", 350, true);
        Exercise plank = new Exercise("Plank", 25, false);
        Assert.assertEquals(100, pushup.caloriesBurned(john));
        Assert.assertEquals(100, plank.caloriesBurned(john));
    }

    @Test
    public void testConverter() {
        ExerciseDatabase.initializeMETValues();
        ExerciseDatabase.initializeRepUnitsMale();
        User john = new User("John", "Male", 20, 67, 150, 0);
        Exercise situp = new Exercise("Situp", 200, true);
        Exercise squats = new Exercise("Squats", 225, true);
        Exercise jumping_jacks = new Exercise("Jumping Jacks", 10, false);
        Exercise cycling = new Exercise("Cycling", 12, false);

        ArrayList<Exercise> listExer = new ArrayList<Exercise>();
        listExer.add(situp);
        listExer.add(squats);
        listExer.add(jumping_jacks);
        listExer.add(cycling);

        Assert.assertEquals(400, Exercise.totalCalories(john, listExer));
    }

    @Test
    public void testEquivalent() {
        ExerciseDatabase.initializeMETValues();
        ExerciseDatabase.initializeRepUnitsMale();
        User john = new User("John", "Male", 20, 67, 150, 0);
        Exercise pullup = new Exercise("Pullup", 100, true);
        Exercise swimming = new Exercise("Swimming", 0, false);

        Assert.assertEquals(13, Exercise.convertValues(john, pullup, swimming));

        Exercise situp = new Exercise("Situp", 200, true);
        Exercise pushup= new Exercise("Pushup", 0, true);

        Assert.assertEquals(350, Exercise.convertValues(john, situp, pushup));
        Assert.assertEquals(200, Exercise.convertValues(john, situp, situp));
    }

    @Test
    public void testExerciseFind() {
        ExerciseDatabase.initializeMETValues();
        ExerciseDatabase.initializeRepUnitsMale();
        User john = new User("John", "Male", 20, 67, 150, 0);
        Exercise walking = new Exercise("Walking", 0, false);
        Exercise squats = new Exercise("Squats", 0, true);

        Assert.assertEquals(20, walking.exerciseNeeded(john, 100));
        Assert.assertEquals(225, squats.exerciseNeeded(john, 100));
    }

    @Test
    public void testChangeValues() {
        ExerciseDatabase.initializeMETValues();
        ExerciseDatabase.initializeRepUnitsMale();
        ExerciseDatabase.initializeRepUnitsFemale();
        User joan = new User("Joan", "Female", 20, 67, 150, 0);
        Exercise walking = new Exercise("Walking", 20, false);

        Assert.assertNotEquals(100, walking.caloriesBurned(joan));
        joan.changeSex("Male");
        Assert.assertEquals(100, walking.caloriesBurned(joan));
    }
}
