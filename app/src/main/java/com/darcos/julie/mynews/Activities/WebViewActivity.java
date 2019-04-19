package com.darcos.julie.mynews.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.darcos.julie.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * this class use when user click on article and open a webview for read article
 */
public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.activity_webview)
    Toolbar toolbarweb;
    @BindView(R.id.activity_main_webview)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        this.configureToolBar();

        webView.loadUrl(getIntent().getStringExtra("url"));
    }

    //configure toolbar
    private void configureToolBar() {
        setSupportActionBar(toolbarweb);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getIntent().getStringExtra("title"));
    }

    //button return
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(WebViewActivity.this, MainActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
