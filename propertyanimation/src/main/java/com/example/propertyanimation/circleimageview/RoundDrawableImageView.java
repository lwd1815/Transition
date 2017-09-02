package com.example.propertyanimation.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
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
public class RoundDrawableImageView extends Drawable {

	private Paint paint;
	private Bitmap mBitmap;
	private RectF rectF;
	private int borderRadius = 30;

	public RoundDrawableImageView(Bitmap bitmap) {
		mBitmap = bitmap;
		BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setShader(shader);
	}

	@Override
	public int getIntrinsicWidth() {
		return mBitmap.getWidth();
	}

	@Override
	public int getIntrinsicHeight() {
		return mBitmap.getHeight();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRoundRect(rectF, borderRadius, borderRadius, paint);
	}

	@Override
	public void setAlpha(int alpha) {
		paint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		paint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setBounds(int left, int top, int right, int bottom) {
		super.setBounds(left, top, right, bottom);
		rectF = new RectF(left, top, right, bottom);
	}

	/**
	 * @param radius
	 */
	public void setBorderRadius(int radius) {
		this.borderRadius = radius;
	}
}
