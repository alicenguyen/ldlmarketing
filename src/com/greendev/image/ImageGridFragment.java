/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.greendev.image;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greendev.image.ImageCache.ImageCacheParams;
import com.greendev.ldlmarketing.BuildConfig;
import com.greendev.ldlmarketing.R;

/**
 * The main fragment that powers the ImageGridActivity screen. Fairly straight
 * forward GridView implementation with the key addition being the ImageWorker
 * class w/ImageCache to load children asynchronously, keeping the UI nice and
 * smooth and caching thumbnails for quick retrieval. The cache is retained over
 * configuration changes like orientation change so the images are populated
 * quickly if, for example, the user rotates the device.
 */
public class ImageGridFragment extends Fragment implements
		AdapterView.OnItemClickListener {
	private static final String TAG = "ImageGridFragment";
	private static final String IMAGE_CACHE_DIR = "thumbs";

	private int mImageThumbSize;
	private int mImageThumbSpacing;
	protected ImageAdapter mAdapter;
	protected ImageWorker mImageFetcher;
	private Context context;
	Class<?> detailClass;
	private String[] typeUrls;
	protected String[] typeUrlsThumbs; 
	private String[] captions;

	/**
	 * Constructor as per the Fragment documentation
	 */
	public ImageGridFragment(Context cxt, Class<?> c, String[] typeUrls,
			String[] thumbs, String[] captions) {
		this.context = cxt;
		this.detailClass = c;
		this.typeUrls = typeUrls;
		typeUrlsThumbs = thumbs;
		this.captions = captions;
	}

	public ImageGridFragment(Context cxt, Class<?> c, String[] thumbs) {
		this.context = cxt;
		this.detailClass = c;
		typeUrlsThumbs = thumbs;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
				
		setHasOptionsMenu(true); // true

		mImageThumbSize = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_size);
		mImageThumbSpacing = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_spacing);

		mAdapter = new ImageAdapter(context);

		ImageCacheParams cacheParams = new ImageCacheParams(context,
				IMAGE_CACHE_DIR);

		// Set memory cache to 25% of mem class
		cacheParams.setMemCacheSizePercent(context, 0.25f);

		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageFetcher = new ImageFetcher(context, mImageThumbSize);
		//mImageFetcher.setLoadingImage(R.drawable.empty_photo);  
		
		mImageFetcher.addImageCache(
				((FragmentActivity) context).getSupportFragmentManager(),
				cacheParams);
		} catch(Exception e){
			Log.e(TAG, "error in onCreate()");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		final View v = inflater.inflate(R.layout.image_grid_fragment,
				container, false);
		final GridView mGridView = (GridView) v.findViewById(R.id.gridView);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(this);
		mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView,
					int scrollState) {
				// Pause fetcher to ensure smoother scrolling when flinging
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
					mImageFetcher.setPauseWork(true);
				} else {
					mImageFetcher.setPauseWork(false);
				}
			}

			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});

		// This listener is used to get the final width of the GridView and then
		// calculate the
		// number of columns and the width of each column. The width of each
		// column is variable
		// as the GridView has stretchMode=columnWidth. The column width is used
		// to set the height
		// of each view so we get nice square thumbnails.
		mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						if (mAdapter.getNumColumns() == 0) {
							final int numColumns = (int) Math.floor(mGridView
									.getWidth()
									/ (mImageThumbSize + mImageThumbSpacing));
							if (numColumns > 0) {
								final int columnWidth = (mGridView.getWidth() / numColumns)
										- mImageThumbSpacing;
								mAdapter.setNumColumns(numColumns);
								mAdapter.setItemHeight(columnWidth);
								if (BuildConfig.DEBUG) {
									Log.d(TAG,
											"onCreateView - numColumns set to "
													+ numColumns);
								}
							}
						}
					}
				});
		
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		mImageFetcher.setExitTasksEarly(false);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPause() {
		super.onPause();
		mImageFetcher.setExitTasksEarly(true);
		//mImageFetcher.flushCache();   
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mImageFetcher.closeCache();
	}

	@TargetApi(16)
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		/* transferring information to ImageDetailActivity */
		Bundle b = new Bundle();
		b.putStringArray("URLS_TYPE", typeUrls);
		b.putStringArray("CAPTIONS", captions);

		final Intent i = new Intent(context, ImageDetailActivity.class);
		i.putExtras(b);
		i.putExtra(ImageDetailActivity.EXTRA_IMAGE, (int) id);

		if (Utils.hasJellyBean()) {
			// makeThumbnailScaleUpAnimation() looks kind of ugly here as the
			// loading spinner may
			// show plus the thumbnail image in GridView is cropped. so using
			// makeScaleUpAnimation() instead.
			ActivityOptions options = ActivityOptions.makeScaleUpAnimation(v,
					0, 0, v.getWidth(), v.getHeight());
			context.startActivity(i, options.toBundle());
		} else {
			startActivity(i);
		}
	}

