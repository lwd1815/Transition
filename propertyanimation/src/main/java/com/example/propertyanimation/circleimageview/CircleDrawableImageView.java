package com.example.propertyanimation.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

/**
 * 创建者     李文东
 * 创建时间   2017/5/23 11:43
 * 描述	      显示圆形图片的imageview
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class CircleDrawableImageView extends Drawable {

	private Bitmap mBitmap;
	private Paint mPaint;
	private int mWidth;

	public CircleDrawableImageView(Bitmap bitmap) {
		mBitmap = bitmap;
		BitmapShader shader = new BitmapShader(mBitmap, TileMode.CLAMP, TileMode.CLAMP);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setShader(shader);
		mWidth = Math.min(mBitmap.getWidth(), mBitmap.getHeight());
	}

	@Override
	public int getIntrinsicWidth() {
		return mWidth;
	}

	@Override
	public int getIntrinsicHeight() {
		return mWidth;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mPaint);
	}

	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

}
