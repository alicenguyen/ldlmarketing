package com.greendev.image;

import com.greendev.ldlmarketing.R;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;

public class PhotoActivityGridFragment extends ImageGridFragment {
	String[] setUrls;
	String[] captions;
	private Context context;
	
	// additions
	String[][] setsOfImages;
	String[][] setsOfThumbs;
	int position;
	
	public PhotoActivityGridFragment(Context cxt, Class<?> c,
			String[] setUrls, String[] setThumbs, String[] captions) {
		super(cxt, c, setUrls, setThumbs, captions);
		this.setUrls = setUrls;
		this.captions = captions;
		this.context = cxt;
		

		
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		/* transferring information to ImageDeatilActivity */
		Bundle b = new Bundle(); 
		b.putStringArray("TYPE_URL", setUrls);
		b.putStringArray("TYPE_URL_THUMB", setUrls);
		b.putStringArray("CAPTIONS", captions);
		b.putString("TITLE", "Test");
		// additional extras

	
		final Intent i = new Intent(context, ImageGridActivity.class);
		i.putExtras(b);
		//i.putExtra(ImageDetailActivity.EXTRA_IMAGE, (int) id);
//	
//		if (Utils.hasJellyBean()) {
//			// makeThumbnailScaleUpAnimation() looks kind of ugly here as the
//			// loading spinner may
//			// show plus the thumbnail image in GridView is cropped. so using
//			// makeScaleUpAnimation() instead.
//			ActivityOptions options = ActivityOptions.makeScaleUpAnimation(v,
//					0, 0, v.getWidth(), v.getHeight());
//			context.startActivity(i, options.toBundle());
//		} else {
//			startActivity(i);
//		}
		startActivity(i);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_menu, menu);
	}

}
