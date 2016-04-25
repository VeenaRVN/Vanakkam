package com.vanakkam.rvnve.vanakkam;

import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;


public class DashboardActivity extends AppCompatActivity {

    //Adding for logging
    private static final String TAG = "vanakkam.statechange";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Lifecycle Methods

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    //App Click Methods

    //Create functionality to return to homepage
    public void return2Home(Context context) {
        Log.i(TAG, "onReturnToHome");
        final Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void onHome(View v) {
        Log.i(TAG, "onHome");
        return2Home(this);
    }


    //Start the category activity
    public void onPickCategory(View v) {
        Log.i(TAG, "onPickCategory");
        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
    }

    //Start the viewdetail with phone images
    public void onPickPhone(View v) {
        Log.i(TAG, "onPickPhone");
        startActivity(new Intent(getApplicationContext(), FromPhoneActivity.class));
    }


    //Add a reminder

    public void onAddReminder(View v) {
        Log.i(TAG, "onAddReminder");
        Intent intent;
        //Add Reminder
        Calendar cal = Calendar.getInstance();
        intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
        intent.putExtra("title", "A Event from Vanakkam app");

        startActivity(intent);
        Toast.makeText(getBaseContext(), "Event Added", Toast.LENGTH_LONG).show();

    }


}
