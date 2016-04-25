package com.vanakkam.rvnve.vanakkam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class ChristmasActivity extends DashboardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_christmas);

        GridView gridview = (GridView) findViewById(R.id.gvChristmasCategory);
        gridview.setAdapter(new ImageAdapter(this,"christmas"));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                try {
                    // Sending image id to ViewDetailActivity
                    Intent i = new Intent(getApplicationContext(), ViewDetailActivity.class);
                    // passing array index
                    i.putExtra("id", position);
                    i.putExtra("pageName", "christmas");
                    startActivity(i);
                } catch (Exception e) {
                    Log.e("Christmas", e.getMessage().toString());
                } finally {
                    finish();
                }
            }
        });

    }
}
