package com.greendev.ldlmarketing;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.sax.Element;
import android.util.Log;
import android.widget.MediaController;
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
