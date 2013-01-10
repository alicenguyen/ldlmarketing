package com.greendev.ldlmarketing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);

		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");

		// Texts
		TextView titleText1 = (TextView) findViewById(R.id.home_title1);
		titleText1.setTypeface(font);

		TextView titleText2 = (TextView) findViewById(R.id.home_title2);
		titleText2.setTypeface(font);

		// Buttons
		View aboutButton = findViewById(R.id.button_about);
		aboutButton.setOnClickListener(this);
		((TextView) aboutButton).setTypeface(font);

		View clientDirButton = findViewById(R.id.button_clientdir);
		clientDirButton.setOnClickListener(this);
		((TextView) clientDirButton).setTypeface(font);

		View portfolioButton = findViewById(R.id.button_portfolio);
		portfolioButton.setOnClickListener(this);
		((TextView) portfolioButton).setTypeface(font);

		View servicesButton = findViewById(R.id.button_services);
		servicesButton.setOnClickListener(this);
		((TextView) servicesButton).setTypeface(font);

		View faqsButton = findViewById(R.id.button_faqs);
		faqsButton.setOnClickListener(this);
		((TextView) faqsButton).setTypeface(font);

		View contactUsButton = findViewById(R.id.button_contactus);
		contactUsButton.setOnClickListener(this);
		((TextView) contactUsButton).setTypeface(font);
		
	}

	@Override
	public void onClick(View v) {

		Context context = getApplicationContext();
		CharSequence text = "Oops! We need internet connection to access this page!";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);

		switch (v.getId()) {
		case R.id.button_about:
			Intent i = new Intent(this, AboutActivity.class);
			startActivity(i);
			break;

		case R.id.button_clientdir:
			Intent c = new Intent(this, ClientDirectoryActivity.class);
			startActivity(c);
			break;

		case R.id.button_faqs:
			Intent a = new Intent(this, FAQActivity.class);
			startActivity(a);
			break;

		case R.id.button_portfolio:
			try {
				Intent b = new Intent(this, PortfolioActivity.class);
				startActivity(b);
			} catch (Exception e) {
				toast.show();
				Log.e("HOMEACTIVITY", "portfolio error");
			}
			break;

		case R.id.button_services:
			Intent d = new Intent(this, ServicesActivity.class);
			startActivity(d);
			break;

		case R.id.button_contactus:
			Intent e = new Intent(this, ContactActivity.class);
			startActivity(e);
			break;

		}

	}

	/*
	 * Checks for internet connection
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting();
	}

}
