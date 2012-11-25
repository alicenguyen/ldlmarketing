package com.greendev.camera;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.greendev.ldlmarketing.R;

public class FrameActivity extends Activity implements OnClickListener {

	protected String _output = "output.jpg";
	private File path = getFilesDir();
	File outFile = new File(path, _output);

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_frame_layout);

		Bitmap pic = BitmapFactory.decodeFile(outFile.getAbsolutePath());
		Bitmap map = BitmapFactory.decodeResource(getResources(),
				R.drawable.frame1);
		Bitmap out1 = Bitmap.createBitmap(pic.getHeight() + 10, pic.getWidth() + 10, null);
		Canvas comboImage = new Canvas(out1);
		comboImage.drawBitmap(map,0,0, null);
		comboImage.drawBitmap(pic, 5, 5, null);

		ImageView imgView = (ImageView) findViewById(R.id.imgView);
		imgView.setImageBitmap(out1);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}