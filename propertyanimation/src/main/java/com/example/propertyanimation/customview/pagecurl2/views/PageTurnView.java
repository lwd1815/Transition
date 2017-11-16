package com.example.propertyanimation.customview.pagecurl2.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 
 * @author AigeStudio
 * @since 2014/12/20
 * @version 1.0.0
 * 
 */
public class PageTurnView extends View {
	private static final float TEXT_SIZE_NORMAL = 1 / 40F, TEXT_SIZE_LARGER = 1 / 20F;
	private static final float AUTO_AREA_LEFT = 1 / 5F, AUTO_AREA_RIGHT = 4 / 5F;
	private static final float MOVE_VALID = 1 / 100F;

	private TextPaint mTextPaint;
	private Context mContext;

	private List<Bitmap> mBitmaps;

	private int pageIndex;//
	private int mViewWidth, mViewHeight;

	private float mTextSizeNormal, mTextSizeLarger;//
	private float mClipX;//
	private float mAutoAreaLeft, mAutoAreaRight;//
	private float mCurPointX;//
	private float mMoveValid;//

	private boolean isNextPage, isLastPage;//

	public PageTurnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		isNextPage = true;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mCurPointX = event.getX();

			if (mCurPointX < mAutoAreaLeft) {
				isNextPage = false;
				pageIndex--;
				mClipX = mCurPointX;
				invalidate();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			float SlideDis = mCurPointX - event.getX();
			if (Math.abs(SlideDis) > mMoveValid) {
				mClipX = event.getX();

				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			judgeSlideAuto();

			if (!isLastPage && isNextPage && mClipX <= 0) {
				pageIndex++;
				mClipX = mViewWidth;
				invalidate();
			}
			break;
		}
		return true;
	}

	private void judgeSlideAuto() {
		if (mClipX < mAutoAreaLeft) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (mClipX > 0) {
						mClipX--;
						postInvalidate();
					}
				}
			}).start();
		}
		if (mClipX > mAutoAreaRight) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (mClipX < mViewWidth) {
						mClipX++;
						postInvalidate();
					}
				}
			}).start();
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mViewWidth = w;
		mViewHeight = h;

		initBitmaps();

		mTextSizeNormal = TEXT_SIZE_NORMAL * mViewHeight;
		mTextSizeLarger = TEXT_SIZE_LARGER * mViewHeight;
		mClipX = mViewWidth;
		mAutoAreaLeft = mViewWidth * AUTO_AREA_LEFT;
		mAutoAreaRight = mViewWidth * AUTO_AREA_RIGHT;

		mMoveValid = mViewWidth * MOVE_VALID;
	}

	private void initBitmaps() {
		List<Bitmap> temp = new ArrayList<Bitmap>();
		for (int i = mBitmaps.size() - 1; i >= 0; i--) {
			Bitmap bitmap = Bitmap.createScaledBitmap(mBitmaps.get(i), mViewWidth, mViewHeight, true);
			temp.add(bitmap);
		}
		mBitmaps = temp;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (null == mBitmaps || mBitmaps.size() == 0) {
			defaultDisplay(canvas);
			return;
		}

		// ����λͼ
		drawBtimaps(canvas);
	}

	private void defaultDisplay(Canvas canvas) {
		canvas.drawColor(Color.WHITE);

		mTextPaint.setTextSize(mTextSizeLarger);
		mTextPaint.setColor(Color.RED);
		canvas.drawText("FBI WARNING", mViewWidth / 2, mViewHeight / 4, mTextPaint);

		mTextPaint.setTextSize(mTextSizeNormal);
		mTextPaint.setColor(Color.BLACK);
		canvas.drawText("Please set data use setBitmaps method", mViewWidth / 2, mViewHeight / 3, mTextPaint);
	}

	private void drawBtimaps(Canvas canvas) {
		isLastPage = false;
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageIndex = pageIndex > mBitmaps.size() ? mBitmaps.size() : pageIndex;

		int start = mBitmaps.size() - 2 - pageIndex;
		int end = mBitmaps.size() - pageIndex;

		if (start < 0) {
			isLastPage = true;
			showToast("This is fucking lastest page");
			start = 0;
			end = 1;
		}

		for (int i = start; i < end; i++) {
			canvas.save();
			if (!isLastPage && i == end - 1) {
				canvas.clipRect(0, 0, mClipX, mViewHeight);
			}
			canvas.drawBitmap(mBitmaps.get(i), 0, 0, null);

			canvas.restore();
		}
	}
	public synchronized void setBitmaps(List<Bitmap> bitmaps) {
		if (null == bitmaps || bitmaps.size() == 0)
			throw new IllegalArgumentException("no bitmap to display");
		if (bitmaps.size() < 2)
			throw new IllegalArgumentException("fuck you and fuck to use imageview");

		mBitmaps = bitmaps;
		invalidate();
	}
	private void showToast(Object msg) {
		Toast.makeText(mContext, msg.toString(), Toast.LENGTH_SHORT).show();
	}
}
