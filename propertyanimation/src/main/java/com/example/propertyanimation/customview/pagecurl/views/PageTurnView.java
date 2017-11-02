package com.example.propertyanimation.customview.pagecurl.views;

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
 * 
 * @author AigeStudio {@link http://blog.csdn.net/aigestudio}
 * @version 1.0.0
 * @since 2014/12/20
 */
public class PageTurnView extends View {
	private static final float TEXT_SIZE_NORMAL = 1 / 40F, TEXT_SIZE_LARGER = 1 / 20F;// ��׼���ֳߴ�ʹ�����ֳߴ��ռ��
	private static final float AUTO_AREA_LEFT = 1 / 5F, AUTO_AREA_RIGHT = 4 / 5F;// �ؼ����Ҳ��Զ���������ռ��
	private static final float MOVE_VALID = 1 / 100F;// �ƶ��¼�����Ч����ռ��

	private TextPaint mTextPaint;// �ı�����
	private Context mContext;// �����Ļ�������

	private List<Bitmap> mBitmaps;// λͼ�����б�

	private int pageIndex;// ��ǰ��ʾmBitmaps���ݵ��±�
	private int mViewWidth, mViewHeight;// �ؼ����

	private float mTextSizeNormal, mTextSizeLarger;// ��׼���ֳߴ�ʹ�����ֳߴ�
	private float mClipX;// �ü��Ҷ˵�����
	private float mAutoAreaLeft, mAutoAreaRight;// �ؼ������Ҳ��Զ�����������
	private float mCurPointX;// ָ�ⴥ����Ļʱ��X������ֵ
	private float mMoveValid;// �ƶ��¼�����Ч����

	private boolean isNextPage, isLastPage;// �Ƿ����ʾ��һҳ���Ƿ����һҳ�ı�ʶֵ

	public PageTurnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		/*
		 * ʵ�����ı����ʲ����ò���
		 */
		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ÿ�δ���TouchEvent����isNextPageΪtrue
		isNextPage = true;

		/*
		 * �жϵ�ǰ�¼�����
		 */
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:// ������Ļʱ
			// ��ȡ��ǰ�¼���x����
			mCurPointX = event.getX();

			/*
			 * ����¼���λ�ڻع�����
			 */
			if (mCurPointX < mAutoAreaLeft) {
				// �ǾͲ�����һҳ�˶�����һҳ
				isNextPage = false;
				pageIndex--;
				mClipX = mCurPointX;
				invalidate();
			}
			break;
		case MotionEvent.ACTION_MOVE:// ����ʱ
			float SlideDis = mCurPointX - event.getX();
			if (Math.abs(SlideDis) > mMoveValid) {
				// ��ȡ�������x����
				mClipX = event.getX();

				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:// ����̧��ʱ
			// �ж��Ƿ���Ҫ�Զ�����
			judgeSlideAuto();

			/*
			 * �����ǰҳ�������һҳ
			 * �������Ҫ����һҳ
			 * ������һҳ�ѱ�clip��
			 */
			if (!isLastPage && isNextPage && mClipX <= 0) {
				pageIndex++;
				mClipX = mViewWidth;
				invalidate();
			}
			break;
		}
		return true;
	}

	/**
	 * �ж��Ƿ���Ҫ�Զ�����
	 * ���ݲ����ĵ�ǰֵ�ж��Զ�����
	 */
	private void judgeSlideAuto() {
		/*
		 * ����ü����Ҷ˵������ڿؼ����ʮ��֮һ�������ڣ���ô����ֱ�������Զ������ؼ����
		 */
		if (mClipX < mAutoAreaLeft) {
			while (mClipX > 0) {
				mClipX--;
				invalidate();
			}
		}
		/*
		 * ����ü����Ҷ˵������ڿؼ��Ҷ�ʮ��֮һ�������ڣ���ô����ֱ�������Զ������ؼ��Ҷ�
		 */
		if (mClipX > mAutoAreaRight) {
			while (mClipX < mViewWidth) {
				mClipX++;
				invalidate();
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// ��ȡ�ؼ����
		mViewWidth = w;
		mViewHeight = h;

		// ��ʼ��λͼ����
		initBitmaps();

		// �������ֳߴ�
		mTextSizeNormal = TEXT_SIZE_NORMAL * mViewHeight;
		mTextSizeLarger = TEXT_SIZE_LARGER * mViewHeight;

		// ��ʼ���ü��Ҷ˵�����
		mClipX = mViewWidth;

		// ����ؼ������Ҳ��Զ�����������
		mAutoAreaLeft = mViewWidth * AUTO_AREA_LEFT;
		mAutoAreaRight = mViewWidth * AUTO_AREA_RIGHT;

		// ����һ�ȵ���Ч����
		mMoveValid = mViewWidth * MOVE_VALID;
	}

	/**
	 * ��ʼ��λͼ����
	 * ����λͼ�ߴ�����Ļƥ��
	 */
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
		/*
		 * �������Ϊ������ʾĬ����ʾ�ı�
		 */
		if (null == mBitmaps || mBitmaps.size() == 0) {
			defaultDisplay(canvas);
			return;
		}

		// ����λͼ
		drawBtimaps(canvas);
	}

	/**
	 * Ĭ����ʾ
	 * 
	 * @param canvas
	 *            Canvas����
	 */
	private void defaultDisplay(Canvas canvas) {
		// ���Ƶ�ɫ
		canvas.drawColor(Color.WHITE);

		// ���Ʊ����ı�
		mTextPaint.setTextSize(mTextSizeLarger);
		mTextPaint.setColor(Color.RED);
		canvas.drawText("FBI WARNING", mViewWidth / 2, mViewHeight / 4, mTextPaint);

		// ������ʾ�ı�
		mTextPaint.setTextSize(mTextSizeNormal);
		mTextPaint.setColor(Color.BLACK);
		canvas.drawText("Please set data use setBitmaps method", mViewWidth / 2, mViewHeight / 3, mTextPaint);
	}

	/**
	 * ����λͼ
	 * 
	 * @param canvas
	 *            Canvas����
	 */
	private void drawBtimaps(Canvas canvas) {
		// ����λͼǰ����isLastPageΪfalse
		isLastPage = false;

		// ����pageIndex��ֵ��Χ
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		pageIndex = pageIndex > mBitmaps.size() ? mBitmaps.size() : pageIndex;

		// ����������ʼλ��
		int start = mBitmaps.size() - 2 - pageIndex;
		int end = mBitmaps.size() - pageIndex;

		/*
		 * ����������λ��С��0���ʾ��ǰ�Ѿ��������һ��ͼƬ
		 */
		if (start < 0) {
			// ��ʱ����isLastPageΪtrue
			isLastPage = true;

			// ����ʾ��ʾ��Ϣ
			showToast("This is fucking lastest page");

			// ǿ��������ʼλ��
			start = 0;
			end = 1;
		}

		for (int i = start; i < end; i++) {
			canvas.save();

			/*
			 * ���ü�λ�����Ļ�������
			 * �������ĩҳ����ִ�вü�
			 */
			if (!isLastPage && i == end - 1) {
				canvas.clipRect(0, 0, mClipX, mViewHeight);
			}
			canvas.drawBitmap(mBitmaps.get(i), 0, 0, null);

			canvas.restore();
		}
	}

	/**
	 * ����λͼ����
	 * 
	 * @param bitmaps
	 *            λͼ�����б�
	 */
	public synchronized void setBitmaps(List<Bitmap> bitmaps) {
		/*
		 * �������Ϊ�����׳��쳣
		 */
		if (null == bitmaps || bitmaps.size() == 0)
			throw new IllegalArgumentException("no bitmap to display");

		/*
		 * ������ݳ���С��2��GG˼�ܴ�
		 */
		if (bitmaps.size() < 2)
			throw new IllegalArgumentException("fuck you and fuck to use imageview");

		mBitmaps = bitmaps;
		invalidate();
	}

	/**
	 * Toast��ʾ
	 * 
	 * @param msg
	 *            Toast��ʾ�ı�
	 */
	private void showToast(Object msg) {
		Toast.makeText(mContext, msg.toString(), Toast.LENGTH_SHORT).show();
	}
}
