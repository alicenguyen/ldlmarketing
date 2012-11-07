package com.greendev.camera;

import com.greendev.ldlmarketing.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class LDLCam2Activity extends Activity {
	private String picPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ldlcam2_layout);
		
		// Get picture path from LDLCamActivity.class
		Intent intent = getIntent();
		picPath = intent.getStringExtra("PIC_PATH");
		
		// Load image view
		ImageView imageView = (ImageView) findViewById(R.id.imgView);
		imageView.setImageBitmap(BitmapFactory.decodeFile(picPath));
	}
}
