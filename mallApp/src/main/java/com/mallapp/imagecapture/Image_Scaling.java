package com.mallapp.imagecapture;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.util.Log;

public class Image_Scaling {
	
	
	public static Bitmap getRoundedShape(Bitmap scaleBitmapImage, int width, int height) {
		//Log.e("height", ""+height);
		//Log.e("width", ""+width);
	    int targetWidth = width;
	    int targetHeight = height;
	    Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,Bitmap.Config.ARGB_8888);

	    Canvas canvas = new Canvas(targetBitmap);
	    Path path = new Path();
	    path.addCircle(((float) targetWidth - 1) / 2,((float) targetHeight - 1) / 2, 
	    					(Math.min(((float) targetWidth), 
	    					((float) targetHeight)) / 2),Path.Direction.CCW);

	    canvas.clipPath(path);
	    Bitmap sourceBitmap = scaleBitmapImage;
	    canvas.drawBitmap(sourceBitmap,new Rect(0, 0, sourceBitmap.getWidth(),
	    					sourceBitmap.getHeight()),new Rect(0, 0, targetWidth, targetHeight), null);
	    return targetBitmap;
	}
	
	
	public static Bitmap getImageOrintation(Bitmap bitmapSelectedImage, String profile_image_path) {
		ExifInterface exif;
		try {
			exif = new ExifInterface(profile_image_path);
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
			Log.d("EXIF", "Exif: " + orientation);
			
			Matrix matrix = new Matrix();
	        if (orientation == 6) {
	            matrix.postRotate(90);
	        }
	        else if (orientation == 3) {
	            matrix.postRotate(180);
	        }
	        else if (orientation == 8) {
	            matrix.postRotate(270);
	        }
			
	        bitmapSelectedImage = Bitmap.createBitmap(bitmapSelectedImage, 0, 0, bitmapSelectedImage.getWidth(), bitmapSelectedImage.getHeight(), matrix, true);
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmapSelectedImage;
		
	}
	
	
	
}