package com.greendev.twitter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greendev.ldlmarketing.R;

/**
 * Adapter for the tweets
 * @author Alice
 *
 */
public class TweetItemAdapter extends ArrayAdapter<Tweet> {
	private ArrayList<Tweet> tweets;
	
	public TweetItemAdapter(Context context, int textViewResourceId, ArrayList<Tweet> tweets) {
		super(context, textViewResourceId, tweets);
		this.tweets = tweets;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					
			v = vi.inflate(R.drawable.tweet_listitem, null);
		}
		
		Tweet tweet = tweets.get(position);
		if (tweet != null) {
			TextView username = (TextView) v.findViewById(R.id.username);
			TextView message = (TextView) v.findViewById(R.id.message);
			TextView image = (TextView) v.findViewById(R.id.avatar);
			
			if (username != null) {
		        username.setText(tweet.username);
		      }

		      if(message != null) {
		        message.setText(tweet.message);
		      }
		        
		      if(image != null) {
		       // image.setImageBitmap(getBitmap(tweet.image_url));
		      }
		}
		return v;
	}
	
}
