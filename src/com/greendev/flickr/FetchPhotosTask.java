package com.greendev.flickr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;



public class FetchPhotosTask implements Runnable {
	private final Handler replyTo;
	// A reference to retrieve the data when this task finishes
		public static final String LIBRARY = "Library";
	
	public FetchPhotosTask(Handler replyTo) {
		this.replyTo = replyTo;
	}

	public void run() {
		HttpClient httpclient = new DefaultHttpClient();
//		HttpGet httpget = new HttpGet(
//				"http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=4409059966d7ae8599d00460266641c1&user_id=87656684%40N07&format=json&nojsoncallback=1");  
		HttpGet httpget = new HttpGet(
				"http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=4409059966d7ae8599d00460266641c1&user_id=88561829@N06&format=json&nojsoncallback=1");  
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputstream = entity.getContent();
				BufferedReader bufferedreader = new BufferedReader(
						new InputStreamReader(inputstream));
				StringBuilder stringbuilder = new StringBuilder();
				String currentline = null;
				try {
					while ((currentline = bufferedreader.readLine()) != null) {
						stringbuilder.append(currentline + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				String result = stringbuilder.toString();

				JSONObject thedata = new JSONObject(result);
				JSONObject thephotosdata = thedata.getJSONObject("photos");
				JSONArray thephotodata = thephotosdata.getJSONArray("photo");
				
				// Create an array to store FlickrPhotos in 
				FlickrPhoto[] photos = new FlickrPhoto[thephotodata.length()];
				for (int i = 0; i < thephotodata.length(); i++) {
					JSONObject photodata = thephotodata.getJSONObject(i);
					photos[i] = new FlickrPhoto(photodata.getString("id"),
							photodata.getString("secret"),
							photodata.getString("server"),
							photodata.getString("title"),
							photodata.getString("farm"));
				}
				inputstream.close();
				
				// Create a FlickrLibrary to hold our photos
				FlickrLibrary lib = new FlickrLibrary(photos);
				// Pack the library into the bundle to sent back to the Activity
				Bundle data = new Bundle();
				data.putSerializable(LIBRARY, lib);
				
				// Send the Bundle of data (our Library) back to the handler (our Activity)
				Message msg = Message.obtain();
				msg.setData(data);
				replyTo.sendMessage(msg);
	
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("FlickrPhotosFetchTask", "error");
		}
		
	}
}