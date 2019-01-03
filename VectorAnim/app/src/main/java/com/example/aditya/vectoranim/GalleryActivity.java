package com.example.aditya.vectoranim;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.example.aditya.vectoranim.MessageActivity;
import com.example.aditya.vectoranim.R;

public class GalleryActivity extends AppCompatActivity {

    GridView gridView;
    FrameLayout gallery;
    Handler h;
    Runnable activityChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gridView = (GridView) findViewById(R.id.gridView);
        gallery = (FrameLayout) findViewById(R.id.gallery);


        final com.example.aditya.vectoranim.ImageAdapter adapter = new com.example.aditya.vectoranim.ImageAdapter(this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        // Enter Transitiom:
        setEnterAnim();
    }

    private void setEnterAnim() {

       gallery.post(new Runnable() {
            @Override
            public void run() {

                int cx = (gallery.getLeft() + gallery.getRight()) / 2;
                int cy = (gallery.getTop()+gallery.getBottom())/2;
                int finalRadius = Math.max(gallery.getWidth(), gallery.getHeight());

                Animator anim = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    anim = ViewAnimationUtils.createCircularReveal(gallery, cx, cy, 0, finalRadius);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    gallery.setBackgroundColor(getResources().getColor(R.color.PINK, getTheme()));
                }
                anim.setDuration(800);
                anim.start();

            }
        });
        h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Fade enter = new Fade();
                enter.setDuration(3000);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setEnterTransition(enter);
                }
            }
        },1600);

        activityChange = new Runnable() {
            @Override
            public void run() {

                    Intent intent = new Intent(GalleryActivity.this, MessageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    startActivity(intent);
            };

        };

        h = new Handler();
        h.postDelayed(activityChange,3600);
    }

}
