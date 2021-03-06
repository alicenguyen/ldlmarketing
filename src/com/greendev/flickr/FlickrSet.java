package com.greendev.flickr;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

/**
 * FlickrSet is composed of FlickrPhotos. FlickrSet is first constructred with
 * set id and the content (set name). The object then has to call getPhotos() to
 * load the images of the set from Flickr.
 * 
 * @author Alice Nguyen
 * @since 9/25/2012
 * 
 */
public class FlickrSet {
	private final String API_KEY = "77c86c69fb169a5a42d8eb462c2c8232";
	private final String TAG = "FlickrSet()";
	private String id;
	private String name;
	private FlickrPhoto[] photos;
	private String[] URL;


	
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

	// public String[] getURL() {
	// return URL;
	// }

	public FlickrPhoto[] fetchPhotos() {
		HttpClient httpclient = null;
		HttpGet httpget = null;
		try {
			// Getting pictures in set
			httpclient = new DefaultHttpClient();
			httpget = new HttpGet(
					"http://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key="
							+ API_KEY
							+ "&photoset_id="
							+ id
							+ "&format=json&nojsoncallback=1");
		} catch (Exception e) {
			Log.e(TAG, "HttpClient and HttpGet error " + e.toString());
		}
		HttpResponse response = null;
		HttpEntity entity = null;
		try {

			try {
				response = httpclient.execute(httpget);	
			} catch (Exception e) {
				Log.e(TAG, "HttpResponse error " + e.toString()); // error
			}
			
			try {
				entity = response.getEntity();
			} catch (Exception e) {
				Log.e(TAG, "HttpEntity error " + e.toString()); // error
			}
			
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
					Log.i(TAG, "IO Exception ");
					e.printStackTrace();
				}
				String result = stringbuilder.toString();
				JSONObject thephotosdata = new JSONObject();
				JSONArray thephotodata = new JSONArray();

				try {
					JSONObject thedata = new JSONObject(result);
					thephotosdata = thedata.getJSONObject("photoset");
					thephotodata = thephotosdata.getJSONArray("photo");
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
					}
				} catch (Exception e) {
					Log.e(TAG, "Error in JSONObject retrieval " + e.toString());
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
			Log.e(TAG, "fetchPhoto() error");
		}

		return photos;
	}

	public int getPhotoCount() {
		return photos.length;
	}

	public String getRandomThumbUrl() {
		if (photos == null)
			return null;
		Random rand = new Random();
		int randomInt = rand.nextInt(getPhotoCount());
		return photos[randomInt].makeThumbURL();
	}

	public String[] getPhotoSetUrl() {
		if (photos == null)
			return null;
		String[] result = new String[getPhotoCount()];
		for (int i = 0; i < getPhotoCount(); i++) {
			result[i] = photos[i].makeURL();
		}
		return result;
	}

	public String[] getPhotoSetThumbUrl() {
		if (photos == null)
			return null;
		String[] result = new String[getPhotoCount()];
		for (int i = 0; i < getPhotoCount(); i++) {
			result[i] = photos[i].makeThumbURL();
		}
		return result;
	}
	

}
