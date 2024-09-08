package com.example.translator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class AboutUs extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        webView = findViewById(R.id.WebView1);

        webView.loadUrl("file:///android_asset/aboutUs.html");
        webView.setBackgroundColor(getResources().getColor(R.color.cream));
    }
}