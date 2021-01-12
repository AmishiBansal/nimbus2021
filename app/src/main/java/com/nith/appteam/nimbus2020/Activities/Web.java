package com.nith.appteam.nimbus2020.Activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.nith.appteam.nimbus2020.R;

public class Web extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        if (url == null || url.isEmpty()) {
            finish();
            overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
        }
        setContentView(R.layout.activity_web);
        WebView webView = findViewById(R.id.nyc_poi_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }
}
