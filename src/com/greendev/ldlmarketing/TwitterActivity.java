package com.greendev.ldlmarketing;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.internal.http.HttpResponseCode;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class TwitterActivity extends Activity implements OnClickListener {
	private static final String SHARED_PREF_NAME = "LDLMARKETING_TWITTER_SHARED_PREF";
	private static final String TWITTER_CONSUMER_KEY = "w8kfCAN2qUPDk6FLmGg";
	private static final String TWITTER_CONSUMER_SECRET = "9X1GNOsYuqXb9wc0BUZAdjF5wheCuiBtNQOm2HHeo14";
	private static Uri TWITTER_CALLBACK_URL = Uri.parse("ldlmarketing://twitter"); 
	
	private static final String SHARED_PREF_KEY_TWITTER_CONSUMER_KEY = "TWITTER_CONSUMER_KEY";
	private static final String SHARED_PREF_KEY_TWITTER_CONSUMER_SECRET = "TWITTER_CONSUMER_SECRET";

	private Button m_sendTweet;
	private TextView m_tvResult;
	private Twitter m_insTwitter; // Twitter instance 를 여러번 받아오기 귀찮아서 그냥 써버렸습니다.
	private AccessToken m_insAccessToken; // access token
	private RequestToken m_insRequestToken; // 저장된 access token 이 없는 경우 access
											// token 을 얻어오기 위해 사용됩니다.

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_layout);

		m_sendTweet = (Button) findViewById(R.id.tweet_button);
		m_tvResult = (TextView) findViewById(R.id.tv_result);

		m_sendTweet.setOnClickListener(this); // submit button 을 누르면

		m_tvResult.setText("Sending message"); // 이 message 를 전송하게 됩니다.
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tweet_button: {
			IOPTE_TWITTER_Submit(); // submit button 을 누르면...!!
		}
			break;
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// WEB 에서 인증을 마치거나, 인증하지 않고 앱으로 돌아온 경우 이쪽으로 떨어지게 됩니다.

		super.onNewIntent(intent);

		Uri uri = intent.getData();

		if (null != uri
				&& TWITTER_CALLBACK_URL.getScheme().equals(uri.getScheme()))
		// 내가 보낸 URI Scheme 과 동일하면
		{
			String strOAuthVerifier = uri.getQueryParameter("oauth_verifier"); 
			// twitter 인증 page에서 인증하지 않고 앱으로 이동 링크를 클릭해서 바로 돌아오면
			// strOAuthVerifier == null 이므로 예외처리
			if (null == strOAuthVerifier) {
				return;
			}

			try {
				// request token 과 oauth verifier 로 access token 을 얻어옵니다.
				m_insAccessToken = m_insTwitter.getOAuthAccessToken(
						m_insRequestToken, strOAuthVerifier);

				// 얻어온 access token 은 Shared Preference 에 고이 모셔 둡니다.
				Twitter_StoreAccessToken(m_insTwitter.verifyCredentials()
						.getId(), m_insAccessToken);

				// access token 이 생겼으니 message 를 전송합니다.
				IOPTE_TWITTER_UpdateStatus();
			} catch (TwitterException te) {
				// Log.e(m_gVar.getLogTag(), te.getMessage());
			}
		}
	}

	public void IOPTE_TWITTER_Submit() {
		Twitter_GetInstance();

		m_insAccessToken = Twitter_LoadAccessToken(0);

		if (null != m_insAccessToken) {
			// SharedPreferences 에 저장된 access token 이 있으면
			// Twitter 객체에 access token 을 설정해 주고 바로 message 를 전송합니다.
			m_insTwitter.setOAuthAccessToken(m_insAccessToken);

			IOPTE_TWITTER_UpdateStatus();
		} else {
			// SharedPreferences 에 저장된 access token 이 없으면 인증 절차를 진행하게 됩니다.
			try {
				// request token 을 얻어옵니다.
				m_insRequestToken = m_insTwitter
						.getOAuthRequestToken(TWITTER_CALLBACK_URL.toString());
			} catch (TwitterException te) {
				te.printStackTrace();
			}

			// WEB OAuth 인증을 진행하기 위해 Browser 를 호출합니다.
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(m_insRequestToken.getAuthorizationURL())));
		}
	}

	private void Twitter_GetInstance() {
		if (null == m_insTwitter) {
			m_insTwitter = new TwitterFactory().getInstance();

			// twitter instance 에 앱의 key 를 등록 해 줍니다.
			m_insTwitter.setOAuthConsumer(TWITTER_CONSUMER_KEY,
					TWITTER_CONSUMER_SECRET);
		}
	}

	private void Twitter_StoreAccessToken(long a_lUseID,
			AccessToken a_insAccessToken) {
		String strToken = null;
		String strTokenSecret = null;

		strToken = a_insAccessToken.getToken();
		strTokenSecret = a_insAccessToken.getTokenSecret();

		StoreData(SHARED_PREF_KEY_TWITTER_CONSUMER_KEY, strToken);
		StoreData(SHARED_PREF_KEY_TWITTER_CONSUMER_SECRET, strTokenSecret);
	}

	private AccessToken Twitter_LoadAccessToken(long a_lUseID) {
		AccessToken insAccessToken = null;
		String strToken = null;
		String strTokenSecret = null;

		strToken = LoadData(SHARED_PREF_KEY_TWITTER_CONSUMER_KEY);
		strTokenSecret = LoadData(SHARED_PREF_KEY_TWITTER_CONSUMER_SECRET);

		if (null != strToken && null != strTokenSecret && !"".equals(strToken)
				&& !"".equals(strTokenSecret)) {
			insAccessToken = new AccessToken(strToken, strTokenSecret);
		} else {
			// Log.v(m_strLogTag,
			// "Twitter_LoadAccessToken() ## There's no saved strToken, strTokenSecret data");
		}

		return insAccessToken;
	}

	private boolean StoreData(String a_strKey, String a_strData) {
		SharedPreferences insSharedPref = null;
		SharedPreferences.Editor insSharedPrefEditor = null;

		insSharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
		insSharedPrefEditor = insSharedPref.edit();

		insSharedPrefEditor.putString(a_strKey, a_strData);

		return insSharedPrefEditor.commit();
	}

	private String LoadData(String a_strKey) {
		SharedPreferences insSharedPref = null;

		insSharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

		return insSharedPref.getString(a_strKey, "");
	}

	private void IOPTE_TWITTER_UpdateStatus() {
		String strContent = null;
		String strTwitterID = null;

		strContent = m_tvResult.getText().toString();
		strTwitterID = "jasoncho0129"; // 저의 경우 특정인에게 메세지를 보낼 목적으로 만들어 보았기 때문에 이 부분이 필요합니다.

		try {
			// 특정 ID 에게 message 를 보내봅니다.
			m_insTwitter.updateStatus("@" + strTwitterID + " " + strContent);

			finish();
		} catch (TwitterException te) {
			if (HttpResponseCode.FORBIDDEN == te.getStatusCode()) {
				String strError = null;

				strError = te.getErrorMessage();

				if (true == strError.contains("duplicate")) {
					; // 동일한 message 로 update 를 하게되면 이쪽으로 떨어집니다.
				} else if (true == strError.contains("140")) {
					; // message 가 140 글자를 넘어가면 이쪽으로 떨어집니다.
				} else {
					; // 뭔지는 모르겠지만 status code 가 FORBIDDEN 으로 넘어오면 이쪽에서 처리를 해
						// 주도록 합니다.
						// 아직 이쪽으로 떨어지는 error 를 접해보지는 못했습니다. ^^;;
				}

				finish();
			} else if (HttpResponseCode.UNAUTHORIZED == te.getStatusCode()) {
				; // 인증 관련된 오류가 있으면 이쪽으로 떨어진다고 합니다.
					// 아직 이쪽으로 떨어지는 경우를 접해보지는 못했습니다.
				finish();
			}

			te.printStackTrace();
		}
	}
}