//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		inflater.inflate(R.menu.main_menu, menu);
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.clear_cache:
			mImageFetcher.clearCache();
			Toast.makeText(context, R.string.clear_cache_complete_toast,
					Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * The main adapter that backs the GridView. This is fairly standard except
	 * the number of columns in the GridView is used to create a fake top row of
	 * empty views as we use a transparent ActionBar and don't want the real top
	 * row of images to start off covered by it.
	 */
	protected class ImageAdapter extends BaseAdapter {

		private final Context mContext;
		private int mItemHeight = 0;
		private int mNumColumns = 0;
		private int mActionBarHeight = 0;
		private GridView.LayoutParams mImageViewLayoutParams;

		public ImageAdapter(Context context) {
			super();
			mContext = context;
			mImageViewLayoutParams = new GridView.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			/**
			 * weird bug when below.  creates a white gap above the grid. size of action bar probs. 
			 */
			// Calculate ActionBar height
			/*TypedValue tv = new TypedValue();
			if (context.getTheme().resolveAttribute(
					android.R.attr.actionBarSize, tv, true)) {
				mActionBarHeight = TypedValue.complexToDimensionPixelSize(
						tv.data, context.getResources().getDisplayMetrics());
			}*/
		}

		@Override
		public int getCount() {
			Log.i("getCOunt() is: ", typeUrlsThumbs.length + " ");
			// Size + number of columns for top empty row
			return typeUrlsThumbs.length + mNumColumns;
		}

		@Override
		public Object getItem(int position) {
			return position < mNumColumns ? null : typeUrlsThumbs[position
					- mNumColumns];
		}

		@Override
		public long getItemId(int position) {
			return position < mNumColumns ? 0 : position - mNumColumns;
		}

		@Override
		public int getViewTypeCount() {
			// Two types of views, the normal ImageView and the top row of empty
			// views
			return 2;
		}

		@Override
		public int getItemViewType(int position) {
			return (position < mNumColumns) ? 1 : 0;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		/*
		 * Displays the thumbnails
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup container) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View gridView;

			// First check if this is the top row
			if (position < mNumColumns) {
				if (convertView == null) {
					convertView = new View(mContext);
				}
				// Set empty view with height of ActionBar
				convertView.setLayoutParams(new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, mActionBarHeight));
				return convertView;
			}

			ImageView imageView;
			/* The infamous album label */
			//TextView textView;
			if (convertView == null) { // if it's not recycled, instantiate and
										// initialize
				gridView = new View(mContext);
				// get layout from mobile.xml
				gridView = (View) inflater.inflate(R.layout.image_grid_item,
						null);
				View blackShadow = gridView.findViewById(R.id.blackShadow);
				blackShadow.setVisibility(ImageView.GONE); 
				
				imageView = (ImageView) gridView.findViewById(R.id.fragItem);
				mImageFetcher.loadImage(typeUrlsThumbs[position - mNumColumns],
						imageView);

				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				gridView.setLayoutParams(mImageViewLayoutParams);
				
				
//				textView = (TextView) gridView.findViewById(R.id.fragText);
//				textView.setText(setsNames[position - mNumColumns]);
			} else { // Otherwise re-use the converted view
				// imageView = (ImageView) convertView;
				gridView = (View) convertView;
				
				imageView = (ImageView) gridView.findViewById(R.id.fragItem);
				mImageFetcher.loadImage(typeUrlsThumbs[position - mNumColumns],
						imageView);
				
//				textView = (TextView) gridView.findViewById(R.id.fragText);
//				textView.setText(setsNames[position - mNumColumns]);
			}

			// Check the height matches our calculated column width
			if (gridView.getLayoutParams().height != mItemHeight) {
				gridView.setLayoutParams(mImageViewLayoutParams);
				// imageView.setLayoutParams(mImageViewLayoutParams);
			}

			return (View) gridView;
		}
		/**
		 * Sets the item height. Useful for when we know the column width so the
		 * height can be set to match.
		 * 
		 * @param height
		 */
		public void setItemHeight(int height) {
			if (height == mItemHeight) {
				return;
			}
			mItemHeight = height;
			mImageViewLayoutParams = new GridView.LayoutParams( 
					LayoutParams.MATCH_PARENT, mItemHeight);
			((ImageResizer) mImageFetcher).setImageSize(height);
			notifyDataSetChanged();
		}

		public void setNumColumns(int numColumns) {
			mNumColumns = numColumns;
		}

		public int getNumColumns() {
			return mNumColumns;
		}
	}
}
