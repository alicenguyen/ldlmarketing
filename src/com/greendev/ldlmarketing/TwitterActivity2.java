package com.greendev.ldlmarketing;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This activity uses the JTwitter api.
 * @author Alice
 *
 */

public class TwitterActivity2 extends Activity {
	private static final String TAG = "ldlmarketing";
	private static final String OAUTH_KEY = "MVzKsfZg7p6IGgiSlgjA";
	private static final String OAUTH_SECRET = "0ZaCLjkEWdiMXYZB0AUUcTAYtWGcvCa6cMitRncz0";
	private static final String OAUTH_CALLBACK_SCHEME = "myapp";
	private static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
			+ "://callback";
	private static final String TWITTER_USER = "amnguyen30@gmail.com";

	private OAuthSignpostClient oauthClient;
	private OAuthConsumer mConsumer;
	private OAuthProvider mProvider;
	private Twitter twitter;
	SharedPreferences prefs;

	/*
	 * Calls when activity is first created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter2_layout);

		mConsumer = new CommonsHttpOAuthConsumer(OAUTH_KEY, OAUTH_SECRET);
		mProvider = new DefaultOAuthProvider(
				"https://api.twitter.com/oauth/request_token",
				"https://api.twitter.com/oauth/access_token",
				"https://api.twitter.com/oauth/authorize");

		// Read the prefs to see if we have tokens
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String token = prefs.getString("token", null);
		String tokenSecret = prefs.getString("tokenSecret", null);
		if (token != null && tokenSecret != null) {
			// we got the tokens, now use it.
			mConsumer.setTokenWithSecret(token, tokenSecret);
			// Create twitter object
			oauthClient = new OAuthSignpostClient(OAUTH_KEY, OAUTH_SECRET,
					token, tokenSecret);
			twitter = new Twitter(TWITTER_USER, oauthClient);
		}
	}

	/*
	 * This method is called once authorization with Twitter is done.
	 */
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d(TAG, "intent: " + intent);

		// Make sure this is a valid callback from OAuth
		Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(OAUTH_CALLBACK_SCHEME)) {
			Log.d(TAG, "callback: " + uri.getPath());

			String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
			Log.d(TAG, "verifier: " + verifier);

			// method from class below
			new RetrieveAccessTokenTask().execute(verifier);
		}
	}

	/*
	 * "Authorize" button callback
	 */
	public void onClickAuthorize(View view) {
		new OAuthAuthorizeTask().execute();
	}

	/*
	 * "Tweet" button callback
	 */
	public void onClickTweet(View view) {
		// if not authenticated yet...
		if (twitter == null) {
			Toast.makeText(this, "Authenticate first", Toast.LENGTH_LONG)
					.show();
			return;
		}

		// already authenticated, tweet
		EditText status = (EditText) findViewById(R.id.status);
		new PostStatusTask().execute(status.getText().toString());

	}

	/*
	 * "Get Status" button callback
	 */
	public void onClickGetStatus(View view) {
		if (twitter == null) {
			Toast.makeText(this, "Authenticate first", Toast.LENGTH_LONG)
					.show();
			return;
		}
		new GetStatusTask().execute();
	}

	/**
	 * Handles starting the Twitter authorization. It runs on a separate thread
	 * in the background.
	 */
	class OAuthAuthorizeTask extends AsyncTask<Void, Void, String> {

		/*
		 * This method runs automatically.
		 */
		@Override
		protected String doInBackground(Void... params) {
			String authUrl;
			String message = null;
			try {
				authUrl = mProvider.retrieveRequestToken(mConsumer,
						OAUTH_CALLBACK_URL);
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(authUrl));
				startActivity(intent);
			} catch (OAuthMessageSignerException e) {
				message = "OAuthMessageSignerException";
				e.printStackTrace();
			} catch (OAuthNotAuthorizedException e) {
				message = "OAuthNotAuthorizedException";
				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				message = "OAuthExpectationFailedException";
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				message = "OAuthCommunicationException";
				e.printStackTrace();
			}
			return message;
		}

		/*
		 * This method synchronizes itself with the UI thread and allows to
		 * update it.
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				Toast.makeText(TwitterActivity2.this, result, Toast.LENGTH_LONG)
						.show();
			}
		}
	} // -- end of Class  --

	/**
	 * Handles retrieving access tokens from twitter
	 */
	class RetrieveAccessTokenTask extends AsyncTask<String, Void, String> {

		/*
		 * Automcatically called and runs in the background on a separate thread.
		 */
		@Override
		protected String doInBackground(String... params) {
			String message = null;
			String verifier = params[0];
			try {
				// Get the token
				Log.d(TAG, "mConsumer: " + mConsumer);
				Log.d(TAG, "mProvider: " + mProvider);
				mProvider.retrieveAccessToken(mConsumer, verifier);
				String token = mConsumer.getToken();
				String tokenSecret = mConsumer.getTokenSecret();
				mConsumer.setTokenWithSecret(token, tokenSecret);
				Log.d(TAG, String.format(
						"verifier: %s, token: %s, tokenSecret: %", verifier,
						token, tokenSecret));

				// Store token in prefs
				prefs.edit().putString("token", token)
						.putString("tokenSecret", tokenSecret).commit();

				// Make a Twitter object
				oauthClient = new OAuthSignpostClient(OAUTH_KEY, OAUTH_SECRET,
						token, tokenSecret);
				twitter = new Twitter("AliceNguyen", oauthClient); // ???
				Log.d(TAG, "token: " + token);

			} catch (OAuthMessageSignerException e) {
				message = "OAuthMessageSignerException";
				e.printStackTrace();
			} catch (OAuthNotAuthorizedException e) {
				message = "OAuthNotAuthorizedException";
				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				message = "OAuthExpectationFailedException";
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				message = "OAuthCommunicationException";
				e.printStackTrace();
			}
			return message;
		}
		
		/*
		 *  Syncs with the UI and also allows it to be updated.
		 *  @param result is the return message from doInBackground().
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(result != null) {
				Toast.makeText(TwitterActivity2.this, result, Toast.LENGTH_LONG).show();
			}
		}
	} // -- end of class --
	
	/**
	 * Handles getting Twitter status
	 */
	class GetStatusTask extends AsyncTask<Void, Void, String> {
		
		/*
		 * Automatically runs on separate thread
		 * 
		 */
		@Override
		protected String doInBackground(Void... params) {
			return twitter.getStatus().text;
		}
		
		/*
		 * Syncs with the UI and updates it
		 * @param result return value from doInBackground()
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast.makeText(TwitterActivity2.this, result, Toast.LENGTH_LONG).show();
		}
	} // -- end of class --
	
	/**
	 * Handles posting new statis to Twitter
	 */
	class PostStatusTask extends AsyncTask<String, Void, String> {
		
		/*
		 * Automatically runs on separate thread
		 */
		@Override
		protected String doInBackground(String... params) {
			try {
				twitter.setStatus(params[0]);
				return "Successfully posted: " + params[0];
			} catch (TwitterException e) {
				return "Error connecting to server.";
			}
		}
		
		/*
		 * Is called after doInBackground()
		 * @param result return value from doInBackground()
		 */
	    @Override
	    protected void onPostExecute(String result) {
	      super.onPostExecute(result);
	      Toast.makeText(TwitterActivity2.this, result, Toast.LENGTH_LONG).show();
	    }
		
	}
}
