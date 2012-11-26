package com.greendev.ldlmarketing;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ServicesActivity extends Activity implements OnClickListener {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");

		// Custom title bar
	
		setContentView(R.layout.services_layout);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
