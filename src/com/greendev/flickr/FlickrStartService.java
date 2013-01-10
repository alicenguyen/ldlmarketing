package com.greendev.flickr;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class FlickrStartService extends IntentService {

	private int result = Activity.RESULT_CANCELED;
	private final String TAG = "FlickrStartService";
	private Intent intent;
	private FlickrLibrary lib;

	public FlickrStartService() {
		super("FlickrStartService");
	}

	/**
	 * Will be called asynchronously
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		this.intent = intent;
		Log.i("FlickrStartService", "onHandleIntent is starting");
		new Thread(new FetchSetsTask(responseHandler)).start();
	}

	Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			prepSets(msg);
			if (lib != null) {
				Log.i("FlickrStartService", "lib is not null");
			} else
				Log.i("FlickrStartService", "lib is null");

			Bundle extras = intent.getExtras();
			if (extras != null) {
				Messenger messenger = (Messenger) extras.get("MESSENGER");
				Message message = Message.obtain();
				message.arg1 = result;
				message.obj = lib;

				try {
					messenger.send(message);
				} catch (android.os.RemoteException e1) {
					Log.w(getClass().getName(), "Exception sending message", e1);
				}
			}
		};
	};

	/**
	 * this tells FetchSetsTask which Flickrs to get.
	 * 
	 * @param msg
	 */
	private void prepSets(Message msg) {
		try {
			lib = (FlickrLibrary) msg.getData().get(FetchSetsTask.LIBRARY);

			FlickrSetsLibrary setslib = FlickrSetsLibrary.getInstance();
			setslib.setFlickrSets(lib.fetchSets());
			setslib.createUrlPortfolio();
			setslib.createUrlGallery();

			// Successful finished
			result = Activity.RESULT_OK;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}

}
