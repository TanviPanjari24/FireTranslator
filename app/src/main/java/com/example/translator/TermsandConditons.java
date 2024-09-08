package com.example.translator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class TermsandConditons extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_conditons);
        webView = findViewById(R.id.WebView1);

        webView.loadUrl("file:///android_asset/terms.html");
        webView.setBackgroundColor(getResources().getColor(R.color.cream));
    }
}