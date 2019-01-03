package com.example.aditya.vectoranim;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.aditya.vectoranim.R;


/**
 * Created by Aditya on 10-Jun-18.
 */

public class ImageAdapter extends BaseAdapter {

    Context cxt;

    public int[] images = {
            R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4
    };

    public ImageAdapter(Context cxt){
        this.cxt = cxt;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgView = new ImageView(cxt);
        imgView.setImageResource(images[position]);
        imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imgView.setLayoutParams(new GridView.LayoutParams(240,240));
        return imgView;
    }
}
