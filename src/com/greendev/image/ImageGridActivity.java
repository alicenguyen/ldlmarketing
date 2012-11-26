/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * KNOWN BUGS:
 *  - (fixed) thread issue which prevents android api 11 or above to crash. Inserted
 *    StrictMode configurations
 */


package com.greendev.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.greendev.flickr.MyParcelableObjectArray;
import com.greendev.ldlmarketing.BuildConfig;
import com.greendev.ldlmarketing.R;
import com.greendev.ldlmarketing.R.id;
import com.greendev.ldlmarketing.R.layout;

/**
 * Simple FragmentActivity to hold the main {@link ImageGridFragment} and not
 * much else.
 */
public class ImageGridActivity extends FragmentActivity {
	private static final String TAG = "ImageGridActivity";
	private static String[] TYPE_URL;
	private static String[] TYPE_URL_THUMB;
	private static String[] CAPTIONS;
	private static String TITLE;
	private String key;
	private MyParcelableObjectArray setImgsParcel;
	private MyParcelableObjectArray setThumbsParcel;
	private MyParcelableObjectArray setDescsParcel;
	private Object[] setImgs;
	private Object[] setThumbs;
	private Object[] setDescs;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
		/**
		 * get the intent called from an Activity (eg. Portfolio.class or
		 * PhotoActivity)
		 **/
		Intent intent = getIntent();
		Log.i("Intent from: ", intent.toString());
		/** get the bundle from the intent **/
		Bundle b = intent.getExtras();
	

		// PhotoActivityGridFragment
		key = b.getString("key");
		if (key == null)
			key = "hi";

		TITLE = b.getString("TITLE");

		if (BuildConfig.DEBUG) {
			Utils.enableStrictMode();
		}
	
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");

		/* Custom title bar */
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//		setContentView(R.layout.about_layout);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
//		TextView titleBar = (TextView) findViewById(R.id.title);
//		titleBar.setTypeface(font);
//		titleBar.setText(TITLE);

		if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
			final FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			// ft.add(android.R.id.content, new ImageGridFragment(this,
			// ImageDetailActivity.class, TYPE_URL, TYPE_URL_THUMB), TAG);

			// If calling this activity for the Gallery images then use
			// PhotoActivityGridFragment
			if (key.equals("PhotoActivityGridFragment")) {
				TYPE_URL_THUMB = b.getStringArray("TYPE_URL_THUMB");
				
				/* Parcelable Objects from PhotoActivity */
				setImgsParcel = b.getParcelable("SET_IMGS");
				if (setImgsParcel != null){
					setImgs = setImgsParcel.getArray();
					if(setImgs == null) setImgs = Images.boothDesignsThumbUrls;}
				else setImgs = Images.campaignsThumbUrls;
				
				setThumbsParcel = b.getParcelable("SET_THUMBS");
				if (setThumbsParcel != null){
					setThumbs = setThumbsParcel.getArray();
					if(setThumbs == null) setThumbs = Images.boothDesignsThumbUrls;
				}
				else setThumbs = Images.campaignsThumbUrls;
				
				setDescsParcel = b.getParcelable("SET_DESCS");
				if (setDescsParcel != null){
					setDescs = setDescsParcel.getArray();
					if(setDescs == null) setDescs = Images.boothDesignsThumbUrls;
				}
				else setDescs = Images.campaignsThumbUrls;
				
				
				/* test */
				String[] test = (String[])setDescs[0];
				for(int i = 0; i < test.length; i++){
				Log.i("ImageGridActivity1: ", test[i].toString());
				}
				
				ft.add(android.R.id.content, new PhotoActivityGridFragment(
						this, ImageGridActivity.class, TYPE_URL_THUMB, setImgs,
						setThumbs, setDescs), TAG);
			} else {
				/** retrieve the string array extra passed */
				TYPE_URL = b.getStringArray("TYPE_URL");
				TYPE_URL_THUMB = b.getStringArray("TYPE_URL_THUMB");
			
				CAPTIONS = b.getStringArray("CAPTIONS");
				
				/* test */
				Log.i("object2: ", TYPE_URL[0].toString());
				
				ft.add(android.R.id.content, new ImageGridFragment(this,
						ImageDetailActivity.class, TYPE_URL, TYPE_URL_THUMB,
						CAPTIONS), TAG);
			}
			ft.commit();
		}
	}
}
