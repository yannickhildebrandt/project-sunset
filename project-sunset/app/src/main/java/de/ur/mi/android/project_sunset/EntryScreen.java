package de.ur.mi.android.project_sunset;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class EntryScreen extends AppCompatActivity {
    Intent intent;
    RelativeLayout layout;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entryscreen);
        Log.d("ZZZ","Welcome");
        initUI();
        setOnClickListener();
    }

    private void setOnClickListener() {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void initUI() {
        intent = new Intent(this,SecondScreen.class);
        layout = (RelativeLayout) findViewById(R.id.entryScreenLayout);
    }
}
