package com.greendev.ldlmarketing;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;

public class FrameActivity extends LDLFragmentActivity implements OnClickListener, ActionBar.TabListener {

	protected Uri outputUri;
	private Intent intent;
	private int frameWidth = 50;
	private int frameHeight = 50;
	private int picWidth;
	private int picHeight;
	private Bitmap pic;
	private Bitmap outpic;
	private ImageView imgView;
	private Context context;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.picture_frame_layout);
		this.imgView = (ImageView) findViewById(R.id.imgView);

//		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//	
//
//		Tab newTab0 = ab.newTab();
//    	newTab0.setText("1");
//    	Tab newTab1 = ab.newTab();
//    	newTab1.setText("2");
//    	Tab newTab2 = ab.newTab();
//    	newTab2.setText("3");
//    	Tab newTab3 = ab.newTab();
//    	newTab3.setText("4");
//    	
//    	
//    	newTab0.setTabListener((TabListener) this);
//    	newTab1.setTabListener((TabListener) this);
//    	newTab2.setTabListener((TabListener) this);
//    	newTab3.setTabListener((TabListener) this);
//    	
//    	
//    	ab.addTab(newTab0);
//    	ab.addTab(newTab1);
//    	ab.addTab(newTab2);
//    	ab.addTab(newTab3);


		Button frameB1 = (Button) findViewById(R.id.frame_b1);
		frameB1.setOnClickListener(this);
		// frameB1.setBackground((makeThumb(R.id.frame_b1)).mutate());
		// makeThumb(frameB1, R.id.frame_b1);

		Button frameB2 = (Button) findViewById(R.id.frame_b2);
		frameB2.setOnClickListener(this);
		// frameB2.setBackground((makeThumb(R.id.frame_b2)).mutate());
		// makeThumb(frameB2, R.id.frame_b2);

		Button frameB3 = (Button) findViewById(R.id.frame_b3);
		frameB3.setOnClickListener(this);
		// frameB3.setBackground((makeThumb(R.id.frame_b3)).mutate());
		// makeThumb(frameB3, R.id.frame_b3);

		Button frameB4 = (Button) findViewById(R.id.frame_b4);
		frameB4.setOnClickListener(this);
		// frameB4.setBackground((makeThumb(R.id.frame_b4)).mutate());
		// makeThumb(frameB4, R.id.frame_b4);

		intent = this.getIntent();

		if (intent.getData() != null) {
			outputUri = intent.getData();

			pic = BitmapFactory.decodeFile(outputUri.getPath());

			this.picWidth = pic.getWidth();
			this.picHeight = pic.getHeight();
			// Load the image as a NinePatch drawable
			int startFrame = -1;

			setFrame(startFrame);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_frame_activity,
				(com.actionbarsherlock.view.Menu) menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case R.id.share_save:
			File temp = savePhoto();
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("image/jpeg");
			share.putExtra(Intent.EXTRA_SUBJECT, getText(R.string.app_name));
			share.putExtra(Intent.EXTRA_TEXT,
					"Just took this picture with LDL Marketing App! ");
			share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(temp));
			startActivity(Intent.createChooser(share, "Share using"));
			onDestroy();
			return (true);

		case R.id.save_option:
			savePhoto();
			onDestroy();
			return (true);
		}

		return (super.onOptionsItemSelected(item));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.frame_b1:
			setFrame(R.drawable.frame_1);
			break;

		case R.id.frame_b2:
			setFrame(R.drawable.frame_2);
			break;

		case R.id.frame_b3:
			setFrame(R.drawable.frame_3);
			break;

		case R.id.frame_b4:
			setFrame(R.drawable.frame_4);
			break;
		}

	}

	private void setFrame(int frame) {

		int width = picWidth + frameWidth;
		int height = picHeight + frameHeight;

		// Finally draw on the canvas
		this.outpic = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas comboImage = new Canvas(outpic);
		// comboImage.drawBitmap(map, 0, 0, null);
		comboImage.drawBitmap(pic, (frameWidth / 2), (frameHeight / 2), null);

		if (frame != -1) {
			// Load the image as a NinePatch drawable
			Drawable d = this.getResources().getDrawable(frame);

			NinePatchDrawable npd = (NinePatchDrawable) d;
			// Set its bound where you need
			Rect npdBounds = new Rect(0, 0, width, height);
			npd.setBounds(npdBounds);
			npd.draw(comboImage);
		}

		imgView.setImageBitmap(outpic);
	}

	private void makeThumb(Button b, int frameIn) {

		Drawable d = this.getResources().getDrawable(frameIn);
		Rect bounds = new Rect(0, 0, 100, 100);

		b.setBackground(d);
		// NinePatchDrawable frame = (NinePatchDrawable) d;
		// Set its bound where you need

		// frame.setBounds(bounds);

	}

	private File savePhoto() {
		File pictureFileDir = Environment.getExternalStorageDirectory();

		if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
			Toast.makeText(context, "Can't create directory to save image.",
					Toast.LENGTH_LONG).show();
			return null;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		String date = dateFormat.format(new Date());
		String photoFile = "Picture_" + date + ".jpg";

		String filename = pictureFileDir.getPath() + File.separator + photoFile;

		File pictureFile = new File(filename);

		try {
			FileOutputStream fos = new FileOutputStream(pictureFile);
			// fos.write(data);
			outpic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			MediaStore.Images.Media.insertImage(getContentResolver(),
					pictureFile.getAbsolutePath(), pictureFile.getName(),
					pictureFile.getName());
			Toast.makeText(context, "New Image saved:" + photoFile,
					Toast.LENGTH_LONG).show();
		} catch (Exception error) {
			Toast.makeText(context, "Image could not be saved.",
					Toast.LENGTH_LONG).show();
		}

		return pictureFile;
	}

	private File getDir() {
		File sdDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		return new File(sdDir, "ldlMarketing");
	}

	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onTabSelected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	
}