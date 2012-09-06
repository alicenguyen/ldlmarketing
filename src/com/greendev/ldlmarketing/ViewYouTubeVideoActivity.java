package com.greendev.ldlmarketing;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Special class which plays youtube videos within it's own layout. It should
 * start playing after user clicks on the item from listview.
 * 
 * --Alice
 */

public class ViewYouTubeVideoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_view_layout);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		String url = extras.getString("videoUrl");

		VideoView myVideoView = (VideoView) findViewById(R.id.myvideoview);
		myVideoView.setVideoURI(Uri.parse(url));
		myVideoView.setMediaController(new MediaController(this));
		myVideoView.requestFocus();
		myVideoView.start();
	}

}
