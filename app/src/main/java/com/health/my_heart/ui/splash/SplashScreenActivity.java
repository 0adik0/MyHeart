package com.health.my_heart.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.health.my_heart.R;
import com.health.my_heart.ui.LaunchActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private boolean isFirstAnimation = false;
    int SPLASH_TIME = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //==============================

        TextView textView1  = findViewById(R.id.textView2);
        ImageView imageView = findViewById(R.id.heart_icon);


        Animation fadeIn = new AlphaAnimation(-2, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(4000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        textView1.setAnimation(animation);

        //==============================
        Animation hold = AnimationUtils.loadAnimation(this, R.anim.hold);
        final Animation translateScale = AnimationUtils.loadAnimation(this, R.anim.translate_scale);
        final TextView textView = findViewById(R.id.textView);
        //==============================


        translateScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isFirstAnimation) {
                    textView.clearAnimation();
                }

                isFirstAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hold.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.clearAnimation();
                textView.startAnimation(translateScale);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //==============================

        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_img);
        imageView.startAnimation(animation2);

        //==============================


        new Handler().postDelayed(() -> {
            Intent mySuperIntent = new Intent(SplashScreenActivity.this, LaunchActivity.class);
            startActivity(mySuperIntent);

            finish();

        }, SPLASH_TIME);

        //==============================

        textView.startAnimation(hold);
    }

}
