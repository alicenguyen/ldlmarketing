package com.greendev.ldlmarketing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greendev.flickr.FetchSetsTask;
import com.greendev.flickr.FlickrLibrary;
import com.greendev.flickr.FlickrSet;
import com.greendev.image.ImageGridActivity;

public class PhotoActivity extends Activity implements OnClickListener {

	boolean commentView;
	TextView commentText;
	ImageView imageArea;
	RelativeLayout image;
	List<String> setNames;
	ListView listView;
	List<String> setsThumbUrls;
	View button;
	List<String[]> listOfSetImgs;
	List<String[]> listOfSetThumbs;
	int position;
//	Intent intent;
//	Bundle b;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Title stuff
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.photo_layout);
		// setContentView(R.layout.portfolio_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		((TextView) findViewById(R.id.title))
				.setText("Testing Flickr Galleries");
		// listView = (ListView) findViewById(R.id.content);
		button = findViewById(R.id.button);
		button.setOnClickListener(this);

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

	public void createURLSets(Message msg) {
		// Get library from the responseHandler
		FlickrLibrary lib = (FlickrLibrary) msg.getData().get(
				FetchSetsTask.LIBRARY);
		// Get sets from library
		FlickrSet[] fSets = lib.getSets();
		setNames = new ArrayList<String>();
		setsThumbUrls = new ArrayList<String>();

		listOfSetImgs = new ArrayList<String[]>();
		listOfSetThumbs = new ArrayList<String[]>();
		// Check names
		for (int i = 0; i < fSets.length; i++) {
			String setName = fSets[i].getName();
			if (isPortfolioSet(setName)) {
				continue;
			}
			// get photos for sets
			fSets[i].getPhotos();
			// Get random image url for the thumbnail
			String randThumbUrl = fSets[i].getRandomThumbUrl();
			setNames.add(setName);
			setsThumbUrls.add(randThumbUrl);
			// Current set's of photos
			listOfSetImgs.add(fSets[i].getPhotoSetUrl());
			listOfSetThumbs.add(fSets[i].getPhotoSetThumbUrl());
			position = i;
			
		}

	}

	/*
	 * Checks if the set belongs in Portfolio. TRUE -- is a portfolio FALSE --
	 * is not; is a gallery set.
	 */
	private boolean isPortfolioSet(String setName) {
		return setName.equals("campaigns") || setName.equals("press")
				|| setName.equals("graphic designs")
				|| setName.equals("websites")
				|| setName.equals("digital marketing")
				|| setName.equals("packaging")
				|| setName.equals("booth designs");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button:
			// listView.setAdapter(new CustomListAdapter(this, setNames));
			Intent intent = new Intent(this, ImageGridActivity.class);
			Bundle b = new Bundle();
			String[] thumbs = setsThumbUrls.toArray(new String[setsThumbUrls
					.size()]);
			Object[] setsOfImages = listOfSetImgs.toArray(new Object[listOfSetImgs.size()]);
			
			//Log.i("PhotoActivity onClick", (String) setsOfImages[position]);

			b.putStringArray("TYPE_URL", thumbs);
			b.putStringArray("TYPE_URL_THUMB", thumbs);
			b.putString("key", "PhotoActivityGridFragment");
			// test
			b.putStringArray("CAPTIONS", thumbs);
			b.putString("TITLE", "PhotoActivity");
			
			// additional
		
			intent.putExtras(b);
			startActivity(intent);
			break;
		}
	}
}
