package com.greendev.ldlmarketing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ClientDirectoryActivity extends Activity implements
		OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	

		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");
		setContentView(R.layout.clientdir_layout);
		
		// customizing font in action bar
		this.getActionBar().setDisplayShowCustomEnabled(true);
		this.getActionBar().setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.title, null);
		TextView title = ((TextView)v.findViewById(R.id.title));
		title.setText(this.getTitle());
		title.setTypeface(font);
		//assign the view to the actionbar
		this.getActionBar().setCustomView(v);


		// List View
		String[] names = new String[] { "Triumph", "LBC", "Bench", "Holcim",
				"brieo", "Bioessence", "Philfire", "Contours", "Bounty Fresh Chicken"};
		ListView listView = (ListView) findViewById(R.id.cd_list);
		listView.setAdapter(new CustomListAdapter(this, names));

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
