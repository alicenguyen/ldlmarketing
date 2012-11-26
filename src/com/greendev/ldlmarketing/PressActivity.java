package com.greendev.ldlmarketing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class PressActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// TODO
		setContentView(R.layout.ldlcam_layout);
		
		// customizing font in action bar
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");
		this.getActionBar().setDisplayShowCustomEnabled(true);
		this.getActionBar().setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.title, null);
		TextView title = ((TextView)v.findViewById(R.id.title));
		title.setText(this.getTitle());
		title.setTypeface(font);
	}
}
