package de.ur.mi.android.project_sunset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

    public class Calculator extends AppCompatActivity {
        EditText gradEdit;
        EditText minutesEdit;
        Button calculateTo;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.calculator_screen);
            initUI();
            setOnClickListener();

        }

    private void setOnClickListener() {
        calculateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initUI() {
        gradEdit = (EditText) findViewById(R.id.gradEdit);
        minutesEdit = (EditText) findViewById(R.id.minutesEdit);
        calculateTo = (Button) findViewById(R.id.buttonCalculateTo);
    }

}
