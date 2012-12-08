package com.greendev.ldlmarketing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ContactActivity extends LDLActivity implements OnClickListener {
	
	private TextView phone;
	private TextView emailT;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactus_layout);
		
		phone = (TextView) findViewById(R.id.hotline);
		phone.setOnClickListener(this);
		
		emailT = (TextView) findViewById(R.id.email);
		emailT.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.hotline:
			
	        String number = "tel:" + phone.getText().toString().trim();
	        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
	        startActivity(callIntent);
			
		case R.id.email:
		
		}

	}

}


