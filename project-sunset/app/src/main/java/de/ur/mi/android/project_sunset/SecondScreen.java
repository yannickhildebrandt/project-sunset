package de.ur.mi.android.project_sunset;

/**
 * Created by yannickhildebrandt on 14/09/17.
 */

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import static java.util.Calendar.HOUR_OF_DAY;

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

    ArrayList<LocationObject> locList;
    int arrivalTimeInSec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        initUI();
        setOnClickListener();
        if (getIntent().getExtras() == null) {calculateButton.setEnabled(false);}
        initLocationList();

    }

    private void initLocationList() {
        Intent i = getIntent();
        if (i == null) {locList = new ArrayList<LocationObject>();}
        else {}//get Intentextra

    }

    // Onclicklistener setzen -- meist noch leer
    private void setOnClickListener() {
        final Intent calculatorIntent = new Intent(this,Calculator.class);
        final Intent secondScreenIntent = new Intent(this, SecondScreen.class);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(calculatorIntent);
            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waypointName.getText().toString().length() != 5){Toast.makeText(getBaseContext(), "Name muss 5 Zeichen lang sein!",Toast.LENGTH_SHORT).show();}
                else {}
            }
        });
        nextWaypoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waypointName.getText().toString().length() != 5){Toast.makeText(getBaseContext(), "Name muss 5 Zeichen lang sein!", Toast.LENGTH_SHORT).show();}
                else {
                    locList.add(createLocationObject());
                    startActivity(secondScreenIntent);
                }
            }
        });

        /**
         * Platzhalter für Button-OnClickListener
         * waypointName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerPopup();
            }
        }); **/

    }

    private LocationObject createLocationObject() {
        String name = waypointName.getText().toString();
        MyDatabaseAdapter mda = new MyDatabaseAdapter(this);
        mda.open();
        WaypointObject loc = mda.getWaypointObjectByName(name);
        mda.close();
        if (loc == null) {Toast.makeText(getBaseContext(), "Waypoint wurde nicht gefunden!", Toast.LENGTH_SHORT).show();}
        else {
            Float longitude = loc.getLongitude();
            Float latitude = loc.getLatitude();
            //Beispielwerte
            int one = 1;
            int two = 2;
            int three = arrivalTimeInSec;
            int four = 4;
            return new LocationObject(name, longitude, latitude, one, two, three ,four);
        }
        return null;
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

    public void timePickerPopup() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        TimePickerDialog timePicker = new TimePickerDialog(this, R.style.AppTheme, datePickerListener, cal.get(HOUR_OF_DAY), cal.get(HOUR_OF_DAY), true);
        timePicker.setCancelable(false);
        timePicker.setTitle("Select the date");
        timePicker.show();
    }

    private  TimePickerDialog.OnTimeSetListener datePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            arrivalTimeInSec = (selectedHour * 3600) + (selectedMinute * 60);
            String hour = String.valueOf(selectedHour);
            String minute = String.valueOf(selectedMinute);
            waypointTime.setText( minute + ":" + hour);
        }
    };


}