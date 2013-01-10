package com.greendev.ldlmarketing;

/**
 * Known Bugs:
 *    - (FIXED) NetworkOnMainThreadException throw when thread isn't handled.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.greendev.flickr.FetchSetsTask;
import com.greendev.flickr.FlickrLibrary;
import com.greendev.flickr.FlickrPhoto;
import com.greendev.flickr.FlickrSet;
import com.greendev.flickr.FlickrSetsLibrary;
import com.greendev.image.ImageGridActivity;

public class PortfolioActivity extends LDLActivity implements OnClickListener {

	private final String log_tag = "PortfolioActivity";
	boolean badService = false;
	private FlickrSetsLibrary lib;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.portfolio_layout);

		// Buttons
		View campaignsButton = findViewById(R.id.campaigns_button);
		campaignsButton.setOnClickListener(this);
		((TextView) campaignsButton).setTypeface(fontreg);

		View pressButton = findViewById(R.id.press_button);
		pressButton.setOnClickListener(this);
		((TextView) pressButton).setTypeface(fontreg);

		View gdButton = findViewById(R.id.graphic_design_button);
		gdButton.setOnClickListener(this);
		((TextView) gdButton).setTypeface(fontreg);

		View websitesButton = findViewById(R.id.websites_button);
		websitesButton.setOnClickListener(this);
		((TextView) websitesButton).setTypeface(fontreg);

		View dmButton = findViewById(R.id.digital_marketing_button);
		dmButton.setOnClickListener(this);
		((TextView) dmButton).setTypeface(fontreg);

		View packButton = findViewById(R.id.packaging_button);
		packButton.setOnClickListener(this);
		((TextView) packButton).setTypeface(fontreg);

		View boothButton = findViewById(R.id.booth_designs_button);
		boothButton.setOnClickListener(this);
		((TextView) boothButton).setTypeface(fontreg);

	}
	
	@Override
	public void onClick(View v) {
		/* internert service check */
		Context context = getApplicationContext();
		CharSequence noServiceText = "No internet connection.";
		CharSequence badServiceText = "Please wait a moment and try again.";
		int duration = Toast.LENGTH_LONG;
		Toast toast1 = Toast.makeText(context, noServiceText, duration);
		Toast toast2 = Toast.makeText(context, badServiceText, duration);
		badService = false;

		try {
			Intent i = new Intent(this, ImageGridActivity.class);
			Bundle b = new Bundle();

			switch (v.getId()) {
			case R.id.campaigns_button:
				b.putStringArray("TYPE_URL", lib.campImgs);
				b.putStringArray("TYPE_URL_THUMB", lib.campThumbs);
				// test
				b.putStringArray("CAPTIONS", lib.campDesc);
				b.putString("TITLE", "Campaigns");
				// throw toast if user has low service.
				checkThumbs(lib.campThumbs);

				break;
			case R.id.press_button:
				b.putStringArray("TYPE_URL", lib.pressImgs);
				b.putStringArray("TYPE_URL_THUMB", lib.pressThumbs);
				// test
				b.putStringArray("CAPTIONS", lib.pressDesc);
				b.putString("TITLE", "Press");
				// throw toast if user has low service.
				checkThumbs(lib.pressThumbs);
				break;
			case R.id.graphic_design_button:
				b.putStringArray("TYPE_URL", lib.gdImgs);
				b.putStringArray("TYPE_URL_THUMB", lib.gdThumbs);
				// test
				b.putStringArray("CAPTIONS", lib.gdDesc);
				b.putString("TITLE", "Graphic Designs");
				// throw toast if user has low service.
				checkThumbs(lib.gdThumbs);
				break;
			case R.id.websites_button:
				b.putStringArray("TYPE_URL", lib.webImgs);
				b.putStringArray("TYPE_URL_THUMB", lib.webThumbs);
				// test
				b.putStringArray("CAPTIONS", lib.webDesc);
				b.putString("TITLE", "Websites");
				// throw toast if user has low service.
				checkThumbs(lib.webThumbs);
				break;
			case R.id.digital_marketing_button:
				b.putStringArray("TYPE_URL", lib.dmImgs);
				b.putStringArray("TYPE_URL_THUMB", lib.dmThumbs);
				// test
				b.putStringArray("CAPTIONS", lib.dmDesc);
				b.putString("TITLE", "Digital Marketing");
				// throw toast if user has low service.
				// throw toast if user has low service.
				checkThumbs(lib.dmThumbs);
				break;
			case R.id.packaging_button:
				b.putStringArray("TYPE_URL", lib.packImgs);
				b.putStringArray("TYPE_URL_THUMB", lib.packThumbs);
				// test
				b.putStringArray("CAPTIONS", lib.packDesc);
				b.putString("TITLE", "Packaging");
				// throw toast if user has low service.
				checkThumbs(lib.packThumbs);
				break;
			case R.id.booth_designs_button:
				b.putStringArray("TYPE_URL", lib.boothImgs);
				b.putStringArray("TYPE_URL_THUMB", lib.boothThumbs);
				// test
				b.putStringArray("CAPTIONS", lib.boothDesc);
				b.putString("TITLE", "Booth Designs");

				// throw toast if user has low service.
				checkThumbs(lib.boothThumbs);
				break;
			}
			i.putExtras(b);

			if (badService == true) {
				toast1.show();
			} else {
				startActivity(i);
			}

		} catch (Exception e) {
			toast1.show();
			Log.e("PORTFOLIOACTIVITY", "can't start activity ImageGridActivity");
		}
		;

	}

	private void checkThumbs(String[] thumbs) {
		// throw toast if user has low service.
		if (thumbs == null) {
			Log.i("PortfolioActivity", thumbs.toString()
					+ " thumbails are null");
			badService = true;
		} else
			badService = false;
	}

}
