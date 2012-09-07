package com.greendev.ldlmarketing;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainTabActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTabs();
	}

	private void setTabs() {
		addTab("LDL Home", R.drawable.tab_home, HomeActivity.class);
		
		// TODO-1  must change drawable files!
		addTab("LDL Cam", R.drawable.tab_ldlcam, LDLCamActivity.class);
		addTab("Gallery", R.drawable.tab_gallery, GalleryActivity.class);
		addTab("LDL Twitter", R.drawable.twitter_bird_icon, TwitterActivity.class);


	}

	private void addTab(String labelId, int drawableId, Class<?> c) {
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
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