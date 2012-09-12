package com.greendev.ldlmarketing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PhotoActivity extends Activity implements OnClickListener {

	boolean commentView;
	TextView commentText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_layout);
		commentText = (TextView)findViewById(R.id.comment);
		commentText.setVisibility(View.VISIBLE); 		
		commentView = true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.header: {
			if (commentView == true) {
				commentView = false;
				commentText.setVisibility(View.INVISIBLE);
				
			}else{
				commentView = true;
				commentText.setVisibility(View.VISIBLE);
			}
		}
			break;
		}
	}
}
