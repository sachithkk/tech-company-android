package com.example.sachith.tech_app_front;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class CompanyWebSite extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.company_website);

        Intent intent = getIntent();
        String webSite = intent.getStringExtra(ViewSingleCompany.WEB_SITE);
        toastMessage("http://"+webSite);

        webView = (WebView) findViewById(R.id.company_webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://" + webSite);

    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
