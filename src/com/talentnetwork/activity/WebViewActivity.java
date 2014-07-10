package com.talentnetwork.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

//import com.homilychart.info.R;
//import com.homilychart.info.util.ActivityManagerUtil;

public class WebViewActivity extends Activity {

	private String url;

	private WebView webview;
	
	private  ProgressBar loading ;
	
	private Button back ;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		webview = (WebView) findViewById(R.id.webview);
		// 将该Activity加入ActivityManger集合里
//		ActivityManagerUtil.getInstance().addActivity(this);

		 loading = (ProgressBar) findViewById(R.id.loading);

		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		webview.loadUrl(url);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);

				loading.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				loading.setVisibility(View.GONE);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webview.loadUrl(url);
				return true;
			}
		});

		// back
//		back = (Button) findViewById(R.id.webback);
//		back.setVisibility(View.VISIBLE);
//		back.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
	}

}
