package com.greendev.ldlmarketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class GalleryActivity extends Activity implements OnClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_layout);
		 
		View photoButton = findViewById(R.id.button_photos);
		photoButton.setOnClickListener(this);
		
		View videoButton = findViewById(R.id.button_videos);
		videoButton.setOnClickListener(this);
		
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_photos:
			Intent i = new Intent(this, PhotoActivity.class);
			startActivity(i);
			break;

		case R.id.button_videos:
			Intent c = new Intent(this, YoutubeActivity.class);
			startActivity(c);
			break;

		}
	}

}
