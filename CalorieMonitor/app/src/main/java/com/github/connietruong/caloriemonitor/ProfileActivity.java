package com.github.connietruong.caloriemonitor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();

        String userJson = preferences.getString("User", "");
        if (userJson.isEmpty()) {
            setContentView(R.layout.activity_make_profile);
        } else {
            user = gson.fromJson(preferences.getString("User", ""),
                    new TypeToken<User>() {
                    }.getType());
            setContentView(R.layout.activity_profile);

            EditText name = (EditText) findViewById(R.id.name);
            Spinner sexSpinner = (Spinner) findViewById(R.id.sex_spinner);
            Spinner ageSpinner = (Spinner) findViewById(R.id.age_spinner);
            Spinner heightInSpinner = (Spinner) findViewById(R.id.height_inch_spinner);
            Spinner heightFtSpinner = (Spinner) findViewById(R.id.height_foot_spinner);
            EditText weightValue = (EditText) findViewById(R.id.weight_value);

            ArrayAdapter<CharSequence> sexAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item, initializeSexValues());
            int sexSpinnerPosition = sexAdapter.getPosition(user.getSex());
            sexSpinner.setAdapter(sexAdapter);
            sexSpinner.setSelection(sexSpinnerPosition);

            ArrayAdapter<CharSequence> ageAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item, initializeAgeValues());
            int ageSpinnerPosition = ageAdapter.getPosition(Integer.toString(user.getAge()));
            ageSpinner.setAdapter(ageAdapter);
            ageSpinner.setSelection(ageSpinnerPosition);

            ArrayAdapter<CharSequence> heightInAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item, initializeHeightInValues());
            int heightInSpinnerPosition = heightInAdapter.getPosition(Integer.toString(user.getHeightIn()));
            heightInSpinner.setAdapter(heightInAdapter);
            heightInSpinner.setSelection(heightInSpinnerPosition);

            ArrayAdapter<CharSequence> heightFtAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item, initializeHeightFtValues());
            int heightFtSpinnerPosition = heightFtAdapter.getPosition(Integer.toString(user.getHeightFt()));
            heightFtSpinner.setAdapter(heightFtAdapter);
            heightFtSpinner.setSelection(heightFtSpinnerPosition);

            name.setText(user.getName());
            weightValue.setText(Integer.toString(user.getWeight()));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void saveCurrentUser(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        Spinner sexSpinner = (Spinner) findViewById(R.id.sex_spinner);
        Spinner ageSpinner = (Spinner) findViewById(R.id.age_spinner);
        Spinner heightInSpinner = (Spinner) findViewById(R.id.height_inch_spinner);
        Spinner heightFtSpinner = (Spinner) findViewById(R.id.height_foot_spinner);
        EditText weight = (EditText) findViewById(R.id.weight_value);

        user.changeName(name.getText().toString());
        user.changeSex(sexSpinner.getSelectedItem().toString());
        String age = ageSpinner.getSelectedItem().toString();
        if (age.equals("60+")) {
            user.changeAge(60);
        } else {
            user.changeAge(Integer.parseInt(age));
        }
        user.changeWeight(Integer.parseInt(weight.getText().toString()));
        Integer heightIn = 12 * Integer.parseInt(heightFtSpinner.getSelectedItem().toString())
                + Integer.parseInt(heightInSpinner.getSelectedItem().toString());
        user.changeHeight(heightIn);

        Gson gson = new Gson();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = preferences.edit();
        String newUserJson = gson.toJson(user);
        prefsEditor.putString("User", newUserJson);
        prefsEditor.commit();

        ArrayList<User> users = gson.fromJson(preferences.getString("Users", ""),
                new TypeToken<ArrayList<User>>(){}.getType());
        users.set(user.getId(), user);
        SharedPreferences.Editor usersEditor = preferences.edit();
        usersEditor.putString("Users", gson.toJson(users));
        usersEditor.commit();

        //REMEMBER TO SAVE THE NEW USER IN THE USERS SHARED PREFERENCES

        TextView save_message = (TextView) findViewById(R.id.changes_saved);
        save_message.setText("Changes have been saved.");
    }

    private ArrayList<String> initializeSexValues() {
        ArrayList<String> sexValues = new ArrayList<>();
        sexValues.add("Male");
        sexValues.add("Female");
        return sexValues;
    }

    private ArrayList<String> initializeAgeValues() {
        ArrayList<String> ageValues = new ArrayList<>();
        for (int i=15; i < 59; i++) {
            ageValues.add(Integer.toString(i));
        }
        ageValues.add("60+");
        return ageValues;
    }

    private ArrayList<String> initializeHeightFtValues() {
        ArrayList<String> heightFtValues = new ArrayList<>();
        for (int i = 4; i <= 8; i++) {
            heightFtValues.add(Integer.toString(i));
        }
        return heightFtValues;
    }

    private ArrayList<String> initializeHeightInValues() {
        ArrayList<String> heightInValues = new ArrayList<>();
        for (int i = 0; i <= 11; i++) {
            heightInValues.add(Integer.toString(i));
        }
        return heightInValues;
    }

    public void switchProfile(final View view) {
        AlertDialog dialog;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();

        ArrayList<User> users = gson.fromJson(preferences.getString("Users", ""),
                new TypeToken<ArrayList<User>>(){}.getType());
        ArrayList<String> userNames = new ArrayList<String>();
        for (User user : users) {
            if (user.getName().isEmpty()) {
                userNames.add("New Name");
            } else {
                userNames.add(user.getName());
            }
        }
        userNames.add("New Profile");

        final CharSequence[] items = userNames.toArray(new CharSequence[userNames.size()]);
        int selectPos;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select profile");

        if (user == null) {
            selectPos = -1;
        } else {
            selectPos = user.getId();
        }
        builder.setSingleChoiceItems(items, selectPos, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Button deleteButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEUTRAL);
                Gson gson = new Gson();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                ArrayList<User> users = gson.fromJson(preferences.getString("Users", ""),
                        new TypeToken<ArrayList<User>>(){}.getType());
                if (item == users.size()) {
                    deleteButton.setEnabled(false);
                } else {
                    deleteButton.setEnabled(true);
                }
            }
        })
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    ListView lw = ((AlertDialog) dialog).getListView();
                    int currentPosition = lw.getCheckedItemPosition();

                    Gson gson = new Gson();
                    User newUser;
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    ArrayList<User> users = gson.fromJson(preferences.getString("Users", ""),
                            new TypeToken<ArrayList<User>>() {
                            }.getType());
                    if (currentPosition == users.size()) {
                        newUser = new User("", "Male", 15, 48, 100, currentPosition);
                        users.add(newUser);
                        SharedPreferences.Editor usersEditor = preferences.edit();
                        usersEditor.putString("Users", gson.toJson(users));
                        usersEditor.putString("User", gson.toJson(newUser));
                        usersEditor.commit();
                        user = newUser;
                    } else {
                        User currentUser = users.get(currentPosition);
                        SharedPreferences.Editor prefsEditor = preferences.edit();
                        String newUserJson = gson.toJson(currentUser);
                        prefsEditor.putString("User", newUserJson);
                        prefsEditor.commit();
                        user = currentUser;
                    }

                    Intent convertIntent = new Intent(view.getContext(), ProfileActivity.class);
                    startActivity(convertIntent);
                    overridePendingTransition(0, 0);

                }
            })
            .setNeutralButton("Delete Profile", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    ListView lw = ((AlertDialog) dialog).getListView();
                        final int currentPosition = lw.getCheckedItemPosition();

                        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(view.getContext());
                        confirmBuilder.setMessage("Are you sure you want to delete this profile?");
                        confirmBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Gson gson = new Gson();
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                ArrayList<User> users = gson.fromJson(preferences.getString("Users", ""),
                                        new TypeToken<ArrayList<User>>() {
                                        }.getType());
                                if (currentPosition != users.size()) {
                                    User newUser = gson.fromJson(preferences.getString("User", ""), User.class);
                                    if (newUser.getId() == currentPosition) {
                                        SharedPreferences.Editor prefsEditor = preferences.edit();
                                        prefsEditor.putString("User", "");
                                        prefsEditor.commit();
                                    }
                                    for (int i = currentPosition; i < users.size(); i++) {
                                        User currUser = users.get(i);
                                        currUser.changeId(currUser.getId() - 1);
                                    }
                                    users.remove(currentPosition);
                                    SharedPreferences.Editor usersEditor = preferences.edit();
                                    usersEditor.putString("Users", gson.toJson(users));
                                    usersEditor.commit();
                                }
                                Intent convertIntent = new Intent(view.getContext(), ProfileActivity.class);
                                startActivity(convertIntent);
                                overridePendingTransition(0, 0);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                        AlertDialog confirmDialog = confirmBuilder.create();
                        confirmDialog.show();
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //  Your code when user clicked on Cancel

                }
            });
        dialog = builder.create();
        //AlertDialog dialog; create like this outside onClick
        dialog.show();
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
