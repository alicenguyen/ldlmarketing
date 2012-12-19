package com.greendev.ldlmarketing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.aviary.android.feather.FeatherActivity;
import com.greendev.ldlmarketing.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
	private static final int RESULT_CAMERA_IMAGE = 2;
	private static final int RESULT_FRAME_IMAGE = 3;
	protected String _path;
	protected String _output;
	protected File outFile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ldlcam_layout);

		_path = "temp.jpg";
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
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(photoIntent, RESULT_LOAD_IMAGE);
			break;

		// from camera button
		case R.id.from_camera_button:
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			try {
				FileOutputStream fos = openFileOutput(_path, Context.MODE_WORLD_WRITEABLE);

				fos.close();
				File path = getFilesDir();
				File file = new File(path, _path);
				Uri uri = Uri.fromFile(file);
				cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,	uri);

				startActivityForResult(cameraIntent, RESULT_CAMERA_IMAGE);
			} catch (IOException ie) {

			}
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

					File file = new File(path, _path);
					cameraImage = Uri.fromFile(file);
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

	private void photoEditor(Uri photo) {

		File path = getFilesDir();
		outFile = new File(path, _output);
		
		if(outFile != null){
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
		i.putExtra("output", Uri.parse(outFile.getAbsolutePath()));
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
		i.putExtra("effect-enable-fast-preview", true);
	    i.putExtra("stickers-enable-external-pack", false);
		startActivityForResult(i, RESULT_FRAME_IMAGE);
		
	}
	
	public void onDestory(){
		super.onDestroy();
		if(outFile != null){
			outFile.delete();
		}
	}

}
