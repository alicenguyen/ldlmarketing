package com.greendev.ldlmarketing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PortfolioActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Custom Font
		Typeface fontb = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");

		// Custom title bar
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.portfolio_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		TextView title = (TextView) findViewById(R.id.title);
		title.setTypeface(fontb);
		title.setText("Portfolio");

		// Buttons
		View campaignsButton = findViewById(R.id.campaigns_button);
		campaignsButton.setOnClickListener(this);
		((TextView) campaignsButton).setTypeface(font);

		View pressButton = findViewById(R.id.press_button);
		pressButton.setOnClickListener(this);
		((TextView) pressButton).setTypeface(font);

		View gdButton = findViewById(R.id.graphic_design_button);
		gdButton.setOnClickListener(this);
		((TextView) gdButton).setTypeface(font);

		View websitesButton = findViewById(R.id.websites_button);
		websitesButton.setOnClickListener(this);
		((TextView) websitesButton).setTypeface(font);

		View videosButton = findViewById(R.id.videos_button);
		videosButton.setOnClickListener(this);
		((TextView) videosButton).setTypeface(font);

		View dmButton = findViewById(R.id.digital_marketing_button);
		dmButton.setOnClickListener(this);
		((TextView) dmButton).setTypeface(font);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.graphic_design_button:
			Intent i = new Intent(this, ImageGridActivity.class);
			startActivity(i);
			break;

	/*	case R.id.button_clientdir:
			Intent c = new Intent(this, ClientDirectoryActivity.class);
			startActivity(c);
			break;

		case R.id.button_faqs:
			Intent a = new Intent(this, FAQSActivity.class);
			startActivity(a);
			break;

		case R.id.button_portfolio:
			Intent b = new Intent(this, PortfolioActivity.class);
			startActivity(b);
			break;

		case R.id.button_services:
			Intent d = new Intent(this, ServicesActivity.class);
			startActivity(d);
			break;

		case R.id.button_contactus:
			Intent e = new Intent(this, ContactActivity.class);
			startActivity(e);
			break;*/
		}

	}

}
