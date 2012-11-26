package com.greendev.ldlmarketing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ContactActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");

		// customizing font in action bar
		this.getActionBar().setDisplayShowCustomEnabled(true);
		this.getActionBar().setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.title, null);
		TextView title = ((TextView)v.findViewById(R.id.title));
		title.setText(this.getTitle());
		title.setTypeface(font);

		setContentView(R.layout.contactus_layout);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
