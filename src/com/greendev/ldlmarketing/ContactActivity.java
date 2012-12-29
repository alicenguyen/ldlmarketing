package com.greendev.ldlmarketing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ContactActivity extends LDLActivity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactus_layout);

		View fbButton = findViewById(R.id.fb_button);
		fbButton.setOnClickListener(this);

		View twButton = findViewById(R.id.twitter_button);
		twButton.setOnClickListener(this);

		View blogButton = findViewById(R.id.blogspot_button);
		blogButton.setOnClickListener(this);

		View ytButton = findViewById(R.id.youtube_button);
		ytButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fb_button:
			Intent viewFb = new Intent("android.intent.action.VIEW",
					Uri.parse("https://m.facebook.com/LDLMarketingServicesInc"));
			startActivity(viewFb);
			break;

		case R.id.twitter_button:
			Intent viewTwit = new Intent("android.intent.action.VIEW",
					Uri.parse("https://mobile.twitter.com/LDLmarketingINC"));
			startActivity(viewTwit);
			break;

		case R.id.blogspot_button:
			Intent viewBs = new Intent("android.intent.action.VIEW",
					Uri.parse("http://www.ldlmarketing.blogspot.com/"));
			startActivity(viewBs);
			break;

		case R.id.youtube_button:
			Intent viewYt = new Intent("android.intent.action.VIEW",
					Uri.parse("http://m.youtube.com/user/LDLmarketing"));
			startActivity(viewYt);
			break;
		}
	}

}
