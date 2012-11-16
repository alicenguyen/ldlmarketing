package com.greendev.ldlmarketing;

import java.io.File;
import com.aviary.android.feather.FeatherActivity;
import com.greendev.ldlmarketing.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LDLCamActivity extends Activity implements OnClickListener {
	private static final int RESULT_LOAD_IMAGE = 1;
	private static final int RESULT_CAMERA_IMAGE = 666;
	protected String _path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ldlcam_layout);

		_path = "temp.jpg";
		// Custom Font
		Typeface font = Typeface.createFromAsset(getAssets(), "Eurosti.TTF");

		// Texts Views
		TextView titleText1 = (TextView) findViewById(R.id.ldlcam_text1);
		titleText1.setTypeface(font);
		TextView titleText2 = (TextView) findViewById(R.id.ldlcam_text2);
		titleText2.setTypeface(font);

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
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(i, RESULT_LOAD_IMAGE);
			break;

		// from camera button
		case R.id.from_camera_button:
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

			File path = getFilesDir();
			File file = new File(path, "test_picture.jpg");
			Uri uri = Uri.fromFile(file);
			cameraIntent
					.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);

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
					File path = getFilesDir();

					File file = new File(path, "test_picture.jpg");
					cameraImage = Uri.fromFile(file);
				}
				
				photoEditor(cameraImage);
				break;

			default:
				break;

			}
		}
	}

	private void photoEditor(Uri photo) {

		Intent i = new Intent(this, FeatherActivity.class);
		// set the source image uri
		i.setData(photo);
		// pass the required api key ( http://developers.aviary.com/ )
		i.putExtra("API_KEY", "68bceceb1");
		// pass the uri of the destination image file (optional)
		// This will be the same uri you will receive in the
		// onActivityResult
		// newIntent.putExtra( "output", Uri.parse( "file://" +
		// mOutputFile.getAbsolutePath() ) );
		// format of the destination image (optional)
		// newIntent.putExtra( "output-format",
		// Bitmap.CompressFormat.JPEG.name() );
		// output format quality (optional)
		i.putExtra("output-quality", 85);
		// you can force feather to display only a certain tools
		// newIntent.putExtra( "tools-list", new String[]{"ADJUST",
		// "BRIGHTNESS" } );

		// enable fast rendering preview
		i.putExtra("effect-enable-fast-preview", true);
		i.putExtra("hide-exit-unsave-confirmation", true);
		startActivity(i);

	}

}
