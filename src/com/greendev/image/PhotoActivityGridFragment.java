package com.greendev.image;

import com.greendev.image.ImageCache.ImageCacheParams;
import com.greendev.image.ImageGridFragment.ImageAdapter;
import com.greendev.ldlmarketing.R;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class PhotoActivityGridFragment extends ImageGridFragment {
	String[] setUrls;
	String[] captions;
	private Context context;
	
	// additions
	String[][] setsOfImages;
	String[][] setsOfThumbs;
	int position;
	
	/* Array Objects of sets attributes */
	Object[] setImgs;
	Object[] setThumbs;
	Object[] setDesc;
	
	String[] test;
	
	
/*	public PhotoActivityGridFragment(Context cxt, Class<?> c,
			String[] setUrls, String[] setThumbs, String[] captions) {
		super(cxt, c, setUrls, setThumbs, captions);
		this.setUrls = setUrls;
		this.captions = captions;
		this.context = cxt;
	
	}*/
	
	public PhotoActivityGridFragment(Context cxt, Class<?> c, String[] thumbs, 
			Object[] setImgs, Object[] setThumbs, Object[] setDesc) {
		super(cxt, c, thumbs);
		context = cxt;
		this.setImgs = setImgs;
		this.setThumbs = setThumbs;
		this.setDesc = setDesc;
		this.test = thumbs;
	}
	
	
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		/* transferring information to ImageDeatilActivity */
		Bundle b = new Bundle(); 
		b.putStringArray("TYPE_URL", ((String[])setImgs[position-2]));
		b.putStringArray("TYPE_URL_THUMB", ((String[])setThumbs[position-2]));
		b.putStringArray("CAPTIONS", ((String[])setDesc[position-2]));
		b.putString("TITLE", "Test");
		
//		b.putStringArray("TYPE_URL", test);
//		b.putStringArray("TYPE_URL_THUMB", test);
//		// test
//		b.putStringArray("CAPTIONS", test);
//		b.putString("TITLE", "test");
		
		/* test */
		String[] test = (String[])setThumbs[0];
		for(int i = 0; i < test.length; i++){
		Log.i("PhotoActivityGridFragment: ", test[i].toString());
		}

		/* Loops back to ImageGrid */
		final Intent i = new Intent(context, ImageGridActivity.class);
		Log.i("hello???", "1");
		i.putExtras(b);
		Log.i("About to start ImageGridActivity in PhotoActivityGridFragment: ", i.toString());
		Log.i("position clicked: " ,position+"");
		startActivity(i);
	}


	
	
	
	
	
	

}
