package com.greendev.ldlmarketing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
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
import com.greendev.flickr.FlickrPhoto;
import com.greendev.flickr.FlickrSet;
import com.greendev.flickr.MyParcelableObjectArray;
import com.greendev.image.ImageGridActivity;

/*
 * This class is currently not useful!
 */
public class PhotoActivity extends Activity implements OnClickListener {

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

		// Title stuff
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.photo_layout);

		// setContentView(R.layout.portfolio_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		((TextView) findViewById(R.id.title))
				.setText("Testing Flickr Galleries");

		// listView = (ListView) findViewById(R.id.content);
		// button = findViewById(R.id.button);
		// button.setOnClickListener(this);

		// Start fetching sets from Flickr
		new Thread(new FetchSetsTask(responseHandler)).start();

	}

	Handler response = new Handler() {
	};

	Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			createURLSets(msg);
			startIntent();

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

		Log.i("PhotoActivity->setThumbUrls: ", setsThumbUrls.length + "");
		Log.i("PhotoActivity->setNames: ", setNames.length + "");

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

	public void startIntent() {

		Intent intent = new Intent(this, ImageGridActivity.class);
		Bundle b = new Bundle();

		// Object[] setsOfImages = listOfSetImgs.toArray(new
		// Object[listOfSetImgs.size()]);
		// b.putParcelableArray("arrayOfSets", (Parcelable[]) setsOfImages);
		// Log.i("PhotoActivity onClick", setsOfImages[0]);

		// b.putStringArray("TYPE_URL", setsThumbUrls);
		b.putStringArray("TYPE_URL_THUMB", setsThumbUrls);
		b.putStringArray("CAPTIONS", setsThumbUrls);
		b.putString("key", "PhotoActivityGridFragment");
		b.putString("TITLE", "PhotoActivity");

		b.putParcelable("SET_IMGS", new MyParcelableObjectArray(this,
				setOfSetImgs));
		b.putParcelable("SET_THUMBS", new MyParcelableObjectArray(this,
				setOfSetDescs));
		b.putParcelable("SET_DESCS", new MyParcelableObjectArray(this,
				setOfSetThumbs));

		// test

		// additional
		intent.putExtras(b);
		startActivity(intent);
	}

	// @Override
	public void onClick(View v) {
		// switch (v.getId()) {
		// case R.id.button:
		// // listView.setAdapter(new CustomListAdapter(this, setNames));
		// Intent intent = new Intent(this, ImageGridActivity.class);
		// Bundle b = new Bundle();
		//
		// // Object[] setsOfImages = listOfSetImgs.toArray(new
		// // Object[listOfSetImgs.size()]);
		// // b.putParcelableArray("arrayOfSets", (Parcelable[]) setsOfImages);
		// // Log.i("PhotoActivity onClick", setsOfImages[0]);
		//
		// // b.putStringArray("TYPE_URL", setsThumbUrls);
		// b.putStringArray("TYPE_URL_THUMB", setsThumbUrls);
		// b.putStringArray("CAPTIONS", setsThumbUrls);
		// b.putString("key", "PhotoActivityGridFragment");
		// b.putString("TITLE", "PhotoActivity");
		//
		// b.putParcelable("SET_IMGS", new MyParcelableObjectArray(this,
		// setOfSetImgs));
		// b.putParcelable("SET_THUMBS", new MyParcelableObjectArray(this,
		// setOfSetDescs));
		// b.putParcelable("SET_DESCS", new MyParcelableObjectArray(this,
		// setOfSetThumbs));
		//
		// // test
		//
		//
		// // additional
		// intent.putExtras(b);
		// startActivity(intent);
		// break;
		// }
	}

}
