package com.health.my_heart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.health.my_heart.R;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView31);
        imageView.setImage(ImageSource.resource(R.drawable.food1));
        SubsamplingScaleImageView imageView1 = (SubsamplingScaleImageView)findViewById(R.id.imageView4);
        imageView1.setImage(ImageSource.resource(R.drawable.food2));
        SubsamplingScaleImageView imageView2 = (SubsamplingScaleImageView)findViewById(R.id.imageView5);
        imageView2.setImage(ImageSource.resource(R.drawable.food3));
        SubsamplingScaleImageView imageView3 = (SubsamplingScaleImageView)findViewById(R.id.imageView6);
        imageView3.setImage(ImageSource.resource(R.drawable.food4));
        SubsamplingScaleImageView imageView4 = (SubsamplingScaleImageView)findViewById(R.id.imageView7);
        imageView4.setImage(ImageSource.resource(R.drawable.food5));
        SubsamplingScaleImageView imageView5 = (SubsamplingScaleImageView)findViewById(R.id.imageView8);
        imageView5.setImage(ImageSource.resource(R.drawable.food6));
        SubsamplingScaleImageView imageView6 = (SubsamplingScaleImageView)findViewById(R.id.imageView9);
        imageView6.setImage(ImageSource.resource(R.drawable.food7));

    }
}