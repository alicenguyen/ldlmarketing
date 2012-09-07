package com.greendev.ldlmarketing;

import android.app.Activity;
import android.os.Bundle;
import android.widget.VideoView;

/**
 * Special class which plays youtube videos within it's own layout. It should
 * start playing after user clicks on the item from listview.
 * 
 * --Alice
 */

public class ViewYouTubeVideoActivity extends Activity {
	VideoView myVideoView; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_view_layout);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		} 
		String url = extras.getString("videoUrl");
		System.out.println("" + url);
		
/*		// method 1 -- embedded version of videoplay
		String videoUrl = getUrlVideoRTSP(url);
		System.out.println("url-- " + videoUrl);
		System.out.println("url rtsp-- " + Uri.parse(videoUrl));
		myVideoView = (VideoView) findViewById(R.id.myvideoview);
		myVideoView.setVideoURI(Uri.parse(videoUrl));	
		MediaController mc = new MediaController(this);
		myVideoView.setMediaController(mc);
		myVideoView.requestFocus();
		myVideoView.start();
		mc.show();*/
		
/*		// method 2 -- plays with default youtube player. NOT WANTED!
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(i);*/
	}
	

}
