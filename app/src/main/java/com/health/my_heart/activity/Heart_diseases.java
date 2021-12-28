package com.health.my_heart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.health.my_heart.R;

public class Heart_diseases extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_diseases);

        WebView webview = (WebView) findViewById(R.id.webview1);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl("file:///android_asset/pdf.html");

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView3);
        imageView.setImage(ImageSource.resource(R.drawable.img));
    }
}