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
		setContentView(R.layout.youtube_video_view_layout);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		} 
		String url = extras.getString("videoUrl");
		System.out.println("" + url);
	}
	

}
