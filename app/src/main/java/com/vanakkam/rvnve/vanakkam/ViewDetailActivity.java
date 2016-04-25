package com.vanakkam.rvnve.vanakkam;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.print.PrintHelper;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class ViewDetailActivity extends Activity {

    Button btnAddText, btnSendImage, btnPrintImage, btnDone, btnReset;
    ImageView imageViewCard;
    EditText textDetail;
    Bitmap scaledBitmap;
    int position;
    ImageAdapter imageAdapter;
    String imgPageName;
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdetail);

        // get intent data
        Intent i = getIntent();

        // Selected image id and pagename
        position = i.getExtras().getInt("id");
        imgPageName=i.getExtras().getString("pageName");
        FilePathStrings=i.getExtras().getStringArray("FilePathStrings");
        FileNameStrings=i.getExtras().getStringArray("FileNameStrings");

        Log.e("vd -pgname","-"+imgPageName+"-");

        if (imgPageName.equals("sdcard"))
        {
            Log.e("vd","in if");
            imageAdapter=new ImageAdapter(this, FilePathStrings, FileNameStrings,"sdcard");
        }
      else {
            Log.e("vd","in else");
            imageAdapter = new ImageAdapter(this,imgPageName);
        }

        Log.e("vd",imageAdapter.toString());

        btnAddText = (Button) findViewById(R.id.btnAddText);
        btnSendImage = (Button) findViewById(R.id.btnSendImage);
        btnPrintImage = (Button) findViewById(R.id.btnPrintImage);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnReset = (Button) findViewById(R.id.btnResetImage);

        textDetail = (EditText) findViewById(R.id.textDetail);
        imageViewCard = (ImageView) findViewById(R.id.imgDetail);

        //Get the default machine display size
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        //Determine the size of the current image
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inMutable=true;

        if (imgPageName.equals("sdcard"))
        {
            //Decoding the image size so as to occupy less memory space
            BitmapFactory.decodeFile(FilePathStrings[position], options);
        } else
        {
            // Decode the filepath with BitmapFactory followed by the position
            BitmapFactory.decodeResource(getResources(), imageAdapter.mThumbIds[position], options);

        }

        //BitmapFactory.decodeResource(getResources(), imageAdapter.mThumbIds[position], options);

        int width = options.outWidth;
        int height = options.outHeight;

        //Get the expected image size from the display size and actual image size
        if (width > screenWidth) {
            int widthRatio = Math.round((float) width / (float) screenWidth);
            options.inSampleSize = widthRatio;
        }
        if (height > screenHeight) {
            int heightRatio = Math.round((float) height / (float) screenHeight);
            options.inSampleSize = heightRatio;
        }

        //load the scaled image to the image view using the ratio calculated
        options.inJustDecodeBounds = false;
        //setting this helps to load the image on the canvas in future
        options.inMutable = true;


        if (imgPageName.equals("sdcard"))
        {
            // Decode the filepath with BitmapFactory followed by the position
            Log.e("vd",FilePathStrings[position].toString());
            scaledBitmap = BitmapFactory.decodeFile(FilePathStrings[position], options);

        }
        else {
            //imageView.setImageResource(mThumbIds[position]);
            //Decoding the image size so as to occupy less memory space
            Log.e("vd",imageAdapter.mThumbIds[position].toString());
            scaledBitmap = BitmapFactory.decodeResource(getResources(), imageAdapter.mThumbIds[position], options);
        }

        options.inMutable=true;
        imageViewCard.setImageBitmap(scaledBitmap);


        //Add Text button on click event
        btnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Load the scaled image to canvas
                Canvas canvas = new Canvas(scaledBitmap);
                float scale = getResources().getDisplayMetrics().density;
                String textFinal = textDetail.getText().toString();

                // set text width to canvas width minus 16dp padding
                int textWidth = canvas.getWidth() - (int) (16 * scale);
                // new antialiased Paint
                TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
                // text color - #3D3D3D
                paint.setColor(Color.rgb(61, 61, 61));
                // text size in pixels
                paint.setTextSize((int) (14 * scale));
                // text shadow
                paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

                // StaticLayout for text
                StaticLayout textLayout = new StaticLayout(textFinal, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

                // get height of multiline text
                int textHeight = textLayout.getHeight();

                // get position of text's top left corner
                float x = (scaledBitmap.getWidth() - textWidth)/2;
                float y = (scaledBitmap.getHeight() - textHeight)/2;

                // draw text to the Canvas center
                canvas.save();
                canvas.translate(x, y);
                textLayout.draw(canvas);
                canvas.restore();


            }
        });


        //Add Send Image on click event
        btnSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    // Get access to bitmap image from view
                    ImageView ivImage = (ImageView) findViewById(R.id.imgDetail);
                    // Get access to the URI for the bitmap
                    Intent shareIntent = new Intent();
                    Uri bmpUri = getLocalBitmapUri(ivImage);
                    Log.e("bmpUri", bmpUri.toString());

                    if (bmpUri != null) {
                        // Construct a ShareIntent with link to image
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        shareIntent.setType("image/*");
                        // Launch sharing dialog for image
                        startActivity(Intent.createChooser(shareIntent, "Share Image"));
                        Log.e("Started Sharing", shareIntent.toString());
                    } else {
                        shareIntent.setDataAndType(null, "");
                        ViewDetailActivity.this.setResult(RESULT_CANCELED, shareIntent);
                    }

                } catch (Exception e) {
                    Log.e("Send Image Error", e.getMessage().toString());
                } finally {
                    finish();
                    ;
                }
            }
        });

        //print image button onclick event
        btnPrintImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Started printer", "printer");
                // Get the print manager
                PrintHelper photoPrinter = new PrintHelper(ViewDetailActivity.this);
                // Set the desired scale mode.
                photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                // Get the bitmap for the ImageView's drawable.
                Bitmap bitmap = ((BitmapDrawable) imageViewCard.getDrawable()).getBitmap();

                // Print the bitmap
                photoPrinter.printBitmap("Print Bitmap", bitmap);
                Toast.makeText(getBaseContext(), "Printing Completed", Toast.LENGTH_LONG).show();
            }
        });

        //done editing button onclick event
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //finish this page
                finish();
                Intent refresh = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(refresh);


            }
        });

        //Reload button onclick event
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        verifyStoragePermissions(ViewDetailActivity.this);

        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.

            Log.e("Dir Pic:", Environment.DIRECTORY_DOWNLOADS);

            File file = new File(getExternalFilesDir( Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.setReadable(true);
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);

        } catch (IOException e) {
            Log.e("getLocalBitmapUri", e.getMessage().toString());
        }
        return bmpUri;
    }

    //Destroy Method
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

}



