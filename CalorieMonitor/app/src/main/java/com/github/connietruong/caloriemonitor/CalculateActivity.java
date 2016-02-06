package com.github.connietruong.caloriemonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CalculateActivity extends AppCompatActivity {
    ArrayList<Exercise> exerciseList;
    User user;
    Integer i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        exerciseList = gson.fromJson(preferences.getString("Exercises", ""),
                new TypeToken<ArrayList<Exercise>>() {}.getType());

        String userJson = preferences.getString("User", "");
        if (userJson.isEmpty()) {
            setContentView(R.layout.activity_no_user_cal);
        } else {
            user = gson.fromJson(preferences.getString("User", ""), User.class);
            setContentView(R.layout.activity_calculate);

            final ArrayList<EditText> allValues = initializeExerciseValueText();
            final ArrayList<TextView> allUnits = initializeExerciseUnitText();

            for (i=0; i < exerciseList.size(); i++) {
                EditText currentVal = allValues.get(i);

                currentVal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        EditText value = (EditText) v;
                        if (hasFocus) {
                            if (value.getText().toString().equals("0")) {
                                value.setText("");
                            }
                        } else {
                            if (value.getText().toString().equals("")) {
                                value.setText("0");
                            }
                        }
                    }
                });

                currentVal.addTextChangedListener(new TextWatcher() {
                    int counter = i;

                    public void afterTextChanged(Editable s) {
                        String newInput = s.toString();
                        TextView totalVal = (TextView) findViewById(R.id.total_value);
                        TextView totalUnits = (TextView) findViewById(R.id.total_units);
                        Exercise currExercise = exerciseList.get(counter);
                        TextView currentUnits = allUnits.get(counter);

                        if (!newInput.isEmpty()) {
                            Integer intInput = Integer.parseInt(newInput);
                            if (intInput == 1) {
                                if (currExercise.isRep()) {
                                    currentUnits.setText("Rep");
                                } else {
                                    currentUnits.setText("Minute");
                                }
                            } else {
                                if (currExercise.isRep()) {
                                    currentUnits.setText("Reps");
                                } else {
                                    currentUnits.setText("Minutes");
                                }
                            }
                            currExercise.changeInputValue(intInput);
                            Integer totalCalories = Exercise.totalCalories(user, exerciseList);
                            totalVal.setText(Integer.toString(totalCalories));
                            if (totalCalories == 1) {
                                totalUnits.setText("Calorie");
                            } else {
                                totalUnits.setText("Calories");
                            }
                        }
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                    }
                });
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private ArrayList<EditText> initializeExerciseValueText() {
        ArrayList<EditText> allValues = new ArrayList<>();
        allValues.add(((EditText) findViewById(R.id.cal_pushup_val)));
        allValues.add(((EditText) findViewById(R.id.cal_situp_val)));
        allValues.add(((EditText) findViewById(R.id.cal_squats_val)));
        allValues.add(((EditText) findViewById(R.id.cal_leg_lift_val)));
        allValues.add(((EditText) findViewById(R.id.cal_plank_val)));
        allValues.add(((EditText) findViewById(R.id.cal_jumping_jacks_val)));
        allValues.add(((EditText) findViewById(R.id.cal_pullup_val)));
        allValues.add(((EditText) findViewById(R.id.cal_cycling_val)));
        allValues.add(((EditText) findViewById(R.id.cal_walking_val)));
        allValues.add(((EditText) findViewById(R.id.cal_jogging_val)));
        allValues.add(((EditText) findViewById(R.id.cal_swimming_val)));
        allValues.add(((EditText) findViewById(R.id.cal_stair_climbing_val)));

        return allValues;
    }

    private ArrayList<TextView> initializeExerciseUnitText() {
        ArrayList<TextView> allUnits = new ArrayList<>();
        allUnits.add((TextView) findViewById(R.id.cal_pushup_unit));
        allUnits.add((TextView) findViewById(R.id.cal_situp_unit));
        allUnits.add((TextView) findViewById(R.id.cal_squats_unit));
        allUnits.add((TextView) findViewById(R.id.cal_leg_lift_unit));
        allUnits.add((TextView) findViewById(R.id.cal_plank_unit));
        allUnits.add((TextView) findViewById(R.id.cal_jumping_jacks_unit));
        allUnits.add((TextView) findViewById(R.id.cal_pullup_unit));
        allUnits.add((TextView) findViewById(R.id.cal_cycling_unit));
        allUnits.add((TextView) findViewById(R.id.cal_walking_unit));
        allUnits.add((TextView) findViewById(R.id.cal_jogging_unit));
        allUnits.add((TextView) findViewById(R.id.cal_swimming_unit));
        allUnits.add((TextView) findViewById(R.id.cal_stair_climbing_unit));
        return allUnits;
    }

    public void resetValues(View view) {
        ArrayList<EditText> allValues = initializeExerciseValueText();
        ArrayList<TextView> allUnits = initializeExerciseUnitText();

        TextView total = (TextView) findViewById(R.id.total_value);
        for (Integer j=0;j < exerciseList.size(); j++) {
            EditText currVal = allValues.get(j);
            if (exerciseList.get(j).isRep()) {
                allUnits.get(j).setText("Reps");
            } else {
                allUnits.get(j).setText("Minutes");
            }
            currVal.setText("0");
        }
        total.setText("0");
    }

    public void checkProfile(View view) {
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        startActivity(profileIntent);
        overridePendingTransition(0, 0);
    }

    public void checkConvert(View view) {
        Intent convertIntent = new Intent(this, ConvertActivity.class);
        startActivity(convertIntent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(this, MainActivity.class);
        startActivity(backIntent);
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}
