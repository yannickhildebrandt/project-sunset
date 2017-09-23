package de.ur.mi.android.project_sunset;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MONTH;

public class SecondScreen extends AppCompatActivity {
    EditText waypointName;
    Button nextWaypoint;
    Button calculateButton;
    Button pickTime;
    Button addFavourite;
    TextView arrivalTimeText;

    private final static String ARRAYLIST_EXTRA_ID = "ArraylistExtra";
    private final static String BUNDLE_EXTRA_ID = "bundleExtra";

    ArrayList<LocationObject> locList;
    int arrivalTimeInSec = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        initUI();
        setOnClickListener();
        if (getIntent().getExtras() == null) {calculateButton.setEnabled(false);}
        initLocationList();
    }

    /**
     * checks if a ArrayList<LocationObject> has been passed. If not, a new Arraylist is created
     * This ArrayList to generate a list of Waypoints, which can be passed to the next Intent
     */
    private void initLocationList() {
        Intent i = getIntent();
        if (i.getExtras() == null) {
            locList = new ArrayList<LocationObject>();
            Log.d("ZZZ", "Arraylist created");}
        else {
            Bundle args = i.getBundleExtra(BUNDLE_EXTRA_ID);
            locList = (ArrayList<LocationObject>) args.getSerializable(ARRAYLIST_EXTRA_ID);
            for (LocationObject k: locList){
                Log.e("ZZZ", k.toString());
            }
        }

    }

    /**
     * inits all onClickListeners
     */
    private void setOnClickListener() {
        //creates all necessary intents
        final Intent secondScreenIntent = new Intent(this, SecondScreen.class);
        final Intent addWaypointIntent = new Intent(this, AddWaypointScreen.class);
        final Intent resultScreenIntnt = new Intent(this, ResultScreen.class);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //modified input check
                if (checkCorrectInput() || (waypointName.getText().toString().length() == 0 && arrivalTimeInSec == -1)) {
                    if (checkCorrectInput()){locList.add(createLocationObject());}
                    //creates a TimeCalculator to determine the exact suntimes
                    TimeCalculator tc = new TimeCalculator(getApplicationContext());
                    ResultObject result = tc.calculateResult(locList);
                    //puts all claculatet results in the result-intent
                    resultScreenIntnt.putExtra("manyClouds", result.getTimeManyClouds());
                    resultScreenIntnt.putExtra("mediumClouds", result.getTimeMediumClouds());
                    resultScreenIntnt.putExtra("noClouds", result.getTimeNoClouds());
                    resultScreenIntnt.putExtra("latitude", result.getPosLatitude());
                    resultScreenIntnt.putExtra("longitude", result.getPosLongitude());
                    startActivity(resultScreenIntnt);
                }
            }
        });
        nextWaypoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (checkCorrectInput()) {
                   LocationObject tempObj = createLocationObject();
                   if (tempObj != null) {
                       //adds the current LocationList to the ArrayList which is passed to the next Intent
                       locList.add(tempObj);
                       Bundle args = new Bundle();
                       args.putSerializable(ARRAYLIST_EXTRA_ID, locList);
                       secondScreenIntent.putExtra(BUNDLE_EXTRA_ID, args);
                       Toast.makeText(getBaseContext(), "Waypoint hinzugefügt!", Toast.LENGTH_SHORT).show();
                       startActivity(secondScreenIntent);
                   }
               }
            }
        });

        addFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(addWaypointIntent);
            }
        });

          pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerPopup();
            }
        });
    }

    /**
     * reads all input fields, then looks up matching suntimes and modifier in the database
     * @return a LocationObject containing the matching values to the given waypoint-name
     */
    private LocationObject createLocationObject() {
        int currentDay = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH);
        String name = waypointName.getText().toString().toUpperCase();
        MyDatabaseAdapter mda = new MyDatabaseAdapter(this);
        mda.open();
        WaypointObject loc = mda.getWaypointObjectByName(name);
        if (loc == null) {Toast.makeText(getBaseContext(), "Waypoint wurde nicht gefunden!", Toast.LENGTH_SHORT).show();}
        else {
            double longitude = loc.getLongitude();
            double latitude = loc.getLatitude();
            int sunrise = mda.getSunriseTime(Math.round(latitude), currentDay);
            int sunset = mda.getSunsetTime(Math.round(latitude), currentDay);
            int mod = mda.getModifier(Math.round(latitude), currentDay);
            mda.close();
            LocationObject temp = new LocationObject(name, longitude, latitude, sunrise, sunset, arrivalTimeInSec , mod);
            Log.e("ZZZ", "LocObj: " + temp.toString());
            return temp;
        }
        mda.close();
        return null;
    }

    /**
     * checks if the input for name and time is not empty
     * @return returns true if input is correct, false for incorrect input
     */
    private boolean checkCorrectInput(){
        if (waypointName.getText().toString().length() != 5) {Toast.makeText(getBaseContext(), "Name muss 5 Zeichen lang sein!", Toast.LENGTH_SHORT).show();}
        else if (arrivalTimeInSec == -1) {Toast.makeText(getBaseContext(), "Ankunftszeit darf nicht leer sein!", Toast.LENGTH_SHORT).show();}
        else {return true;}
        return false;
    }

    /**
     * references the layout elements
     */
    private void initUI() {
        waypointName = (EditText) findViewById(R.id.WaypointNameEdit);
        nextWaypoint = (Button) findViewById(R.id.nextWaypointButton);
        calculateButton = (Button) findViewById(R.id.buttonCalculate);
        pickTime = (Button) findViewById(R.id.setTimeButton);
        arrivalTimeText = (TextView) findViewById(R.id.arrivaltime);
        addFavourite = (Button) findViewById(R.id.waypointAddToFavourite);
    }

    /**
     * sets up the timepicker dialog
     */
    public void timePickerPopup() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        TimePickerDialog timePicker = new TimePickerDialog(this, R.style.AppTheme, datePickerListener, cal.get(HOUR_OF_DAY), cal.get(HOUR_OF_DAY), true);
        timePicker.setCancelable(false);
        timePicker.setTitle("Wähle die Ankunftszeit");
        timePicker.show();
    }


    /**
     * sets the time picker on a datePickerListener and sets the input to the interface
     */
    private  TimePickerDialog.OnTimeSetListener datePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            arrivalTimeInSec = (selectedHour * 3600) + (selectedMinute * 60);
            String hour = String.valueOf(selectedHour);
            String minute = String.valueOf(selectedMinute);
            arrivalTimeText.setText("Zeitpunkt des Erreichens: " + hour + ":" + minute);
        }
    };
}