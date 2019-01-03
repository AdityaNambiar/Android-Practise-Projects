package com.example.aditya.vectoranim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    FrameLayout mainContentWrap;
    LinearLayout maleRightHand, femaleLeftHand, giftBox, mHandAbsent, fHandAbsent,
                 maleBody, femaleBody, giftLarge, giftOpen;
    ImageView mBody, fBody, m_rHand_iv, f_lHand_iv, giftimg;
    Handler mHand, fHand, giftMove, postGiftPass, enlargeGift, openGift, transition;
    ObjectAnimator maleT,postmaleT,femaleT, giftT, giftExtraT,
                   mHandRotate, fHandRotate, fHandRotate2;
    AnimatorSet mAndGift, fAndGift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainContentWrap = (FrameLayout) findViewById(R.id.mainContentWrap);

        maleRightHand = (LinearLayout) findViewById(R.id.imgAnim);
        femaleLeftHand = (LinearLayout) findViewById(R.id.imgAnim2);
        giftBox = (LinearLayout) findViewById(R.id.gift);
        mHandAbsent = (LinearLayout) findViewById(R.id.maleWithoutHand);
        fHandAbsent = (LinearLayout) findViewById(R.id.femaleWithoutHand);
        maleBody = (LinearLayout) findViewById(R.id.maleBody);
        femaleBody = (LinearLayout) findViewById(R.id.femaleBody);
        giftLarge = (LinearLayout) findViewById(R.id.giftLarge);
        giftOpen = (LinearLayout) findViewById(R.id.giftopen);



        mBody = (ImageView) findViewById(R.id.body);
        fBody = (ImageView) findViewById(R.id.fbody);
        m_rHand_iv = (ImageView) findViewById(R.id.m_rHand_iv);
        f_lHand_iv = (ImageView) findViewById(R.id.f_lHand_iv);
        giftimg = (ImageView) findViewById(R.id.giftiv);

        entryAnim();
        handMovements();
        giftPass();
        giftAccept();
    }

    private void entryAnim() {
        mainContentWrap.setVisibility(View.VISIBLE);
        giftBox.setVisibility(View.VISIBLE);
        mAndGift = new AnimatorSet();

        maleT = ObjectAnimator.ofFloat(mBody,"translationX",-1000,0);
        giftT = ObjectAnimator.ofFloat(giftimg,"translationX",-1000,0);
        mAndGift.playTogether(maleT,giftT);
        mAndGift.setDuration(2000);
        mAndGift.start();

        femaleT = ObjectAnimator.ofFloat(fBody,"translationX",1000,0);
        femaleT.setDuration(2000);
        femaleT.start();


    }

    private void handMovements() {
        mHand = new Handler();
        mHand.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Bringing up the male vector without hand...
                mHandAbsent.setVisibility(View.VISIBLE);
                // ...to replace the male vector with hand.
                maleBody.setVisibility(View.GONE);

                // Bring up the male right hand and apply rotation:
                maleRightHand.setVisibility(View.VISIBLE);
                mHandRotate = ObjectAnimator.ofFloat(maleRightHand,"rotation",0,-30);
                mHandRotate.setDuration(2000);
                mHandRotate.start();
            }
        },2500); /* means this Runnable instance will be
     executed after 500ms of the first animation (i.e. entryAnim()'s animation which is of 2000ms */

        fHand = new Handler();
        fHand.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Bringing up the female vector without hand...
                fHandAbsent.setVisibility(View.VISIBLE);
                // ...to replace the male vector with hand.
                femaleBody.setVisibility(View.GONE);

                // Bring up the female right hand and apply rotation:
                femaleLeftHand.setVisibility(View.VISIBLE);

                fHandRotate = ObjectAnimator.ofFloat(femaleLeftHand,"rotation",0,25);
                fHandRotate.setDuration(2000);
                fHandRotate.start();
            }
        },3500);
    }

    private void giftPass() {
        giftMove = new Handler();
        giftMove.postDelayed(new Runnable() {
            @Override
            public void run() {
                giftBox.setVisibility(View.VISIBLE);
                giftT = ObjectAnimator.ofFloat(giftBox,"translationX",0,28);
                giftT.setDuration(2000);
                giftT.start();
            }
        },2600); // Start 100ms late to the male hand rotation so it's "Intensely" ( ͡° ͜ʖ ͡°)
    }

    private void giftAccept() {
        postGiftPass = new Handler();
        postGiftPass.postDelayed(new Runnable() {
            @Override
            public void run() {
                fAndGift = new AnimatorSet();
                postmaleT = ObjectAnimator.ofFloat(maleRightHand,"rotation",-30,0);
                giftExtraT = ObjectAnimator.ofFloat(giftBox,"translationX",28,108);
                fHandRotate2 = ObjectAnimator.ofFloat(femaleLeftHand, "rotation", 25, 0, -20);
                fAndGift.playTogether(postmaleT,giftExtraT,fHandRotate2);
                fAndGift.setDuration(2000);
                fAndGift.start();
            }
        },4000); // After the female hand goes (rotates) for accepting the gift.

        // THIS IS WHERE THE OPTIONAL THINGS WERE HAPPENING AFTER ALL HUMAN ANIMATIONS:
        enlargeGift = new Handler();
        enlargeGift.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Set all human vectors as "gone" ...
                maleRightHand.setVisibility(View.GONE);
                femaleLeftHand.setVisibility(View.GONE);
                giftBox.setVisibility(View.GONE);
                mHandAbsent.setVisibility(View.GONE);
                fHandAbsent.setVisibility(View.GONE);
                maleBody.setVisibility(View.GONE);
                femaleBody.setVisibility(View.GONE);

                // ... and only keep the large gift visible and then the open gift:
                giftLarge.setVisibility(View.VISIBLE);
            }
        },6500);

        openGift = new Handler();
        openGift.postDelayed(new Runnable() {
            @Override
            public void run() {
                giftLarge.setVisibility(View.GONE); // hide wrapped gift image ...
                Fade fade = new Fade();
                fade.setDuration(2000);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setEnterTransition(fade);
                }
                giftOpen.setVisibility(View.VISIBLE); // ... and display unwrapped gift
            }
        },7000);

        transition = new Handler();
        transition.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent prefinalstage = new Intent(MainActivity.this,ImageActivity.class);
                startActivity(prefinalstage);
            }
        },7500);
    }

}