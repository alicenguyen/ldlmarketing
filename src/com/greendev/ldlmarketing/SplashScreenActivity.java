package com.greendev.ldlmarketing;

import java.util.Timer;
import java.util.TimerTask;

import com.greendev.flickr.FetchSetsTask;
import com.greendev.flickr.FlickrLibrary;
import com.greendev.flickr.FlickrSetsLibrary;
import com.greendev.ldlmarketing.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

/**
 * This is the splash screen activity for starting the app. 
 * Also, loading flickr data here to buy me more time!
 * @author Alice Nguyen
 *
 */

public class SplashScreenActivity extends Activity {
	protected int _splashTime = 1500; // time to display the splash screen in ms

	@Override
	public void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splashscreen_layout);
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");
		TextView text1 = (TextView) findViewById(R.id.text_ldl);
		text1.setTypeface(font);
		
		TextView text2 = (TextView) findViewById(R.id.text_services);
		text2.setTypeface(font);
	 
        TimerTask task = new TimerTask()
        {
            @Override  
            public void run() {
                    finish();
                    Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, MainTabActivity.class);
                    startActivity(mainIntent);
            }
        };
        
        Timer timer = new Timer();
        timer.schedule(task, _splashTime);
            /* Handles thread issue */
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
            StrictMode.setThreadPolicy(policy);
    }

}