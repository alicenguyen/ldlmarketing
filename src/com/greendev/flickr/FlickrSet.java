package com.greendev.flickr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class FlickrSet {
	String id;
	String name;
	FlickrPhoto[] photos;
	String[] URL;

	public FlickrSet(String _id, String _content) {
		id = _id;
		name = _content;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

//	public String[] getURL() {
//		return URL;
//	}

	public FlickrPhoto[] getPhotos() {
		// Getting pictures in set
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"http://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=1dc263ed8c964801657b49ee9ab48354&photoset_id="
						+ id + "&format=json&nojsoncallback=1");
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
				JSONObject thephotosdata = thedata.getJSONObject("photoset");
				JSONArray thephotodata = thephotosdata.getJSONArray("photo");

				// Create an array to store FlickrPhotos in
				photos = new FlickrPhoto[thephotodata.length()];

				// Create an array for storing urls
				URL = new String[thephotodata.length()];

				for (int i = 0; i < thephotodata.length(); i++) {
					JSONObject photodata = thephotodata.getJSONObject(i);
					photos[i] = new FlickrPhoto(photodata.getString("id"),
							photodata.getString("secret"),
							photodata.getString("server"),
							photodata.getString("title"),
							photodata.getString("farm"));

//					URL[i] = photos[i].makeURL();

				}
				inputstream.close();

				/*
				 * // Create a FlickrLibrary to hold our photos FlickrLibrary
				 * lib = new FlickrLibrary(photos); // Pack the library into the
				 * bundle to sent back to the Activity Bundle data = new
				 * Bundle(); data.putSerializable(LIBRARY, lib);
				 * 
				 * // Send the Bundle of data (our Library) back to the handler
				 * (our Activity) Message msg = Message.obtain();
				 * msg.setData(data); replyTo.sendMessage(msg);
				 */

			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("PhotoSet", "error");
		}
		
		return photos;

	}
	
	public int getPhotoCount(){
		return photos.length;
	}

}
