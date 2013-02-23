package com.greendev.ldlmarketing;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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
	//private Uri imageFileUri;
	private Uri mImageCaptureUri;
	private Uri mImageCaptureUri_samsung;

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

			String storageState = Environment.getExternalStorageState();

			if (storageState.equals(Environment.MEDIA_MOUNTED)) {
				Intent cameraIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);

				String filename = System.currentTimeMillis() + ".jpg";
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.TITLE, filename);
				mImageCaptureUri = getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

				cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
						mImageCaptureUri);

				try {

					startActivityForResult(cameraIntent, RESULT_CAMERA_IMAGE);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				new AlertDialog.Builder(this)
						.setMessage(
								"External Storeage (SD Card) is required.\n\nCurrent state: "
										+ storageState).setCancelable(true)
						.create().show();
				
				/*
				 * Intent cameraIntent = new
				 * Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				 * 
				 * try { imageFileUri = getOutputImageFileUri(this); } catch
				 * (IOException e) { 
				 * e.printStackTrace(); }
				 * cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
				 * 
				 * startActivityForResult(cameraIntent, RESULT_CAMERA_IMAGE);
				 */
			}

			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {

			case RESULT_LOAD_IMAGE:
				if (data != null) {

					// performing task on a separate thread.
					new Thread(new Runnable() {
						public void run() {
							Uri selectedImage = data.getData();
							photoEditor(selectedImage);
						}
					}).start();
				}
				break;

			case RESULT_CAMERA_IMAGE:
				Log.i("TAG", "Inside PICK_FROM_CAMERA");

                // Final Code As Below
                try {
                    Log.i("TAG", "inside Samsung Phones");
                    String[] projection = {
                            MediaStore.Images.Thumbnails._ID, // The columns we want
                            MediaStore.Images.Thumbnails.IMAGE_ID,
                            MediaStore.Images.Thumbnails.KIND,
                            MediaStore.Images.Thumbnails.DATA };
                    String selection = MediaStore.Images.Thumbnails.KIND + "=" + // Select
                                                                                    // only
                                                                                    // mini's
                            MediaStore.Images.Thumbnails.MINI_KIND;

                    String sort = MediaStore.Images.Thumbnails._ID + " DESC";

                    // At the moment, this is a bit of a hack, as I'm returning ALL
                    // images, and just taking the latest one. There is a better way
                    // to narrow this down I think with a WHERE clause which is
                    // currently the selection variable
                    Cursor myCursor = getContentResolver().query(
                            MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                            projection, selection, null, sort);

                    long imageId = 0l;
                    long thumbnailImageId = 0l;
                    String thumbnailPath = "";

                    try {
                        myCursor.moveToFirst();
                        imageId = myCursor
                                .getLong(myCursor
                                        .getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
                        thumbnailImageId = myCursor
                                .getLong(myCursor
                                        .getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID));
                        thumbnailPath = myCursor
                                .getString(myCursor
                                        .getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
                    } finally {
                        // myCursor.close();
                    }

                    // Create new Cursor to obtain the file Path for the large image

                    String[] largeFileProjection = {
                            MediaStore.Images.ImageColumns._ID,
                            MediaStore.Images.ImageColumns.DATA };

                    String largeFileSort = MediaStore.Images.ImageColumns._ID
                            + " DESC";
                    myCursor = getContentResolver().query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            largeFileProjection, null, null, largeFileSort);
                    String largeImagePath = "";

                    try {
                        myCursor.moveToFirst();

                        // This will actually give yo uthe file path location of the
                        // image.
                        largeImagePath = myCursor
                                .getString(myCursor
                                        .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
                        mImageCaptureUri_samsung = Uri.fromFile(new File(
                                largeImagePath));
                        mImageCaptureUri = null;
                    } finally {
                        // myCursor.close();
                    }

                    // These are the two URI's you'll be interested in. They give
                    // you a
                    // handle to the actual images
                    Uri uriLargeImage = Uri.withAppendedPath(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            String.valueOf(imageId));
                    Uri uriThumbnailImage = Uri.withAppendedPath(
                            MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                            String.valueOf(thumbnailImageId));

                    // I've left out the remaining code, as all I do is assign the
                    // URI's
                    // to my own objects anyways...
                } catch (Exception e) {
                    mImageCaptureUri_samsung = null;
                    Log.i("TAG",
                            "inside catch Samsung Phones exception " + e.toString());

                }

                try {
                    Log.i("TAG",
                            "URI Samsung:" + mImageCaptureUri_samsung.getPath());

                } catch (Exception e) {
                    Log.i("TAG", "Excfeption inside Samsung URI :" + e.toString());
                }

                try {

                    Log.i("TAG", "URI Normal:" + mImageCaptureUri.getPath());
                } catch (Exception e) {
                    Log.i("TAG", "Excfeption inside Normal URI :" + e.toString());
                }

				
				/*String[] projection = {
						MediaStore.Images.Thumbnails._ID, // The columns we want
						MediaStore.Images.Thumbnails.IMAGE_ID,
						MediaStore.Images.Thumbnails.KIND,
						MediaStore.Images.Thumbnails.DATA };
				String selection = MediaStore.Images.Thumbnails.KIND + "="
						+ MediaStore.Images.Thumbnails.MINI_KIND;

				String sort = MediaStore.Images.Thumbnails._ID + " DESC";

				Cursor myCursor = getContentResolver().query(
						MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
						projection, selection, null, sort);

				long imageId = 0l;
				long thumbnailImageId = 0l;
				String thumbnailPath = "";

				try {
					myCursor.moveToFirst();
					imageId = myCursor
							.getLong(myCursor
									.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
					thumbnailImageId = myCursor
							.getLong(myCursor
									.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID));
					thumbnailPath = myCursor
							.getString(myCursor
									.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
				} finally {
					myCursor.close();
				}

				// Create new Cursor to obtain the file Path for the large image

				String[] largeFileProjection = {
						MediaStore.Images.ImageColumns._ID,
						MediaStore.Images.ImageColumns.DATA };

				String largeFileSort = MediaStore.Images.ImageColumns._ID
						+ " DESC";
				myCursor = getContentResolver().query(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						largeFileProjection, null, null, largeFileSort);
				String largeImagePath = "";

				try {
					myCursor.moveToFirst();

					// This will actually give you the file path location of the
					// image.
					largeImagePath = myCursor
							.getString(myCursor
									.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
				} finally {
					myCursor.close();
				}
				// These are the two URI's you'll be interested in. They give
				// you a handle to the actual images
				Uri uriLargeImage = Uri.withAppendedPath(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						String.valueOf(imageId));
				Uri uriThumbnailImage = Uri.withAppendedPath(
						MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
						String.valueOf(thumbnailImageId));*/

				/*
				 * 
				 * Uri cameraImage = null; if (data != null) { cameraImage =
				 * data.getData(); } else { /* File path = getFilesDir();
				 * 
				 * File file = new File(path, _path); cameraImage =
				 * Uri.fromFile(file);
				 * 
				 * cameraImage = imageFileUri; }
				 */

				photoEditor(mImageCaptureUri_samsung);
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

		// System.out.println(photo);
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

	public static Uri getOutputImageFileUri(Context ctx) throws IOException {

		String tstamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File file = new File(getTempDirectoryPath(ctx), "IMG_" + tstamp
				+ ".jpg");
		if (!file.exists()) {
			file.createNewFile();
		}
		return Uri.fromFile(file);

	}

	public void onDestory() {
		super.onDestroy();
		if (outFile != null) {
			outFile.delete();
		}
	}

}
