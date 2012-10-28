package com.greendev.ldlmarketing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.greendev.flickr.FetchSetsTask;
import com.greendev.flickr.FlickrLibrary;
import com.greendev.flickr.FlickrPhoto;
import com.greendev.flickr.FetchPhotosTask;
import com.greendev.flickr.FlickrSet;
import com.greendev.image.ImageGridActivity;
import com.greendev.image.Images;

public class PortfolioActivity extends Activity implements OnClickListener {

	public String[] campImgs, campThumbs, campDesc;
	public String[] pressImgs, pressThumbs, pressDesc;
	public String[] gdImgs, gdThumbs, gdDesc;
	public String[] webImgs, webThumbs, webDesc;
	public String[] dmImgs, dmThumbs, dmDesc;
	public String[] packImgs, packThumbs, packDesc;
	public String[] boothImgs, boothThumbs, boothDesc;

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

		// Start fetching sets from Flickr
		new Thread(new FetchSetsTask(responseHandler)).start();
	}

	Handler response = new Handler() {
	};

	Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			createURLSets(msg);
		};
	};

	private void createURLSets(Message msg) {
		// Get library from the responseHandler that was generated above.
		FlickrLibrary lib = (FlickrLibrary) msg.getData().get(
				FetchSetsTask.LIBRARY);
		FlickrSet[] fSets = lib.fetchSets();
		for(int i = 0; i < fSets.length; i++) {
			// Campaigns
			if (fSets[i].getName().equals("campaigns")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				campImgs = new String[setPhotos.length];
				campThumbs = new String[setPhotos.length];
				campDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					campImgs[j] = setPhotos[j].makeURL();
					campThumbs[j] = setPhotos[j].makeThumbURL();
					campDesc[j] = setPhotos[j].getTitle();
					
				}
			} else if (fSets[i].getName().equals("press")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				pressImgs = new String[setPhotos.length];
				pressThumbs = new String[setPhotos.length];
				pressDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					pressImgs[j] = setPhotos[j].makeURL();
					pressThumbs[j] = setPhotos[j].makeThumbURL();
					pressDesc[j] = setPhotos[j].getTitle();
				}
			} else if (fSets[i].getName().equals("graphic designs")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				gdImgs = new String[setPhotos.length];
				gdThumbs = new String[setPhotos.length];
				gdDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					gdImgs[j] = setPhotos[j].makeURL();
					gdThumbs[j] = setPhotos[j].makeThumbURL();
					gdDesc[j] = setPhotos[j].getTitle();
				}

			} else if (fSets[i].getName().equals("websites")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				webImgs = new String[setPhotos.length];
				webThumbs = new String[setPhotos.length];
				webDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					webImgs[j] = setPhotos[j].makeURL();
					webThumbs[j] = setPhotos[j].makeThumbURL();
					webDesc[j] = setPhotos[j].getTitle();
				}
			} else if (fSets[i].getName().equals("digital marketing")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				dmImgs = new String[setPhotos.length];
				dmThumbs = new String[setPhotos.length];
				dmDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					dmImgs[j] = setPhotos[j].makeURL();
					dmThumbs[j] = setPhotos[j].makeThumbURL();
					dmDesc[j] = setPhotos[j].getTitle();
				}

			} else if (fSets[i].getName().equals("packaging")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				packImgs = new String[setPhotos.length];
				packThumbs = new String[setPhotos.length];
				packDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					packImgs[j] = setPhotos[j].makeURL();
					packThumbs[j] = setPhotos[j].makeThumbURL();
					packDesc[j] = setPhotos[j].getTitle();
				}

			} else if (fSets[i].getName().equals("booth designs")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				boothImgs = new String[setPhotos.length];
				boothThumbs = new String[setPhotos.length];
				boothDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					boothImgs[j] = setPhotos[j].makeURL();
					boothThumbs[j] = setPhotos[j].makeThumbURL();
					boothDesc[j] = setPhotos[j].getTitle();
				}

			} else {
				continue;
			}
		}

	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(this, ImageGridActivity.class);
		Bundle b = new Bundle();

		switch (v.getId()) {
		case R.id.campaigns_button:
			b.putStringArray("TYPE_URL", campImgs);
			b.putStringArray("TYPE_URL_THUMB", campThumbs);
			// test
			b.putStringArray("CAPTIONS", campDesc);
			b.putString("TITLE", "Campaigns");
			break;
		case R.id.press_button:
			b.putStringArray("TYPE_URL", pressImgs);
			b.putStringArray("TYPE_URL_THUMB", pressThumbs);
			// test
			b.putStringArray("CAPTIONS", pressDesc);
			b.putString("TITLE", "Press");
			break;
		case R.id.graphic_design_button:
			b.putStringArray("TYPE_URL", gdImgs);
			b.putStringArray("TYPE_URL_THUMB", gdThumbs);
			// test
			b.putStringArray("CAPTIONS", gdDesc);
			b.putString("TITLE", "Graphic Designs");
			break;
		case R.id.websites_button:
			b.putStringArray("TYPE_URL", webImgs);
			b.putStringArray("TYPE_URL_THUMB", webThumbs);
			// test
			b.putStringArray("CAPTIONS", webDesc);
			b.putString("TITLE", "Websites");
			break;
		case R.id.digital_marketing_button:
			b.putStringArray("TYPE_URL", dmImgs);
			b.putStringArray("TYPE_URL_THUMB", dmThumbs);
			// test
			b.putStringArray("CAPTIONS", dmDesc);
			b.putString("TITLE", "Digital Marketing");
			break;
		case R.id.packaging_button:
			b.putStringArray("TYPE_URL", packImgs);
			b.putStringArray("TYPE_URL_THUMB", packThumbs);
			// test
			b.putStringArray("CAPTIONS", packDesc);
			b.putString("TITLE", "Packaging");
			break;
		case R.id.booth_designs_button:
			b.putStringArray("TYPE_URL", boothImgs);
			b.putStringArray("TYPE_URL_THUMB", boothThumbs);
			// test
			b.putStringArray("CAPTIONS", boothDesc);
			b.putString("TITLE", "Booth Designs");
			break;

		}
		i.putExtras(b);
		startActivity(i);

	}

}
