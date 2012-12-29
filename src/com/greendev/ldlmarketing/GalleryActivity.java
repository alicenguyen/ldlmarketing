package com.greendev.ldlmarketing;

import java.util.ArrayList;
import java.util.List;

import com.greendev.flickr.FetchSetsTask;
import com.greendev.flickr.FlickrLibrary;
import com.greendev.flickr.FlickrPhoto;
import com.greendev.flickr.FlickrSet;
import com.greendev.flickr.FlickrSetsLibrary;
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

	/* UI stuff*/
	boolean commentView;
	private TextView commentText;
	private ImageView imageArea;
	private RelativeLayout image;
	private ListView listView;
	private View button;
	
	/* image fragment stuff */
	List<String[]> listOfSetImgs;
	List<String[]> listOfSetThumbs;
	int position;
	Object[] setOfSetImgs;
	Object[] setOfSetDescs;
	Object[] setOfSetThumbs;

	/* flickr data stuff*/
	private FlickrSetsLibrary lib;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		setContentView(R.layout.gallery_layout);

		// Photos button
		View photoButton = findViewById(R.id.button_photos);
		photoButton.setOnClickListener(this);

		// Videos button
		View videoButton = findViewById(R.id.button_videos);
		videoButton.setOnClickListener(this);

		lib = FlickrSetsLibrary.getInstance();
		getData();

		// // Start fetching sets from Flickr
		// new Thread(new FetchSetsTask(responseHandler)).start();
	}

	private void getData() {
		setOfSetImgs = lib.setOfSetImgs;
		setOfSetDescs = lib.setOfSetDescs;
		setOfSetThumbs = lib.setOfSetThumbs;
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

		try {
			switch (v.getId()) {
			case R.id.button_photos:
				if (isNetworkAvailable()) {
					Intent intent = new Intent(this, ImageGridActivity.class);
					Bundle b = new Bundle();

					b.putStringArray("SETS_NAMES", lib.setNames);
					b.putStringArray("TYPE_URL_THUMB", lib.setsThumbUrls);
					b.putStringArray("CAPTIONS", lib.setsThumbUrls);

					/* the key to enter the PhotoActivtityGridFragment */
					b.putString("key", "PhotoActivityGridFragment");
					b.putString("TITLE", "LDL Gallery");

					b.putParcelable("SET_IMGS", new MyParcelableObjectArray(
							this, setOfSetImgs));
					b.putParcelable("SET_THUMBS", new MyParcelableObjectArray(
							this, setOfSetDescs));
					b.putParcelable("SET_DESCS", new MyParcelableObjectArray(
							this, setOfSetThumbs));
					intent.putExtras(b);

					/* checking if thumbs are null from low service */
					if (lib.setsThumbUrls != null) {
						startActivity(intent);
					} else {
						toast2.show();
					}

				} else
					toast1.show();

				break;

			case R.id.button_videos:
				if (isNetworkAvailable()) {
					Intent c = new Intent(this, YoutubeActivity.class);
					startActivity(c);
				} else
					toast1.show();
				break;

			}
		} catch (Exception e) {
			toast1.show();
			Log.e("GALLERYACTIVITY", "can't start activity ImageGridActivity");
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
