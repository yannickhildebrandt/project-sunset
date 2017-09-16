package de.ur.mi.android.project_sunset;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class EntryScreen extends AppCompatActivity {
    Intent intent;
    InputStream is;
    Button newCalculation;
    ProgressBar progressBar;
    MyDatabaseAdapter myDatabaseAdapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entryscreen);
        Log.d("CREATION","Welcome");
        initUI();
        setOnClickListener();
    }


    private void setOnClickListener() {
        newCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCalculation.setEnabled(false);

                progressBar.setEnabled(true);
                progressBar.setScaleY(10f);
                new loadCSV().execute(is);

            }
        });
    }

    private void initUI() {
        intent = new Intent(this,SecondScreen.class);
        newCalculation = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setEnabled(false);
        progressBar.setMax(38540);
        progressBar.setProgress(0);
        is = getResources().openRawResource(R.raw.waypoints);

    }
    class loadCSV extends AsyncTask<InputStream, Integer, String>{
        @Override
        protected String doInBackground(InputStream... params) {
            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ",";
            int count = 0;
            try {

                Log.d("InitReader","Initialize file reader");
                br = new BufferedReader(new InputStreamReader(params[0], Charset.forName("UTF-8")));
                Log.d("ReaderCompleted","Filereader initialized");
                while ((line = br.readLine()) != null) {
                count++;
                    // use comma as separator
                    String[] waypoint = line.split(cvsSplitBy);
                    System.out.println(waypoint[5]);
                    publishProgress(count);
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
            return "Task Completed";
        }}

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //System.out.println("CSV Datei Laden erfolgreich"+myDatabaseAdapter.getAllMyObjects().getCount());
            startActivity(intent);
        }
    }

}
