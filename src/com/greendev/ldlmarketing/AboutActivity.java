package com.greendev.ldlmarketing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.greendev.flickr.FlickrSetsLibrary;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.PageIndicator;

@SuppressLint("ResourceAsColor")
public class AboutActivity extends LDLActivity {
	PageIndicator mIndicator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);

		// page viewer
		PageAdapter adapter = new PageAdapter();
		ViewPager myPager = (ViewPager) findViewById(R.id.myfivepanelpager);
		myPager.setAdapter(adapter);
		myPager.setCurrentItem(0);

		// Bind the title indicator to the adapter
		LinePageIndicator lIndicator = (LinePageIndicator) findViewById(R.id.indicator);
		mIndicator = lIndicator;
		mIndicator.setViewPager(myPager);
		
	}

	private class PageAdapter extends PagerAdapter {
		TextView name;
		TextView pos;

		View view;

		public int getCount() {
			return 4;
		}

		public Object instantiateItem(View collection, int position) {
			LayoutInflater inflater = (LayoutInflater) collection.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			int resId = 0;

			/* Instantiating layout views */
			switch (position) {
			case 0:
				resId = R.layout.about_layout_0;
				break;
			case 1:
				resId = R.layout.about_layout_1;
				break;
			case 2:
				resId = R.layout.about_layout_2;
				break;
			case 3:
				resId = R.layout.about_layout_3;
				break;
			}
			view = inflater.inflate(resId, null);

			TextView bio;
			/* setting fonts for bio */
			switch (position) {
			case 0:
				bio = (TextView) view.findViewById(R.id.about_line0);

				break;
			case 1:
				bio = (TextView) view.findViewById(R.id.about_line1);
				break;
			case 2:
				bio = (TextView) view.findViewById(R.id.about_line2);
				break;
			case 3:
				bio = (TextView) view.findViewById(R.id.about_line3);
				break;
			default:
				bio = (TextView) view.findViewById(R.id.about_line0);
				break;
			}
			// bio.setTypeface(font);
			bio.setTextColor(R.color.darkgrey);
			// bio.setTextSize(50);

			if (position > 0) {
				/* setting fonts for names and company titles */
				switch (position) {
				case 1:
					name = (TextView) view.findViewById(R.id.bio_name1);
					pos = (TextView) view.findViewById(R.id.position1);
					break;
				case 2:
					name = (TextView) view.findViewById(R.id.bio_name2);
					pos = (TextView) view.findViewById(R.id.position2);
					break;
				case 3:
					name = (TextView) view.findViewById(R.id.bio_name3);
					pos = (TextView) view.findViewById(R.id.position3);
					break;
				}
				name.setTypeface(fontreg);
				name.setTextColor(R.color.pink);
				name.setTextSize(22);

				pos.setTypeface(fontreg);
				pos.setTextColor(R.color.darkgrey);
			}

			((ViewPager) collection).addView(view, 0);
	
			return view;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == ((View) arg1);
		}

		@Override
		public Parcelable saveState() {
			return null;

		}
	}
}
