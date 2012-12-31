package com.greendev.ldlmarketing;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FAQActivity extends LDLActivity implements OnClickListener {

	private TextView a1, a2, a3;
	private boolean flag1, flag2, flag3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq_layout);
		
		TextView q1 = (TextView) findViewById(R.id.faq1);
		q1.setOnClickListener(this);
		a1 = (TextView) findViewById(R.id.a1);
		a1.setVisibility(View.GONE);
		flag1 = false;

		TextView q2 = (TextView) findViewById(R.id.faq2);
		q2.setOnClickListener(this);
		a2 = (TextView) findViewById(R.id.a2);
		a2.setVisibility(View.GONE);
		flag2 = false;

		TextView q3 = (TextView) findViewById(R.id.faq3);
		q3.setOnClickListener(this);
		a3 = (TextView) findViewById(R.id.a3);
		a3.setVisibility(View.GONE);
		flag3 = false;
		
		q1.setTypeface(fontbold);
		q1.setTextSize(20);
		q2.setTypeface(fontbold);
		q2.setTextSize(20);
		q3.setTypeface(fontbold);
		q3.setTextSize(20);
		
		//a1.setTypeface(fontreg);
		a1.setTextSize(18);
		//a2.setTypeface(fontreg);
		a2.setTextSize(18);
		//a3.setTypeface(fontreg);
		a3.setTextSize(18);
	}
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.faq1:
			if (!flag1)
				a1.setVisibility(View.VISIBLE);
			else
				a1.setVisibility(View.GONE);
			flag1 = !flag1;
			break;
		case R.id.faq2:
			if (!flag2)
				a2.setVisibility(View.VISIBLE);
			else
				a2.setVisibility(View.GONE);
			flag2 = !flag2;
			break;
		case R.id.faq3:
			if (!flag3)
				a3.setVisibility(View.VISIBLE);
			else
				a3.setVisibility(View.GONE);
			flag3 = !flag3;
			break;
		}

	}

}
