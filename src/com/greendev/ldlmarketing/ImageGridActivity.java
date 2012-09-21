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
 */

package com.greendev.ldlmarketing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.TextView;

import com.greendev.image.ImageDetailActivity;
import com.greendev.image.ImageGridFragment;
import com.greendev.image.Utils;
import com.greendev.ldlmarketing.BuildConfig;
import com.greendev.ldlmarketing.R;

/**
 * Simple FragmentActivity to hold the main {@link ImageGridFragment} and not
 * much else.
 */
public class ImageGridActivity extends FragmentActivity {
	private static final String TAG = "ImageGridActivity";
	private static String[] TYPE_URL;
	private static String[] TYPE_URL_THUMB;
	private static String TITLE;

	protected void onCreate(Bundle savedInstanceState) {
		/** get the intent called from an Activity **/
		Intent intent = getIntent();
		/** get the bundle from the intent **/
		Bundle b = intent.getExtras();
		/** retrieve the stringarray extra passed */
		TYPE_URL = b.getStringArray("TYPE_URL");
		TYPE_URL_THUMB = b.getStringArray("TYPE_URL_THUMB");
		TITLE = b.getString("TITLE");

		if (BuildConfig.DEBUG) {
			Utils.enableStrictMode();
		}
		super.onCreate(savedInstanceState);

		Typeface font = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");
		// Custom title bar
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.about_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		TextView titleBar = (TextView) findViewById(R.id.title);
		titleBar.setTypeface(font);
		titleBar.setText(TITLE);

		if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
			final FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			// ft.add(android.R.id.content, new ImageGridFragment(this,
			// ImageDetailActivity.class, TYPE_URL, TYPE_URL_THUMB), TAG);
			ft.add(android.R.id.content, new ImageGridFragment(this,
					ImageDetailActivity.class, TYPE_URL, TYPE_URL_THUMB), TAG);
			ft.commit();
		}
	}
}
