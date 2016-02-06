package com.github.connietruong.caloriemonitor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Connie on 2/2/2016.
 */
public class CalorieConFragment extends Fragment{
    User user;
    ArrayList<Exercise> exerciseList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        Gson gson = new Gson();
        exerciseList = gson.fromJson(preferences.getString("Exercises", ""), new TypeToken<ArrayList<Exercise>>() {}.getType());
        View v = inflater.inflate(R.layout.calories_con_view, container, false);;

        String userJson = preferences.getString("User", "");
        if (userJson.isEmpty()) {
            v = inflater.inflate(R.layout.activity_no_user_con, container, false);
        }
        else {
            user = gson.fromJson(preferences.getString("User", ""), User.class);

            EditText valueInput = (EditText) v.findViewById(R.id.convert_from_value);

            valueInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText value = (EditText) v.findViewById(R.id.convert_from_value);
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

            valueInput.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    String newInput = s.toString();
                    TextView value = (TextView) getView().findViewById(R.id.convert_from_unit);

                    ArrayList<TextView> allValues = initializeExerciseValueText();
                    ArrayList<TextView> allUnits = initializeExerciseUnitText();

                    if (!newInput.isEmpty()) {
                        int calories = Integer.parseInt(newInput);

                        if (calories == 1) {
                            value.setText("Calorie");
                        } else {
                            value.setText("Calories");
                        }

                        for (int i = 0; i < exerciseList.size(); i++) {
                            Exercise currExercise = exerciseList.get(i);
                            TextView currValue = allValues.get(i);
                            TextView currUnits = allUnits.get(i);
                            Integer neededValue = currExercise.exerciseNeeded(user, calories);
                            currValue.setText(Integer.toString(neededValue));
                            if (neededValue == 1) {
                                if (currExercise.isRep()) {
                                    currUnits.setText("Rep");
                                } else {
                                    currUnits.setText("Minute");
                                }
                            } else {
                                if (currExercise.isRep()) {
                                    currUnits.setText("Reps");
                                } else {
                                    currUnits.setText("Minutes");
                                }
                            }
                        }
                    } else {
                        value.setText("Calories");
                        for (int i = 0; i < exerciseList.size(); i++) {
                            Exercise currExercise = exerciseList.get(i);
                            TextView currValue = allValues.get(i);
                            TextView currUnits = allUnits.get(i);
                            currValue.setText("0");
                            if (currExercise.isRep()) {
                                currUnits.setText("Reps");
                            } else {
                                currUnits.setText("Minutes");
                            }
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
        return v;
    }

    private ArrayList<TextView> initializeExerciseValueText() {
        ArrayList<TextView> allValues = new ArrayList<>();
        allValues.add(((TextView) getView().findViewById(R.id.con_pushup_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_situp_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_squats_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_leg_lift_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_plank_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_jumping_jacks_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_pullup_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_cycling_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_walking_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_jogging_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_swimming_val)));
        allValues.add(((TextView) getView().findViewById(R.id.con_stair_climbing_val)));

        return allValues;
    }

    private ArrayList<TextView> initializeExerciseUnitText() {
        ArrayList<TextView> allUnits = new ArrayList<>();
        allUnits.add((TextView) getView().findViewById(R.id.con_pushup_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_situp_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_squats_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_leg_lift_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_plank_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_jumping_jacks_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_pullup_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_cycling_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_walking_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_jogging_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_swimming_unit));
        allUnits.add((TextView) getView().findViewById(R.id.con_stair_climbing_unit));
        return allUnits;
    }

}
