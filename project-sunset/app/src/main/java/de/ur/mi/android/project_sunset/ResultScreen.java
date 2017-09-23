package de.ur.mi.android.project_sunset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
        noCloudsText.setText(Integer.toString(timeNoClouds));
        mediumCloudsText.setText(Integer.toString(timeMediumClouds));
        manyCloudsText.setText(Integer.toString(timeManyClouds));
        positionText.setText("" + Double.toString(latitude) + " " + Double.toString(longitude));
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
}
