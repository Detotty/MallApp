package com.mallapp.imagecapture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mallapp.Constants.GlobelProfile;
import com.mallapp.SharedPreferences.SharedPreferenceManager;
import com.mallapp.View.R;
import com.mallapp.imagecapture.ScalingUtilities.ScalingLogic;

public class ImageImportHelper {

	// public static final int ACTION_TAKE_PHOTO_BIG = 1;
	// public static final int ACTION_TAKE_PHOTO_SMALL = 2;
	public static final int ACTION_TAKE_PHOTO_IMAGEVIEW = 11;
	public static final int ACTION_TAKE_PHOTO_FROM_GALLERY = 22;
	public static final int DEFAULT_WIDTH_HEIGHT_PROFILE_IMAGE = 300;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	private static final String TAG = ImageImportHelper.class.getSimpleName();

	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
	private String mCurrentPhotoPath;
	private static ImageImportHelper instance;
	private Context mContext;

	private ImageImportHelper(Context context) {
		super();
		mContext = context;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}
	}

	public static ImageImportHelper getInstance(Context context) {
		if (instance == null)
			instance = new ImageImportHelper(context);

		return instance;
	}

	/**
	 * Indicates whether the specified action can be used as an intent. This method queries the package manager for installed packages 
	 * that can respond to an intent with the specified action. If no suitable package is found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 * 
	 * @param context
	 *            The application's environment.
	 * @param action
	 *            The Intent action to check for availability.
	 * 
	 * @return True if an Intent with the specified action can be sent and responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	public void setBtnListenerOrDisable(View btn, Button.OnClickListener onClickListener, String intentName) {
		if (isIntentAvailable(mContext, intentName)) {
			btn.setOnClickListener(onClickListener);
		} else {
			// btn.setText(mContext.getText(R.string.cannot).toString() + " " + btn.getText());
			Toast.makeText(mContext, "Cannot! Try again", Toast.LENGTH_LONG).show();
			btn.setClickable(false);
		}
	}

	public void setBtnListenerOrDisable(View view, Button.OnClickListener onClickListener) {
		setBtnListenerOrDisable(view, onClickListener, MediaStore.ACTION_IMAGE_CAPTURE);
	}

	private File setUpPhotoFile() throws IOException {

		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();

		return f;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}

	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}
		} else {
			Log.v(mContext.getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		return storageDir;
	}

	/* Photo album for this application */
	private String getAlbumName() {
		return mContext.getString(R.string.app_name).replace(" ", "-");
	}

	private void setPic(ImageView imageView, boolean isRounded) {
		
		SharedPreferenceManager.saveString(mContext, GlobelProfile.profile_image, mCurrentPhotoPath);
		if (isRounded) {
			setRoundedImgeToImageView(imageView, BitmapFactory.decodeFile(mCurrentPhotoPath));
		} else {

			/* There isn't enough memory to open up more than a couple camera photos */
			/* So pre-scale the target bitmap into which the file is decoded */
			/* Get the size of the ImageView */

			int targetW = imageView.getWidth();
			int targetH = imageView.getHeight();

			/* Get the size of the image */
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
			int photoW = bmOptions.outWidth;
			int photoH = bmOptions.outHeight;

			/* Figure out which way needs to be reduced less */
			int scaleFactor = 1;
			if ((targetW > 0) || (targetH > 0)) {
				scaleFactor = Math.min(photoW / targetW, photoH / targetH);
			}

			/* Set bitmap options to scale the image decode target */
			bmOptions.inJustDecodeBounds = false;
			bmOptions.inSampleSize = scaleFactor;
			bmOptions.inPurgeable = true;

			/* Decode the JPEG file into a Bitmap */
			Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
			bitmap =Image_Scaling.getImageOrintation(bitmap, mCurrentPhotoPath);
			/* Associate the Bitmap to the ImageView */
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
		}
	}

