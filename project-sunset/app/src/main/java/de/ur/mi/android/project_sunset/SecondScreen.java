package de.ur.mi.android.project_sunset;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import static java.util.Calendar.HOUR_OF_DAY;

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
    int arrivalTimeInSec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        initUI();
        setOnClickListener();
        if (getIntent().getExtras() == null) {calculateButton.setEnabled(false);}
        initLocationList();

        //Platzhalter bis Timepicker implementiert ist
    }

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

    private void setOnClickListener() {
        final Intent secondScreenIntent = new Intent(this, SecondScreen.class);
        final Intent addWaypointIntent = new Intent(this, AddWaypointScreen.class);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCorrectInput()) {
                    TimeCalculator tc = new TimeCalculator(getApplicationContext());
                    ResultObject result = tc.calculateResult(locList);
                }
            }
        });
        nextWaypoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (checkCorrectInput()) {
                   LocationObject tempObj = createLocationObject();
                   if (tempObj != null) {
                       locList.add(tempObj);
                       Log.d("ZZZ", "added to Arraylist");
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

    private LocationObject createLocationObject() {
        String name = waypointName.getText().toString().toUpperCase();
        MyDatabaseAdapter mda = new MyDatabaseAdapter(this);
        mda.open();
        WaypointObject loc = mda.getWaypointObjectByName(name);
        mda.close();
        if (loc == null) {Toast.makeText(getBaseContext(), "Waypoint wurde nicht gefunden!", Toast.LENGTH_SHORT).show();}
        else {
            Float longitude = loc.getLongitude();
            Float latitude = loc.getLatitude();
            //Beispielwerte
            int one = -1;
            int two = -1;
            int three = arrivalTimeInSec;
            int four = -1;
            return new LocationObject(name, longitude, latitude, one, two, three ,four);
        }
        return null;
    }

    private boolean checkCorrectInput(){
        if (waypointName.getText().toString().length() != 5) {Toast.makeText(getBaseContext(), "Name muss 5 Zeichen lang sein!", Toast.LENGTH_SHORT).show();}
        else if (arrivalTimeInSec == -1) {Toast.makeText(getBaseContext(), "Ankunftszeit darf nicht leer sein!", Toast.LENGTH_SHORT).show();}
        else {return true;}
        return false;
    }

    private void initUI() {
        waypointName = (EditText) findViewById(R.id.WaypointNameEdit);
        nextWaypoint = (Button) findViewById(R.id.nextWaypointButton);
        calculateButton = (Button) findViewById(R.id.buttonCalculate);
        pickTime = (Button) findViewById(R.id.setTimeButton);
        arrivalTimeText = (TextView) findViewById(R.id.arrivaltime);
        addFavourite = (Button) findViewById(R.id.waypointAddToFavourite);
    }

    public void timePickerPopup() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        TimePickerDialog timePicker = new TimePickerDialog(this, R.style.AppTheme, datePickerListener, cal.get(HOUR_OF_DAY), cal.get(HOUR_OF_DAY), true);
        timePicker.setCancelable(false);
        timePicker.setTitle("Wähle die Ankunftszeit");
        timePicker.show();
    }

    private  TimePickerDialog.OnTimeSetListener datePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            arrivalTimeInSec = (selectedHour * 3600) + (selectedMinute * 60);
            String hour = String.valueOf(selectedHour);
            String minute = String.valueOf(selectedMinute);
            arrivalTimeText.setText("Zeitpunkt des Erreichens: " + hour + ":" + minute);
        }
    };
}