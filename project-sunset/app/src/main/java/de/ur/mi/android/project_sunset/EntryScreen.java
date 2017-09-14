package de.ur.mi.android.project_sunset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EntryScreen extends AppCompatActivity {
    Button newCalculation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entryscreen);
        initUI();
        setOnClickListener();


    }

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
