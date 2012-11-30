package com.greendev.ldlmarketing;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class TwitterActivity2 extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter2_layout);
		
		WebView webView = (WebView) findViewById(R.id.mobile_twitter);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");
		webView.loadUrl("https://mobile.twitter.com/LDLmarketingINC");
	}
}