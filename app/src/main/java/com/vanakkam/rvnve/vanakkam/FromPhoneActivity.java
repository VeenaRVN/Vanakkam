package com.vanakkam.rvnve.vanakkam;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;


public class FromPhoneActivity extends DashboardActivity {

    private GridView gridview;
    File file;
    private File[] listFile;
    private String[] FilePathStrings;
    private String[] FileNameStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard);
        gridview = (GridView) findViewById(R.id.gvFromPhone);

        Log.e("start-sdcard", Environment.getExternalStorageDirectory().getAbsolutePath());

        // Locate the GridView in gridview_main.xml
        // Check for SD Card
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
        } else {
            // Locate the image folder in your SD Card
            file = new File(Environment.getExternalStorageDirectory() + File.separator + "SDImageTutorial");
            // Create a new folder if no folder named SDImageTutorial exist
            file.mkdirs();
        }

        if (file.isDirectory()) {
            listFile = file.listFiles(IMAGE_FILTER);
            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];
            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }
        }

        Log.e("calling ia", "hello");
        gridview.setAdapter(new ImageAdapter(this, FilePathStrings, FileNameStrings, "sdcard"));

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
                                                    i.putExtra("FilePathStrings", FilePathStrings);
                                                    i.putExtra("FileNameStrings", FileNameStrings);
                                                    i.putExtra("pageName", "sdcard");
                                                    startActivity(i);
                                                } catch (Exception e) {
                                                    Log.e("sdcard", e.getMessage().toString());
                                                } finally {
                                                    finish();
                                                }
                                            }
                                        }

        );
    }


    // array of supported extensions (use a List if you prefer)
    static final String[] EXTENSIONS = new String[]{
            "jpg", "jpeg", "gif", "png", "bmp" // and other formats you need
    };
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
}





