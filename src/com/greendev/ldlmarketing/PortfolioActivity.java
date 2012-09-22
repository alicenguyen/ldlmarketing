package com.greendev.ldlmarketing;

import com.greendev.image.ImageGridActivity;
import com.greendev.image.Images;

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

		View dmButton = findViewById(R.id.digital_marketing_button);
		dmButton.setOnClickListener(this);
		((TextView) dmButton).setTypeface(font);

		View packButton = findViewById(R.id.packaging_button);
		packButton.setOnClickListener(this);
		((TextView) packButton).setTypeface(font);

		View boothButton = findViewById(R.id.booth_designs_button);
		boothButton.setOnClickListener(this);
		((TextView) boothButton).setTypeface(font);
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(this, ImageGridActivity.class);
		Bundle b = new Bundle();

		switch (v.getId()) {
		case R.id.campaigns_button:
			b.putStringArray("TYPE_URL", Images.campaignsImageUrls);
			b.putStringArray("TYPE_URL_THUMB", Images.campaignsThumbUrls);
			b.putString("TITLE", "Campaigns");
			break;
		case R.id.press_button:
			b.putStringArray("TYPE_URL", Images.pressImageUrls);
			b.putStringArray("TYPE_URL_THUMB", Images.pressThumbUrls);
			b.putString("TITLE", "Press");
			break;
		case R.id.graphic_design_button:
			b.putStringArray("TYPE_URL", Images.graphicDesignsImageUrls);
			b.putStringArray("TYPE_URL_THUMB", Images.graphicDesignsThumbUrls);
			b.putString("TITLE", "Graphic Designs");
			break;
		case R.id.websites_button:
			b.putStringArray("TYPE_URL", Images.websitesImageUrls);
			b.putStringArray("TYPE_URL_THUMB", Images.websitesThumbUrls);
			b.putString("TITLE", "Websites");
			break;
		case R.id.digital_marketing_button:
			b.putStringArray("TYPE_URL", Images.digitalMarketingImageUrls);
			b.putStringArray("TYPE_URL_THUMB", Images.digitalMarketingThumbUrls);
			b.putString("TITLE", "Digital Marketing");
			break;
		case R.id.packaging_button:
			b.putStringArray("TYPE_URL", Images.packagingImageUrls);
			b.putStringArray("TYPE_URL_THUMB", Images.packagingThumbUrls);
			b.putString("TITLE", "Packaging");
			break;
		case R.id.booth_designs_button:
			b.putStringArray("TYPE_URL", Images.boothDesignsImageUrls);
			b.putStringArray("TYPE_URL_THUMB", Images.boothDesignsThumbUrls);
			b.putString("TITLE", "Booth Designs");
			break;

		}
		i.putExtras(b);
		startActivity(i);

	}

}
