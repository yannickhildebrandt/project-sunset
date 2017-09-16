package de.ur.mi.android.project_sunset;

/**
 * Created by yannickhildebrandt on 14/09/17.
 */

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SecondScreen extends AppCompatActivity {
    RadioButton sunsetRadioButton;
    RadioButton sundownRadioButton;
    RadioButton waypointNameRadioButton;
    RadioButton waypointDataRadioButton;
    EditText waypointName;
    EditText waypointLongitude;
    EditText waypointLatitude;
    EditText waypointTime;
    Button calculatorButton;
    Button nextWaypoint;
    Button calculateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        initUI();
        setOnClickListener();
        MyDatabaseAdapter mda = new MyDatabaseAdapter(this);
        Log.d("CREATION","Datbase Created");
        mda.open();
        Log.d("CREATION","Database opend");
        Log.d("CREATION",mda.getWaypointObjectByName("GOSHU").toString());
        mda.close();
        Log.d("CREATION","Database closed");

    }

// Onclicklistener setzen -- meist noch leer
    private void setOnClickListener() {
        final Intent calculatorIntent = new Intent(this,Calculator.class);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(calculatorIntent);
            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nextWaypoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
// UI Elemente Initialisieren
    private void initUI() {
        sunsetRadioButton = (RadioButton) findViewById(R.id.sunsetRadioButton);
        sundownRadioButton = (RadioButton) findViewById(R.id.sundownRadioButton);
        waypointNameRadioButton = (RadioButton) findViewById(R.id.WaypointNameRadioButton);
        waypointDataRadioButton = (RadioButton) findViewById(R.id.radioButtonWaypointData);
        waypointName = (EditText) findViewById(R.id.WaypointNameEdit);
        waypointLongitude = (EditText) findViewById(R.id.waypointLongitudeEdit);
        waypointLatitude = (EditText) findViewById(R.id.waypointLatitudeEdit);
        waypointTime = (EditText) findViewById(R.id.waypoint_time);
        calculatorButton = (Button) findViewById(R.id.calculatorButton);
        nextWaypoint = (Button) findViewById(R.id.nextWaypointButton);
        calculateButton = (Button) findViewById(R.id.buttonCalculate);

    }
}