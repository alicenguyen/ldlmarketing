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
	private Twitter m_insTwitter; // Twitter instance �� ������ �޾ƿ��� �����Ƽ� �׳� ����Ƚ��ϴ�.
	private AccessToken m_insAccessToken; // access token
	private RequestToken m_insRequestToken; // ����� access token �� ���� ��� access
											// token �� ������ ���� ���˴ϴ�.

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_layout);

		m_sendTweet = (Button) findViewById(R.id.tweet_button);
		m_tvResult = (TextView) findViewById(R.id.tv_result);

		m_sendTweet.setOnClickListener(this); // submit button �� ������

		m_tvResult.setText("Sending message"); // �� message �� �����ϰ� �˴ϴ�.
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tweet_button: {
			IOPTE_TWITTER_Submit(); // submit button �� ������...!!
		}
			break;
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// WEB ���� ������ ��ġ�ų�, �������� �ʰ� ������ ���ƿ� ��� �������� �������� �˴ϴ�.

		super.onNewIntent(intent);

		Uri uri = intent.getData();

		if (null != uri
				&& TWITTER_CALLBACK_URL.getScheme().equals(uri.getScheme()))
		// ���� ���� URI Scheme �� �����ϸ�
		{
			String strOAuthVerifier = uri.getQueryParameter("oauth_verifier"); 
			// twitter ���� page���� �������� �ʰ� ������ �̵� ��ũ�� Ŭ���ؼ� �ٷ� ���ƿ���
			// strOAuthVerifier == null �̹Ƿ� ����ó��
			if (null == strOAuthVerifier) {
				return;
			}

			try {
				// request token �� oauth verifier �� access token �� ���ɴϴ�.
				m_insAccessToken = m_insTwitter.getOAuthAccessToken(
						m_insRequestToken, strOAuthVerifier);

				// ���� access token �� Shared Preference �� ���� ��� �Ӵϴ�.
				Twitter_StoreAccessToken(m_insTwitter.verifyCredentials()
						.getId(), m_insAccessToken);

				// access token �� �������� message �� �����մϴ�.
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
			// SharedPreferences �� ����� access token �� ������
			// Twitter ��ü�� access token �� ������ �ְ� �ٷ� message �� �����մϴ�.
			m_insTwitter.setOAuthAccessToken(m_insAccessToken);

			IOPTE_TWITTER_UpdateStatus();
		} else {
			// SharedPreferences �� ����� access token �� ������ ���� ������ �����ϰ� �˴ϴ�.
			try {
				// request token �� ���ɴϴ�.
				m_insRequestToken = m_insTwitter
						.getOAuthRequestToken(TWITTER_CALLBACK_URL.toString());
			} catch (TwitterException te) {
				te.printStackTrace();
			}

			// WEB OAuth ������ �����ϱ� ���� Browser �� ȣ���մϴ�.
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(m_insRequestToken.getAuthorizationURL())));
		}
	}

	private void Twitter_GetInstance() {
		if (null == m_insTwitter) {
			m_insTwitter = new TwitterFactory().getInstance();

			// twitter instance �� ���� key �� ��� �� �ݴϴ�.
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
		strTwitterID = "jasoncho0129"; // ���� ��� Ư���ο��� �޼����� ���� �������� ����� ���ұ� ������ �� �κ��� �ʿ��մϴ�.

		try {
			// Ư�� ID ���� message �� �������ϴ�.
			m_insTwitter.updateStatus("@" + strTwitterID + " " + strContent);

			finish();
		} catch (TwitterException te) {
			if (HttpResponseCode.FORBIDDEN == te.getStatusCode()) {
				String strError = null;

				strError = te.getErrorMessage();

				if (true == strError.contains("duplicate")) {
					; // ������ message �� update �� �ϰԵǸ� �������� �������ϴ�.
				} else if (true == strError.contains("140")) {
					; // message �� 140 ���ڸ� �Ѿ�� �������� �������ϴ�.
				} else {
					; // ������ �𸣰����� status code �� FORBIDDEN ���� �Ѿ���� ���ʿ��� ó���� ��
						// �ֵ��� �մϴ�.
						// ���� �������� �������� error �� ���غ����� ���߽��ϴ�. ^^;;
				}

				finish();
			} else if (HttpResponseCode.UNAUTHORIZED == te.getStatusCode()) {
				; // ���� ���õ� ������ ������ �������� �������ٰ� �մϴ�.
					// ���� �������� �������� ��츦 ���غ����� ���߽��ϴ�.
				finish();
			}

			te.printStackTrace();
		}
	}
}
