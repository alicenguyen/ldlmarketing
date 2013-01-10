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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FetchSetsTask implements Runnable {
	private final String API_KEY = "77c86c69fb169a5a42d8eb462c2c8232";
	private final Handler replyTo;
	// A reference to retrieve the data when this task finishes
	public static final String LIBRARY = "Library";

	private static final String TAG = "FetchSetsTask()";

	public FetchSetsTask(Handler replyTo) {
		this.replyTo = replyTo;
	}

	public void run() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"http://api.flickr.com/services/rest/?method=flickr.photosets.getList&api_key="
						+ API_KEY
						+ "&user_id=88561829@N06&format=json&nojsoncallback=1");
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
					Log.e("FlickrSetFetchTask", "error_A");
					e.printStackTrace();
				}
				String result = stringbuilder.toString();

				JSONObject thedata = new JSONObject(result);
				JSONObject thephotosdata = thedata.getJSONObject("photosets");
				JSONArray thephotodata = thephotosdata.getJSONArray("photoset");

				// Create an array to store FlickrPhotos in
				FlickrSet[] sets = new FlickrSet[thephotodata.length()];
				for (int i = 0; i < thephotodata.length(); i++) {
					JSONObject photodata = thephotodata.getJSONObject(i);
					// Get the sets' names.
					JSONObject titledata = photodata.getJSONObject("title");
					sets[i] = new FlickrSet(photodata.getString("id"),
							titledata.getString("_content"));

					Log.i(TAG, "id = " + sets[i].getId());
					Log.i(TAG, "name = " + sets[i].getName());

				}
				inputstream.close();

				try {
					// Create a FlickrLibrary to hold our photos
					FlickrLibrary lib = new FlickrLibrary(sets);
					// Pack the library into the bundle to sent back to the
					// Activity
					Bundle data = new Bundle();
					data.putSerializable(LIBRARY, lib);
					
					// Send the Bundle of data (our Library) back to the handler
					// (our Activity)
					if (!Thread.interrupted() && replyTo != null) {
						Message msg = Message.obtain();
						msg.setData(data);
						// notify the user interface that the download is ready
						replyTo.sendMessage(msg);
					}

				} catch (Exception e) {
					Log.e(TAG,
							"Error in creating FlickrLibrary, packing/serializing/sending Bundle "
									+ e.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("FlickrSetFetchTask", "error");
		}

	}
}