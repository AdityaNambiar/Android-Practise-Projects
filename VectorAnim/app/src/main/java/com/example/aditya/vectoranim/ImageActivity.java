package com.example.aditya.vectoranim;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;

import com.example.aditya.vectoranim.MessageActivity;
import com.example.aditya.vectoranim.R;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        setEnterTrans();
        callMsgAct();
    }

    private void callMsgAct() {
        Intent finalstage = new Intent(ImageActivity.this, MessageActivity.class);
        finalstage.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(finalstage);
    }

    private void setEnterTrans() {
        Fade fade = new Fade();
        fade.setDuration(6000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(fade);
        }
    }
}
