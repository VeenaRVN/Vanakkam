
package com.vanakkam.rvnve.vanakkam;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    public Integer[] mThumbIds;
    public String pageNameFromClass;
    Context c;
    public String[] filepath;
    public String[] filename;
    ImageView imageView;
    Bitmap m_d;

    // references to our birthday images
    public Integer[] mBirthdayThumbIds = {
            R.drawable.birth1, R.drawable.birth2,R.drawable.birth3, R.drawable.birth4,R.drawable.birth5,
            R.drawable.birth6, R.drawable.birth7,R.drawable.birth8, R.drawable.birth9,R.drawable.birth10
    };

    // references to our wedding images
    public Integer[] mWeddingThumbIds = {
            R.drawable.wedding, R.drawable.wedding2, R.drawable.wedding3, R.drawable.wedding4, R.drawable.wedding5,
            R.drawable.wedding6, R.drawable.wedding7, R.drawable.wedding8, R.drawable.wedding9, R.drawable.wedding10
    };

    // references to our christmas images
    public Integer[] mChristmasThumbIds = {
            R.drawable.christmas1, R.drawable.christmas2, R.drawable.christmas3, R.drawable.christmas4, R.drawable.christmas5,
            R.drawable.christmas6, R.drawable.christmas7, R.drawable.christmas8, R.drawable.christmas9, R.drawable.christmas10
    };


    public ImageAdapter(Context c, String pageName) {
        this.mContext = c;
        pageNameFromClass = pageName;
        Log.e("page", pageName);

        switch (pageName) {
            case "birthday":
                mThumbIds = mBirthdayThumbIds;
                break;
            case "christmas":
                mThumbIds = mChristmasThumbIds;
                break;
            case "wedding":
                mThumbIds = mWeddingThumbIds;
                break;
        }
    }

    public ImageAdapter(Context c, String[] fpath, String[] fname, String pageName) {
        this.mContext = c;
        pageNameFromClass = pageName;
        Log.e("page-sdcard", pageName);
        filepath = fpath;
        filename = fname;
    }


    public int getCount() {
        int cnt;
        switch (pageNameFromClass) {
            case "sdcard":
                cnt = filepath.length;
                break;
            default:
                cnt = mThumbIds.length;
                break;
        }
        return cnt;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            Log.e("Getview:log", mContext.toString());
        } else {
            imageView = (ImageView) convertView;
        }


        if (pageNameFromClass.equals("sdcard"))
        {
            // Decode the filepath with BitmapFactory followed by the position
            m_d = BitmapFactory.decodeFile(filepath[position]);
            //imageView.setImageBitmap(m_d);
        }
      else
        {
            //imageView.setImageResource(mThumbIds[position]);
            //Decoding the image size so as to occupy less memory space
            m_d = BitmapFactory.decodeResource(mContext.getResources(), mThumbIds[position]);
        }

        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setPadding(8, 8, 8, 8);


        if (m_d != null)
        {
            try {
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(m_d, 500, 500, true);
                imageView.setImageBitmap(resizedBitmap);
            }
            catch (Exception e)
            {
                Log.e("resizing image",e.getStackTrace().toString());
            }
        }
        return imageView;
    }

}







