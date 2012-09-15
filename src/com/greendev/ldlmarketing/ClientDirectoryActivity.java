package com.greendev.ldlmarketing;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
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
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");

		// Custom title bar
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.clientdir_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		TextView title = (TextView) findViewById(R.id.title);
		title.setTypeface(font);
		title.setText("Client Directory");

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
