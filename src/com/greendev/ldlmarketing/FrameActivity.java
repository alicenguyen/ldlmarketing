package com.greendev.ldlmarketing;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.actionbarsherlock.view.Menu;
import com.aviary.android.feather.FeatherActivity;

public class FrameActivity extends LDLFragmentActivity implements
		OnClickListener {

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

	protected String _path;
	protected String _output;
	protected File outFile;
	private static final int RESULT_FRAME_IMAGE = 1;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.picture_frame_layout);
		this.imgView = (ImageView) findViewById(R.id.imgView);
		_output = "output.jpg";

		Button frameB0 = (Button) findViewById(R.id.frame_b0);
		frameB0.setOnClickListener(this);

		Button frameB1 = (Button) findViewById(R.id.frame_b1);
		frameB1.setOnClickListener(this);

		Button frameB2 = (Button) findViewById(R.id.frame_b2);
		frameB2.setOnClickListener(this);

		Button frameB3 = (Button) findViewById(R.id.frame_b3);
		frameB3.setOnClickListener(this);

		Button frameB4 = (Button) findViewById(R.id.frame_b4);
		frameB4.setOnClickListener(this);

		Button frameB5 = (Button) findViewById(R.id.frame_b5);
		frameB5.setOnClickListener(this);

		Button frameB6 = (Button) findViewById(R.id.frame_b6);
		frameB6.setOnClickListener(this);

		Button frameB7 = (Button) findViewById(R.id.frame_b7);
		frameB7.setOnClickListener(this);

		Button frameB8 = (Button) findViewById(R.id.frame_b8);
		frameB8.setOnClickListener(this);

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

			finish();
			return (true);

		case R.id.save_option:
			savePhoto();
			finish();
			return (true);

		case R.id.cancel:
			finish();
			return (true);
		}

		return (super.onOptionsItemSelected(item));
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.frame_b0:
			int startFrame = -1;
			setFrame(startFrame);
			break;

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

		case R.id.frame_b5:
			setFrame(R.drawable.frame_5);
			break;

		case R.id.frame_b6:
			setFrame(R.drawable.frame_6);
			break;

		case R.id.frame_b7:
			setFrame(R.drawable.frame_7);
			break;

		case R.id.frame_b8:
			setFrame(R.drawable.frame_8);
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
		String photoFile = "ldl_" + date + ".jpg";

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
			Toast.makeText(context, "Saved " + photoFile, Toast.LENGTH_LONG)
					.show();
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
	public void onBackPressed() {
		photoEditor(outputUri);

	}

	private void photoEditor(Uri photo) {

		File path = getFilesDir();
		outFile = new File(path, _output);

		if (outFile == null) {
			outFile.delete();
		}

		Intent i = new Intent(this, FeatherActivity.class);
		// set the source image uri
		i.setData(photo);
		// pass the required api key ( http://developers.aviary.com/ )
		i.putExtra("API_KEY", "68bceceb1");
		// pass the uri of the destination image file (optional)
		// This will be the same uri you will receive in the
		// onActivityResult
		i.putExtra("output", Uri.parse("file://" + outFile.getAbsolutePath()));
		// format of the destination image (optional)
		// newIntent.putExtra( "output-format",
		// Bitmap.CompressFormat.JPEG.name() );
		// output format quality (optional)
		i.putExtra("output-quality", 85);
		// you can force feather to display only a certain tools
		// newIntent.putExtra( "tools-list", new String[]{"ADJUST",
		// "BRIGHTNESS"
		// } );

		// enable fast rendering preview
		i.putExtra("effect-enable-external-pack", false);
		i.putExtra("effect-enable-borders", false);
		i.putExtra("effect-enable-fast-preview", true);
		i.putExtra("stickers-enable-external-pack", false);

		startActivityForResult(i, RESULT_FRAME_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {

			case RESULT_FRAME_IMAGE:
				Intent j = new Intent(this, FrameActivity.class);
				j.setData(Uri.parse(outFile.getAbsolutePath()));

				startActivity(j);
				break;

			default:
				break;
			}
		}
		finish();
	}

	public void onDestory() {
		super.onDestroy();
		if (outFile != null) {
			outFile.delete();
		}
	}
}