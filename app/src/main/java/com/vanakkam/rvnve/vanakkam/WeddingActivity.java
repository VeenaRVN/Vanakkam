package com.vanakkam.rvnve.vanakkam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class WeddingActivity extends DashboardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wedding);

        GridView gridview = (GridView) findViewById(R.id.gvWeddingCategory);
        gridview.setAdapter(new ImageAdapter(this,"wedding"));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                try {
                    // Sending image id to ViewDetailActivity
                    Intent i = new Intent(getApplicationContext(), ViewDetailActivity.class);
                    // passing array index
                    i.putExtra("id", position);
                    i.putExtra("pageName", "wedding");
                    startActivity(i);
                } catch (Exception e) {
                    Log.e("Wedding", e.getMessage().toString());
                } finally {
                    finish();
                }
            }
        });

    }
}
