package com.greendev.image;

import com.greendev.image.ImageCache.ImageCacheParams;
import com.greendev.image.ImageGridFragment.ImageAdapter;
import com.greendev.ldlmarketing.R;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
			
		this.setImgs = setImgs;
		this.setThumbs = setThumbs;
		this.setDesc = setDesc;
	}
	
	
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		/* transferring information to ImageDeatilActivity */
		Bundle b = new Bundle(); 
		b.putStringArray("TYPE_URL", ((String[])setImgs[position]));
		b.putStringArray("TYPE_URL_THUMB", ((String[])setThumbs[position]));
		b.putStringArray("CAPTIONS", ((String[])setDesc[position]));
		b.putString("TITLE", "Test");

		/* Loops back to ImageGrid */
		final Intent i = new Intent(context, ImageGridActivity.class);
		i.putExtras(b);

		startActivity(i);
	}


	
	
	
	
	
	

}
