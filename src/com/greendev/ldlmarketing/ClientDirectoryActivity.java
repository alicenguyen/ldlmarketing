package com.greendev.ldlmarketing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClientDirectoryActivity extends Activity implements
		OnClickListener {
	String[] names;
	GridView gridView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");
		setContentView(R.layout.clientdir_layout);

		// customizing font in action bar
		this.getActionBar().setDisplayShowCustomEnabled(true);
		this.getActionBar().setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.title, null);
		TextView title = ((TextView) v.findViewById(R.id.title));
		title.setText(this.getTitle());
		title.setTypeface(font);
		// assign the view to the actionbar
		this.getActionBar().setCustomView(v);

		// List View
		names = new String[] { "Triumph", "LBC", "Bench", "Holcim", "brieo",
				"Bioessence", "Pasto", "zao", "Bounty Fresh Chicken" };
		
		gridView = (GridView) findViewById(R.id.gridView1);
		 
		gridView.setAdapter(new ImageAdapter(this));
 
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	private class ImageAdapter extends BaseAdapter {
		private Context context;

		public ImageAdapter(Context context) {
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
	 
			LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View gridView;
	 
				gridView = new View(context);
	 
				// get layout from mobile.xml
				gridView = inflater.inflate(R.layout.clientdir_griditem, null);
	 
				// set value into textview
				TextView textView = (TextView) gridView
						.findViewById(R.id.clientdir_item_label);
				textView.setText(" ");
	 
				// set image based on selected text
				ImageView imageView = (ImageView) gridView
						.findViewById(R.id.clientdir_item_image);
	 
				String title = names[position];
	 
				if(convertView == null){
				switch(position){
				case 0:
					imageView.setImageResource(R.drawable.clientdir_triumph);
					break;
				case 1:
					imageView.setImageResource(R.drawable.clientdir_lbc);
					break;
				case 2:
					imageView.setImageResource(R.drawable.clientdir_bench);
					break;
				case 3:
					imageView.setImageResource(R.drawable.clientdir_holcim);
					break;
				case 4:
					imageView.setImageResource(R.drawable.clientdir_brieo);
					break;
				case 5:
					imageView.setImageResource(R.drawable.clientdir_bioessence);
					break;
				case 6:
					imageView.setImageResource(R.drawable.clientdir_pasto);
					break;
				case 7:
					imageView.setImageResource(R.drawable.clientdir_zao);
					break;
				case 8:
					imageView.setImageResource(R.drawable.clientdir_bountyfresh);
					break;
				}
	 
			} else {
				gridView = (View) convertView;
			}
	 
			return gridView;
		}

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}

}
