package com.vanakkam.rvnve.vanakkam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;


public class BirthdayActivity extends DashboardActivity {

    private int columnWidth;
    private GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        gridview = (GridView) findViewById(R.id.gvBirthdayCategory);
        gridview.setAdapter(new ImageAdapter(this,"birthday"));

        //On Click event for Single Gridview Item
        gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                try {
                    // Sending image id to ViewDetailActivity
                    Intent i = new Intent(getApplicationContext(), ViewDetailActivity.class);
                    // passing array index
                    i.putExtra("id", position);
                    i.putExtra("pageName", "birthday");
                    startActivity(i);
                } catch (Exception e) {
                    Log.e("Birthday", e.getMessage().toString());
                } finally {
                    finish();
                }
            }
        });
    }
}




