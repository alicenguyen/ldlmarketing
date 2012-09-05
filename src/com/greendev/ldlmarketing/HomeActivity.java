package com.greendev.ldlmarketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HomeActivity extends Activity implements OnClickListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		

		View aboutButton = findViewById(R.id.button_about);
		aboutButton.setOnClickListener(this);

		View clientDirButton = findViewById(R.id.button_clientdir);
		clientDirButton.setOnClickListener(this);

		View portfolioButton = findViewById(R.id.button_portfolio);
		portfolioButton.setOnClickListener(this);

		View servicesButton = findViewById(R.id.button_services);
		servicesButton.setOnClickListener(this);

		View faqsButton = findViewById(R.id.button_faqs);
		faqsButton.setOnClickListener(this);

		View contactUsButton = findViewById(R.id.button_contactus);
		contactUsButton.setOnClickListener(this);
	}

	public void onClick(View v) {
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
			break;

		}
		
	}

}
