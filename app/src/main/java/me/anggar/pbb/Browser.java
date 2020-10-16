package me.anggar.pbb;

import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Browser extends AppCompatActivity implements View.OnClickListener {

    private WebView canvas;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        canvas = findViewById(R.id.wv_content);
        input = findViewById(R.id.et_url);

        canvas.setWebViewClient(new LocalBrowser());

        Button btn_go = findViewById(R.id.btn_go);
        btn_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        canvas.getSettings().setJavaScriptEnabled(true);
        canvas.loadUrl(input.getText().toString());
    }

    private static class LocalBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}