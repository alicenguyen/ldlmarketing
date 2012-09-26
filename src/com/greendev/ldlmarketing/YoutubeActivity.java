package com.greendev.ldlmarketing;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greendev.youtube.GetYouTubeUserVideosTask;
import com.greendev.youtube.Library;
import com.greendev.youtube.UrlImageView;
import com.greendev.youtube.Video;
import com.greendev.youtube.VideoClickListener;
import com.greendev.youtube.VideosListView;

/**
 * The Activity can retrieve Videos for a specific username from YouTube It
 * then displays them into a list including the Thumbnail preview and the
 * title There is a reference to each video on YouTube. Note orientation change isn't
 * covered yet, you will want to override onSaveInstanceState() and
 * onRestoreInstanceState() when you come to this </br>
 * 
 * @author Alice Nguyen
 * Credit --  paul.blundell
 */
public class YoutubeActivity extends Activity implements VideoClickListener {
	// A reference to our list that will hold the video details
	private VideosListView listView;
	private TextView videoTitle;
	Typeface font;

	// Saves the url of the clicked video.
	private String url = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		// Custom Font
		Typeface fontB = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");
		 font = Typeface.createFromAsset(getAssets(), "Eurostib.TTF");

		// Custom title bar
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.youtube_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		TextView title = (TextView) findViewById(R.id.title);
		title.setTypeface(fontB);
		title.setText("LDL Videos");
		
		
		listView = (VideosListView) findViewById(R.id.videosListView);
		
		
		// getting youtube videos
		new Thread(
				new GetYouTubeUserVideosTask(responseHandler, "LDLmarketing"))
				.start();
		// Here we are adding this activity as a listener for when any row in
		// the List is 'clicked'
		// The activity will be sent back the video that has been pressed to do
		// whatever it wants with
		// in this case we will retrieve the URL of the video and fire off an
		// intent to view it
		listView.setOnVideoClickListener(this);

	
	}

	public void getUserYouTubeFeed(View v) {
		new Thread(
				new GetYouTubeUserVideosTask(responseHandler, "LDLmarketing"))
				.start();
	}

	Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			populateListWithVideos(msg);
		};
	};

	private void populateListWithVideos(Message msg) {
		Library lib = (Library) msg.getData().get(
				GetYouTubeUserVideosTask.LIBRARY);
		listView.setVideos(lib.getVideos());

	}

	@Override
	protected void onStop() {
		responseHandler = null;
		super.onStop();
	}

	/*
	 * This is the interface method that is called when a video in the listview
	 * is clicked! The interface is a contract between this activity and the
	 * listview
	 */
	@Override
	public void onVideoClicked(Video video) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(video.getUrl()));
	
		startActivity(intent);

		this.url = video.getUrl();

		// method 1 -- This creates a new activity for youtube play
		// Intent intent = new Intent(this, ViewYouTubeVideoActivity.class);
		// intent.putExtra("videoUrl", url); startActivity(intent);

		// // method 2 -- Plays youtube video in this activity.
		// Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		// startActivity(i);
	}
	
	

}