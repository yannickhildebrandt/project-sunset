package de.ur.mi.android.project_sunset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * shows the the results of the calculation in a dedicated screen
 */

public class ResultScreen extends AppCompatActivity {

    int timeManyClouds;
    int timeMediumClouds;
    int timeNoClouds;
    double latitude;
    double longitude;

    TextView noCloudsText;
    TextView mediumCloudsText;
    TextView manyCloudsText;
    TextView positionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen);
        initUI();
        initExtras();
        setResults();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SecondScreen.class));
        finish();
    }

    /**
     * sets the reuslts to the textviews
     */
    private void setResults() {
        if (timeNoClouds == -1){noCloudsText.setText(getString(R.string.errorResult));}
        else {noCloudsText.setText(formatTime(timeNoClouds));}
        if (timeMediumClouds == -1) {mediumCloudsText.setText(getString(R.string.errorResult));}
        else {mediumCloudsText.setText(formatTime(timeMediumClouds));}
        if (timeManyClouds == -1) {manyCloudsText.setText(getString(R.string.errorResult));}
        else {manyCloudsText.setText(formatTime(timeManyClouds));}
        if (latitude != -1) {positionText.setText("Länge: " + Double.toString(round(latitude,6)) + "; Breite; " + Double.toString(round(longitude,6)));}
    }

    /**
     * gets all extras from the intent
     */
    private void initExtras() {
        Intent i = getIntent();
        timeManyClouds = i.getIntExtra("manyClouds", -1);
        timeMediumClouds = i.getIntExtra("mediumClouds", -1);
        timeNoClouds = i.getIntExtra("noClouds", -1);
        latitude = i.getDoubleExtra("latitude", -1);
        longitude = i.getDoubleExtra("longitude", -1);
    }

    /**
     * references the UI-elements
     */
    private void initUI() {
        noCloudsText = (TextView) findViewById(R.id.noCloudsResult);
        mediumCloudsText = (TextView) findViewById(R.id.fewCloudsResult);
        manyCloudsText = (TextView) findViewById(R.id.cloudsResult);
        positionText = (TextView) findViewById(R.id.positionResult);
    }

    /**
     * formats time in seconds to a "HH:MM" - string
     * @param seconds time in seconds
     * @return formated time
     */
    private String formatTime(int seconds) {
        int hours = (seconds % 86400 ) / 3600 ;
        int minutes = ((seconds % 86400 ) % 3600 ) / 60;
        int second = ((seconds % 86400 ) % 3600 ) % 60;

        return "" + hours + ":" + minutes;
    }

    /**
     * rounds a double to specific places
     * @param value double to be rounded
     * @param places number of places after comma
     * @return rounded number
     */
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
