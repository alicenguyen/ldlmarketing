package com.greendev.ldlmarketing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PhotoActivity extends Activity implements OnClickListener {

	boolean commentView;
	TextView commentText;
	ImageView imageArea;
	RelativeLayout image;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_layout);
		commentText = (TextView)findViewById(R.id.comment);
		image = (RelativeLayout)findViewById(R.id.background);
		image.setOnClickListener(this);
		commentText.setVisibility(View.VISIBLE); 		
		commentView = true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.background: {
			if (commentView == true) {
				commentView = false;
				commentText.setVisibility(View.GONE);
				
			}else{
				commentView = true;
				commentText.setVisibility(View.VISIBLE);
			}
		}
			break; 
		}
	}
}
