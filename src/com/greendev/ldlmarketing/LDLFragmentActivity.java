package com.greendev.ldlmarketing;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class LDLFragmentActivity extends SherlockFragmentActivity {
	protected Typeface fontreg;
	protected Typeface fontbold;
	private final int LEFT = 7;
	private final int TOP = 0;
	private final int RIGHT = 10;
	private final int BOTTOM = 0;
	private TextView title;
	private View v; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// fonts
		fontreg = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");
		fontbold = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");

		// padding around ic_launcher
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(LEFT, TOP, RIGHT, BOTTOM);

		// customizing font in action bar
		this.getActionBar().setDisplayShowCustomEnabled(true);
		this.getActionBar().setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflator.inflate(R.layout.title, null);
		title = ((TextView) v.findViewById(R.id.title));
		title.setText(this.getTitle());
		title.setTypeface(fontreg);
		// assign the view to the actionbar
		this.getActionBar().setCustomView(v);
	}
	
	protected void setActionBarTitle(String name){
		title.setText(name);
		this.getActionBar().setCustomView(v);
	}

}
