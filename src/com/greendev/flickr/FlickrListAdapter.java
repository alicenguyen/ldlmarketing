package com.greendev.flickr;

import com.greendev.ldlmarketing.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * @author Alice Nguyen
 * @since 09/26/2012
 */
public class FlickrListAdapter extends BaseAdapter {
	// The list of sets to display
	FlickrSet[] set;
	// An inflator to use when creating rows
	private LayoutInflater mInflater;
	private Context context;

	public FlickrListAdapter(Context cxt, FlickrSet[] set) {
		this.context = cxt;
		this.set = set;
	}

	@Override
	public int getCount() {
		return set.length;
	}

	@Override
	public Object getItem(int position) {
		return set[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Typeface font = Typeface.createFromAsset(context.getAssets(), "Eurosti.TTF");
		// If convertView wasn't null it means we have already set it to our
				// list_item_user_video so no need to do it again
				if (convertView == null) {
					// This is the layout we are using for each row in our list
					// anything you declare in this layout can then be referenced below
					convertView = mInflater
							.inflate(R.layout.list_item_user_video, null);
				}
		return null;
	}

}
