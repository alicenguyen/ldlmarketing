package com.greendev.ldlmarketing;

import java.util.ArrayList;
import java.util.List;

import com.greendev.flickr.FetchSetsTask;
import com.greendev.flickr.FlickrLibrary;
import com.greendev.flickr.FlickrPhoto;
import com.greendev.flickr.FlickrSet;
import com.greendev.flickr.MyParcelableObjectArray;
import com.greendev.image.ImageGridActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryActivity extends Activity implements OnClickListener {

	boolean commentView;
	TextView commentText;
	ImageView imageArea;
	RelativeLayout image;
	String[] setNames;
	ListView listView;
	String[] setsThumbUrls;
	View button;
	List<String[]> listOfSetImgs;
	List<String[]> listOfSetThumbs;
	int position;
	// Intent intent;
	// Bundle b;
	Object[] setOfSetImgs;
	Object[] setOfSetDescs;
	Object[] setOfSetThumbs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
				
		setContentView(R.layout.gallery_layout);
		
		// Photos button
		View photoButton = findViewById(R.id.button_photos);
		photoButton.setOnClickListener(this);

		// Videos button
		View videoButton = findViewById(R.id.button_videos);
		videoButton.setOnClickListener(this);

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
		FlickrSet[] fSetsAll = lib.fetchSets();
		List<FlickrSet> list = new ArrayList<FlickrSet>();

		/* get the number of non-portfolio albums */
		for (int i = 0; i < fSetsAll.length; i++) {
			String setName = fSetsAll[i].getName();
			if (!isPortfolioSet(setName)) {
				list.add(fSetsAll[i]);
			}
		}

		FlickrSet[] fSets = list.toArray(new FlickrSet[list.size()]);

		setOfSetImgs = new Object[fSets.length];
		setOfSetDescs = new Object[fSets.length];
		setOfSetThumbs = new Object[fSets.length];

		setNames = new String[fSets.length];
		setsThumbUrls = new String[fSets.length];

		for (int i = 0; i < fSets.length; i++) {
			/* filter out any portfolio sets */
			String setName = fSets[i].getName();
			if (!isPortfolioSet(setName)) {

				/* get the photos of set i */
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();

				/* Get random image url for the thumbnail */
				String randThumbUrl = fSets[i].getRandomThumbUrl();

				/* store the name of set i into the array */
				setNames[i] = setName;
				setsThumbUrls[i] = randThumbUrl;

				/* set's containers */
				String[] aSetOfImgs = new String[setPhotos.length];
				String[] aSetOfThumbs = new String[setPhotos.length];
				String[] aSetOfDesc = new String[setPhotos.length];

				/* getting set's urls and descriptions */
				for (int j = 0; j < setPhotos.length; j++) {
					aSetOfImgs[j] = setPhotos[j].makeURL();
					aSetOfThumbs[j] = setPhotos[j].makeThumbURL();
					aSetOfDesc[j] = setPhotos[j].getTitle();
				}

				/* then storing it to the Object array */
				setOfSetImgs[i] = aSetOfImgs;
				setOfSetDescs[i] = aSetOfThumbs;
				setOfSetThumbs[i] = aSetOfThumbs;
			}
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
				|| setName.equals("booth");
	}

	@Override
	public void onClick(View v) {
		Context context = getApplicationContext();
		CharSequence text = "Oops! We need internet connection to access this page!";
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);

		switch (v.getId()) {
		case R.id.button_photos:
			if(isNetworkAvailable()){
			Intent intent = new Intent(this, ImageGridActivity.class);
			Bundle b = new Bundle();

			b.putStringArray("SETS_NAMES", setNames);
			b.putStringArray("TYPE_URL_THUMB", setsThumbUrls);
			b.putStringArray("CAPTIONS", setsThumbUrls);
			
			/*  the key to enter the PhotoActivtityGridFragment*/
			b.putString("key", "PhotoActivityGridFragment");
			b.putString("TITLE", "LDL Gallery");
			
			b.putParcelable("SET_IMGS", new MyParcelableObjectArray(this,
					setOfSetImgs));
			b.putParcelable("SET_THUMBS", new MyParcelableObjectArray(this,
					setOfSetDescs));
			b.putParcelable("SET_DESCS", new MyParcelableObjectArray(this,
					setOfSetThumbs));

			intent.putExtras(b);
			startActivity(intent);
			} else toast.show();
				
			break;

		case R.id.button_videos:
			if(isNetworkAvailable()){
			Intent c = new Intent(this, YoutubeActivity.class);
			startActivity(c);} else toast.show();
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
