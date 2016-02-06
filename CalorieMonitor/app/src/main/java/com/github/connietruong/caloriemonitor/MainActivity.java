package com.github.connietruong.caloriemonitor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ExerciseDatabase.initializeRepUnitsFemale();
        ExerciseDatabase.initializeRepUnitsMale();
        ExerciseDatabase.initializeMETValues();

        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        /*Initializes the list of exercises that other activities can use if doesn't exit in the shared
        preferences
         */
        String exercisesJson = preferences.getString("Exercises", "");
        if (exercisesJson.isEmpty()) {
            //save arraylist of exercise objects as json
            ArrayList<Exercise> exerciseList = ExerciseDatabase.initializeExercise();
            SharedPreferences.Editor prefsEditor = preferences.edit();
            exercisesJson = gson.toJson(exerciseList);
            prefsEditor.putString("Exercises", exercisesJson);
            prefsEditor.commit();
        }

        if (preferences.getString("Users", "").isEmpty()) {
            ArrayList<User> users = new ArrayList<>();
            SharedPreferences.Editor usersEditor = preferences.edit();
            String usersJson = gson.toJson(users);
            usersEditor.putString("Users", usersJson);
            usersEditor.commit();
        }

        String userJson = preferences.getString("User", "");
        if (userJson.isEmpty()) {
            setContentView(R.layout.activity_no_user);
        } else {
            setContentView(R.layout.activity_main);
            currentUser = gson.fromJson(userJson, User.class);
            final TextView welcomeBack = (TextView) findViewById(R.id.welcome_back);
            final String welcomeTemplate = getString(R.string.welcome_back);
            welcomeBack.setText(welcomeTemplate+ " " + currentUser.getName()+"!");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void checkProfile(View view) {
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        //grab the current user and putExtra on it
        startActivity(profileIntent);
        overridePendingTransition(0, 0);
    }

    public void checkConvert(View view) {
        Intent convertIntent = new Intent(this, ConvertActivity.class);
        //grab the current user and putExtra on the intent
        startActivity(convertIntent);
        overridePendingTransition(0, 0);
    }

    public void checkCalculate(View view) {
        Intent calculateIntent = new Intent(this, CalculateActivity.class);
        //grab the current user and putExtra on the intent
        startActivity(calculateIntent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        Intent exitApp = new Intent(Intent.ACTION_MAIN);
        exitApp.addCategory(Intent.CATEGORY_HOME);
        exitApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(exitApp);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        //if (id == R.id.action_settings) {
//        //    return true;
//        //}
//
//        return super.onOptionsItemSelected(item);
//    }
}
