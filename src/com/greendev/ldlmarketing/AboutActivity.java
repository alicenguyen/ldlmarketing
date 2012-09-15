package com.greendev.ldlmarketing;
   


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class AboutActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	
		setContentView(R.layout.about_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);		
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setTypeface(font);
		title.setText("About LDL");

	
		
	}
}
