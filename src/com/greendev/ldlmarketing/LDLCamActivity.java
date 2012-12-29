package com.greendev.ldlmarketing;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.aviary.android.feather.FeatherActivity;

public class LDLCamActivity extends Activity implements OnClickListener {
	private static final int RESULT_LOAD_IMAGE = 1;
	private static final int RESULT_CAMERA_IMAGE = 2;
	private static final int RESULT_FRAME_IMAGE = 3;
	protected String _output;
	protected File outFile;
	protected File path;
	private Uri imageFileUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ldlcam_layout);

		_output = "output.jpg";
		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");

		// Texts Views II
		TextView titleText1 = (TextView) findViewById(R.id.ldlcam_text1);
		titleText1.setTypeface(font);
		TextView titleText2 = (TextView) findViewById(R.id.ldlcam_text2);
		titleText2.setTypeface(font);

		// Text View I
		TextView buttontv1 = (TextView) findViewById(R.id.pick_image_button);
		buttontv1.setTypeface(font);
		TextView buttontv2 = (TextView) findViewById(R.id.from_camera_button);
		buttontv2.setTypeface(font);

		// Button View
		Button pickButton = (Button) findViewById(R.id.pick_image_button);
		pickButton.setOnClickListener(this);

		Button cameraButton = (Button) findViewById(R.id.from_camera_button);
		cameraButton.setOnClickListener(this);
	}

	@SuppressLint("WorldWriteableFiles")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// from gallery button
		case R.id.pick_image_button:
			Intent photoIntent = new Intent(Intent.ACTION_PICK,
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(photoIntent, RESULT_LOAD_IMAGE);
			break;

		// from camera button
		case R.id.from_camera_button:
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				imageFileUri = getOutputImageFileUri(this);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);

				startActivityForResult(cameraIntent, RESULT_CAMERA_IMAGE);

			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {

			case RESULT_LOAD_IMAGE:
				if (data != null) {
					Uri selectedImage = data.getData();
					photoEditor(selectedImage);
				}
				break;

			case RESULT_CAMERA_IMAGE:
				Uri cameraImage = null;
				if (data != null) {
					cameraImage = data.getData();
				} else {
					/*File path = getFilesDir();

					File file = new File(path, _path);
					cameraImage = Uri.fromFile(file);*/
					cameraImage = imageFileUri;
				}

				photoEditor(cameraImage);
				break;

			case RESULT_FRAME_IMAGE:
				Intent j = new Intent(this, FrameActivity.class);
				j.setData(Uri.parse(outFile.getAbsolutePath()));
				startActivity(j);
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private void photoEditor(Uri photo) {

		File path = getFilesDir();
		outFile = new File(path, _output);

		if (outFile != null) {
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

	private static String getTempDirectoryPath(Context ctx) {
		File cache;

		// SD Card Mounted
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			cache = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ "/Android/data/"
					+ ctx.getPackageName() + "/cache/");
		}
		// Use internal storage
		else {
			cache = ctx.getCacheDir();
		}

		// Create the cache directory if it doesn't exist
		if (!cache.exists()) {
			cache.mkdirs();
		}

		return cache.getAbsolutePath();
	}

	public static Uri getOutputImageFileUri(Context ctx) {

		String tstamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File file = new File(getTempDirectoryPath(ctx), "IMG_" + tstamp
				+ ".jpg");

		return Uri.fromFile(file);

	}

	public void onDestory() {
		super.onDestroy();
		if (outFile != null) {
			outFile.delete();
		}
	}

}
