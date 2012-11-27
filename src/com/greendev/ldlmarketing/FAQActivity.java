package com.greendev.ldlmarketing;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FAQActivity extends Activity implements OnClickListener {

	private TextView a1, a2, a3;
	private boolean flag1, flag2, flag3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");
		setContentView(R.layout.faq_layout);
		
		// customizing font in action bar
		this.getActionBar().setDisplayShowCustomEnabled(true);
		this.getActionBar().setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.title, null);
		TextView title = ((TextView)v.findViewById(R.id.title));
		title.setText(this.getTitle());
		title.setTypeface(font);
		//assign the view to the actionbar
		this.getActionBar().setCustomView(v);

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

	}
	
//	@Override 
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // Navigate "up" the demo structure to the launchpad activity.
//                // See http://developer.android.com/design/patterns/navigation.html for more.
//                NavUtils.navigateUpTo(this, new Intent(this, HomeActivity.class));
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    

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
