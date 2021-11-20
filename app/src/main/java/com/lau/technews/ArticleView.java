package com.lau.technews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class ArticleView extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "URL OF ARTICLE";
    public static final String EXTRA_MESSAGE1 = "TITLE OF ARTICLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        Intent intent = getIntent();

        String url = intent.getStringExtra(EXTRA_MESSAGE);
        String title = intent.getStringExtra(EXTRA_MESSAGE1);

        TextView text = (TextView) findViewById(R.id.textView);

        text.setText(title);

        WebView view = findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient());
        view.loadUrl(url);
    }
}