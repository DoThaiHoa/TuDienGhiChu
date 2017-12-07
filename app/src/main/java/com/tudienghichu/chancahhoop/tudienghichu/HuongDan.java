package com.tudienghichu.chancahhoop.tudienghichu;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class HuongDan extends AppCompatActivity {
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huong_dan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebView webView =(WebView)findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/index.html");



    }
}
