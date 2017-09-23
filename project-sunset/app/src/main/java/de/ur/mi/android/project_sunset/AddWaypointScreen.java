package de.ur.mi.android.project_sunset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * a new screen to give the user the ability to add own waypoints to the waypoints.db
 */

public class AddWaypointScreen extends AppCompatActivity {

    EditText nameEdit;
    EditText latitudeEdit;
    EditText longitudeEdit;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waypoint_screen);
        initUI();
        setOnClick();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setOnClick() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks input
                if (nameEdit.getText().toString().length() != 5) {Toast.makeText(getBaseContext(), "Name muss 5 Zeichen lang sein!", Toast.LENGTH_SHORT).show();}
                else if (latitudeEdit.getText().toString().length() == 0) {Toast.makeText(getBaseContext(), "Breitengrad darf nicht leer sein!", Toast.LENGTH_SHORT).show();}
                else if (longitudeEdit.getText().toString().length() == 0) {Toast.makeText(getBaseContext(), "Längengrad darf nicht leer sein!", Toast.LENGTH_SHORT).show();}
                else {addWaypoint();}
            }
        });
    }

    /**
     * checks if waypoint already exists and adds a new waypoint to the waypoints.db
     */
    private void addWaypoint() {
        MyDatabaseAdapter mda = new MyDatabaseAdapter(getApplicationContext());
        mda.open();
        if (mda.getWaypointObjectByName(nameEdit.getText().toString()) != null) {Toast.makeText(getBaseContext(), "Waypoint existiert bereits!", Toast.LENGTH_SHORT).show();}
        else if(mda.addWaypoint(nameEdit.getText().toString(), longitudeEdit.getText().toString(), latitudeEdit.getText().toString())) {Toast.makeText(getBaseContext(), "Zu Favouriten hinzugefügt!", Toast.LENGTH_SHORT).show();}
        else {Toast.makeText(getBaseContext(), "Konnte nicht zu Favouriten hinzugefügt werden!", Toast.LENGTH_SHORT).show();}
        mda.close();
    }

    /**
     * refernces the UI-elements
     */
    private void initUI() {
        nameEdit = (EditText) findViewById(R.id.addWaypointName);
        latitudeEdit = (EditText) findViewById(R.id.addWaypointLatitude);
        longitudeEdit = (EditText) findViewById(R.id.addWaypointLongitude);
        addButton = (Button) findViewById(R.id.addWaypointButton);
     }
}
