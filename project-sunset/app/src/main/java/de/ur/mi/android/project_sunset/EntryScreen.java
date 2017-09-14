package de.ur.mi.android.project_sunset;

import android.content.Intent;
import android.os.Build;
import android.os.Debug;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EntryScreen extends AppCompatActivity {


    Button newCalculation;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entryscreen);
        initUI();
        Log.d("Hi","HI");
        setOnClickListener();
        readCSV();


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void readCSV() {
        String csvFile = "/src/main/res/files/waypoints.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                Log.d("Hi","TH");
                System.out.println("Country [code= " + country[1] + " , name=" + country[2] + "]");

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }}

    private void setOnClickListener() {
        final Intent intent = new Intent(this,SecondScreen.class);
        newCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);
            }
        });
    }

    private void initUI() {
        newCalculation = (Button) findViewById(R.id.button);
    }
}