//	private void galleryAddPic() {
//		Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
//		File f = new File(mCurrentPhotoPath);
//		Uri contentUri = Uri.fromFile(f);
//		mediaScanIntent.setData(contentUri);
//		mContext.sendBroadcast(mediaScanIntent);
//	}

	public void handleSmallCameraPhoto(Intent intent, ImageView imageView, Bitmap bitmap) {

		Bundle extras = intent.getExtras();
		bitmap = (Bitmap) extras.get("data");
		String imagePath = getImageUri(mContext, bitmap).getPath();
		Log.i(TAG, "" + imagePath);
		SharedPreferenceManager.saveString(mContext, GlobelProfile.profile_image, imagePath);
		setRoundedImgeToImageView(imageView, bitmap);
		// imageView.setImageBitmap(bitmap);
		imageView.setVisibility(View.VISIBLE);
	}

	public void handleBigCameraPhoto(Intent intent, ImageView imageView, Bitmap bitmap) {
	
		Bundle extras = intent.getExtras();
		bitmap = (Bitmap) extras.get("data");
		setFlatImageToImageView(imageView, bitmap);
		imageView.setVisibility(View.VISIBLE);
		// if (mCurrentPhotoPath != null) {
		// setPic(imageView);
		// galleryAddPic();
		// mCurrentPhotoPath = null;
		// }

	}

	public void handleBigCameraPhoto(ImageView imageView) {
		if (mCurrentPhotoPath != null) {
			setPic(imageView, true);
			// galleryAddPic();
			mCurrentPhotoPath = null;
		}
	}

	/**
	 * Handles camera captured image
	 * @param imageView
	 * @param isRounded
	 */
	public void  handleCameraPhoto(ImageView imageView, boolean isRounded) {
		//String path_return= null;
		if (mCurrentPhotoPath != null) {
			setPic(imageView, isRounded);
			//Log.e("path in class...", mCurrentPhotoPath);
			//path_return=mCurrentPhotoPath;
			mCurrentPhotoPath = null;
		}
		//return path_return;
	}

	public void setupResourcess(Intent takePictureIntent) {
		File f = null;
		try {
			f = setUpPhotoFile();
			mCurrentPhotoPath = f.getAbsolutePath();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		} catch (IOException e) {
			e.printStackTrace();
			f = null;
			mCurrentPhotoPath = null;
		}
	}

	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
		return Uri.parse(path);
	}

	/**
	 * sets image into ImgeView after resizing into a rounded shape
	 * 
	 * @param profileImageView
	 * @param profile_bitmap
	 * @param width
	 * @param heigth
	 */
	
	private void setRoundedImgeToImageView(ImageView profileImageView, Bitmap profile_bitmap) {

		int width = DEFAULT_WIDTH_HEIGHT_PROFILE_IMAGE, heigth = DEFAULT_WIDTH_HEIGHT_PROFILE_IMAGE;

		int mDstWidth = mContext.getResources().getDimensionPixelSize(R.dimen.destination_width);
		int mDstHeight = mContext.getResources().getDimensionPixelSize(R.dimen.destination_height);
		profile_bitmap = ScalingUtilities.createScaledBitmap(profile_bitmap, mDstWidth, mDstHeight, ScalingLogic.CROP);

		profile_bitmap = Image_Scaling.getRoundedShape(profile_bitmap, width, heigth);

		Drawable d = new BitmapDrawable(mContext.getResources(), profile_bitmap);
		profileImageView.setImageDrawable(d);
	}

	private void setFlatImageToImageView(ImageView profileImageView, Bitmap profile_bitmap) {
		int mDstWidth = mContext.getResources().getDimensionPixelSize(R.dimen.destination_width);
		int mDstHeight = mContext.getResources().getDimensionPixelSize(R.dimen.destination_height);
		profile_bitmap = ScalingUtilities.createScaledBitmap(profile_bitmap, mDstWidth, mDstHeight, ScalingLogic.CROP);
		Drawable d = new BitmapDrawable(mContext.getResources(), profile_bitmap);
		profileImageView.setImageDrawable(d);
	}

	public void dispatchTakePictureIntent(Activity activity, int actionCode) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivityForResult(takePictureIntent, actionCode);
	}

	public void dispatchTakeAndSavePictureIntent(Activity activity, int actionCode) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		setupResourcess(takePictureIntent);
		activity.startActivityForResult(takePictureIntent, actionCode);
	}

	// New function for captureImageIntent
	// private void dispatchTakePictureIntent(int actionCode) {
	//
	// Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	//
	// switch (actionCode) {
	// case ImageImportHelper.ACTION_TAKE_PHOTO_BIG:
	// mImageImportHelper.setupResources(takePictureIntent);
	// break;
	// default:
	// break;
	// } // switch
	//
	// startActivityForResult(takePictureIntent, actionCode);
	// }
}

abstract class AlbumStorageDirFactory {
	public abstract File getAlbumStorageDir(String albumName);

}