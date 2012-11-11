package com.greendev.camera;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.greendev.ldlmarketing.R;

public class PhotoEditTab  extends TabActivity { 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_tab);
		setTabs();
	}

	private void setTabs() {
		/*
		addTab("", R.drawable.tab_home, HomeActivity.class);
		addTab("", R.drawable.tab_ldlcam, LDLCamActivity.class);
		addTab("", R.drawable.tab_gallery, GalleryActivity.class);
		addTab("", R.drawable.tab_tweet, TwitterActivity2.class);
*/
	}

	private void addTab(String labelId, int drawableId, Class<?> c) {
		Intent intent = new Intent(this, c);
	

		TabHost tabHost = getTabHost();

	
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);

		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator
				.findViewById(R.id.ic_launcher);
		icon.setImageResource(drawableId);

		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}

}