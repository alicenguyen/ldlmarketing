package com.greendev.ldlmarketing;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class LDLCamActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ldlcam_layout);

		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");
		
		// Texts
		TextView titleText1 = (TextView) findViewById(R.id.ldlcam_text1);
		titleText1.setTypeface(font);

		TextView titleText2 = (TextView) findViewById(R.id.ldlcam_text2);
		titleText2.setTypeface(font);
	}

}
