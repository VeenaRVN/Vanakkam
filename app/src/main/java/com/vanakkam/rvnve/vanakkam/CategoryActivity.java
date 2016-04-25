package com.vanakkam.rvnve.vanakkam;

import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;


public class CategoryActivity extends AppCompatActivity {

    //Adding for logging
    private static final String TAG = "vanakkam.statechange";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Lifecycle Methods

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        Log.i(TAG, "onDestroy");

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
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

    //Start the birthday category

    public void onBirthday(View v) {
        Log.i(TAG, "onBirthday");
        startActivity(new Intent(getApplicationContext(), BirthdayActivity.class));
        finish();
    }

    //Start the Christmas category

    public void onChristmas(View v) {
        Log.i(TAG, "onChristams");
        startActivity(new Intent(getApplicationContext(), ChristmasActivity.class));
        finish();
    }

    //Start the Christmas category

    public void onWedding(View v) {
        Log.i(TAG, "onWedding");
        startActivity(new Intent(getApplicationContext(), WeddingActivity.class));
        finish();
    }


}
