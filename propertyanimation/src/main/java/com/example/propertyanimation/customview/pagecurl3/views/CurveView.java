package com.example.propertyanimation.customview.pagecurl3.views;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CurveView extends View {
	private static final float CURVATURE = 1 / 4F;// ����ֵ
	private static final float VALUE_ADDED = 1 / 400F;// ���ȸ���ֵռ��
	private static final float BUFF_AREA = 1 / 50F;// �ײ���������ռ��
	private static final float AUTO_AREA_BUTTOM_RIGHT = 3 / 4F, AUTO_AREA_BUTTOM_LEFT = 1 / 8F;// ���½Ǻ�����Ի�����ռ��
	private static final float AUTO_SLIDE_BL_V = 1 / 25F, AUTO_SLIDE_BR_V = 1 / 100F;// �����ٶ�ռ��
	private static final float TEXT_SIZE_NORMAL = 1 / 40F, TEXT_SIZE_LARGER = 1 / 20F;// ��׼���ֳߴ�ʹ�����ֳߴ��ռ��

	private List<Bitmap> mBitmaps;// λͼ�����б�

	private SlideHandler mSlideHandler;// ��������Handler
	private Paint mPaint;// ����
	private TextPaint mTextPaint;// �ı�����
	private Context mContext;// �����Ļ�������

	private Path mPath;// �۵�·��
	private Path mPathFoldAndNext;// һ�������۵�����һҳ�����Path
	private Path mPathSemicircleBtm, mPathSemicircleLeft;// �ײ�������°�ԲPath
	private Path mPathTrap;// ��������Path

	private Region mRegionShortSize;// �̱ߵ���Ч����
	private Region mRegionCurrent;// ��ǰҳ������ʵ���ǿؼ��Ĵ�С
	private Region mRegionNext;// ��ǰҳ������ʵ���ǿؼ��Ĵ�С
	private Region mRegionFold;// ��ǰҳ������ʵ���ǿؼ��Ĵ�С
	private Region mRegionSemicircle;// ���°�Բ����

	private int mViewWidth, mViewHeight;// �ؼ����
	private int mPageIndex;// ��ǰ��ʾmBitmaps���ݵ��±�

	private float mPointX, mPointY;// ��ָ�����������
	private float mValueAdded;// ���ȸ���ֵ
	private float mBuffArea;// �ײ���������
	private float mAutoAreaButtom, mAutoAreaRight, mAutoAreaLeft;// ���½Ǻ�����Ի�����
	private float mStart_X, mStart_Y;// ֱ���������
	private float mAutoSlideV_BL, mAutoSlideV_BR;// �����ٶ�
	private float mTextSizeNormal, mTextSizeLarger;// ��׼���ֳߴ�ʹ�����ֳߴ�
	private float mDegrees;// ��ǰY�߳���Y��ļн�

	private boolean isSlide, isLastPage, isNextPage;// �Ƿ�ִ�л������Ƿ��ѵ����һҳ���Ƿ����ʾ��һҳ�ı�ʶֵ

	private Slide mSlide;// ���嵱ǰ�����������»��������»�

	/**
	 * ö���ඨ�廬������
	 */
	private enum Slide {
		LEFT_BOTTOM, RIGHT_BOTTOM
	}

	private Ratio mRatio;// ���嵱ǰ�۵��߳�

	/**
	 * ö���ඨ�峤�߶̱�
	 */
	private enum Ratio {
		LONG, SHORT
	}

	public CurveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		/*
		 * ʵ�����ı����ʲ����ò���
		 */
		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
		mTextPaint.setTextAlign(Paint.Align.CENTER);

		/*
		 * ʵ�������ʶ������ò���
		 */
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);

		/*
		 * ʵ����·������
		 */
		mPath = new Path();
		mPathFoldAndNext = new Path();
		mPathSemicircleBtm = new Path();
		mPathSemicircleLeft = new Path();
		mPathTrap = new Path();

		/*
		 * ʵ�����������
		 */
		mRegionShortSize = new Region();
		mRegionCurrent = new Region();
		mRegionSemicircle = new Region();

		// ʵ��������Handler������
		mSlideHandler = new SlideHandler();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		/*
		 * ��ȡ�ؼ����
		 */
		mViewWidth = w;
		mViewHeight = h;

		// ��ʼ��λͼ����
		if (null != mBitmaps) {
			initBitmaps();
		}

		// �������ֳߴ�
		mTextSizeNormal = TEXT_SIZE_NORMAL * mViewHeight;
		mTextSizeLarger = TEXT_SIZE_LARGER * mViewHeight;

		// ���㾫�ȸ���ֵ
		mValueAdded = mViewHeight * VALUE_ADDED;

		// ����ײ���������
		mBuffArea = mViewHeight * BUFF_AREA;

		/*
		 * �����Ի�λ��
		 */
		mAutoAreaButtom = mViewHeight * AUTO_AREA_BUTTOM_RIGHT;
		mAutoAreaRight = mViewWidth * AUTO_AREA_BUTTOM_RIGHT;
		mAutoAreaLeft = mViewWidth * AUTO_AREA_BUTTOM_LEFT;

		// ����̱ߵ���Ч����
		computeShortSizeRegion();

		/*
		 * ���㻬���ٶ�
		 */
		mAutoSlideV_BL = mViewWidth * AUTO_SLIDE_BL_V;
		mAutoSlideV_BR = mViewWidth * AUTO_SLIDE_BR_V;

		// ���㵱ǰҳ����
		mRegionCurrent.set(0, 0, mViewWidth, mViewHeight);
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

	/**
	 * ����̱ߵ���Ч����
	 */
	private void computeShortSizeRegion() {
		// �̱�Բ��·������
		Path pathShortSize = new Path();

		// ����װ��Path�߽�ֵ��RectF����
		RectF rectShortSize = new RectF();

		// ���Բ�ε�Path
		pathShortSize.addCircle(0, mViewHeight, mViewWidth, Path.Direction.CCW);

		// ����߽�
		pathShortSize.computeBounds(rectShortSize, true);

		// ��Pathת��ΪRegion
		mRegionShortSize.setPath(pathShortSize, new Region((int) rectShortSize.left, (int) rectShortSize.top, (int) rectShortSize.right, (int) rectShortSize.bottom));
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

		// �ػ�ʱ����·��
		mPath.reset();
		mPathFoldAndNext.reset();
		mPathTrap.reset();
		mPathSemicircleBtm.reset();
		mPathSemicircleLeft.reset();

		// ���Ƶ�ɫ
		canvas.drawColor(Color.WHITE);

		/*
		 * ����������ԭ�㣨����û��������ʱ������Ƶ�һҳ
		 */
		if (mPointX == 0 && mPointY == 0) {
			canvas.drawBitmap(mBitmaps.get(mBitmaps.size() - 1), 0, 0, null);
			return;
		}

		/*
		 * �жϴ������Ƿ��ڶ̱ߵ���Ч������
		 */
		if (!mRegionShortSize.contains((int) mPointX, (int) mPointY)) {
			// ���������ͨ��x����ǿ������y����
			mPointY = (float) (Math.sqrt((Math.pow(mViewWidth, 2) - Math.pow(mPointX, 2))) - mViewHeight);

			// ���ȸ���ֵ���⾫����ʧ
			mPointY = Math.abs(mPointY) + mValueAdded;
		}

		/*
		 * ���������ж�
		 */
		float area = mViewHeight - mBuffArea;
		if (!isSlide && mPointY >= area) {
			mPointY = area;
		}

		/*
		 * ��������ôע�ͺ��ء�������ͼ��
		 */
		float mK = mViewWidth - mPointX;
		float mL = mViewHeight - mPointY;

		// ��Ҫ�ظ�ʹ�õĲ�����ֵ�����ظ�����
		float temp = (float) (Math.pow(mL, 2) + Math.pow(mK, 2));

		/*
		 * ����̱߳��߳���
		 */
		float sizeShort = temp / (2F * mK);
		float sizeLong = temp / (2F * mL);

		float tempAM = mK - sizeShort;

		/*
		 * ���ݳ��̱߱߳�������ת�ǶȲ�ȷ��mRatio��ֵ
		 */
		if (sizeShort < sizeLong) {
			mRatio = Ratio.SHORT;
			float sin = tempAM / sizeShort;
			mDegrees = (float) (Math.asin(sin) / Math.PI * 180);
		} else {
			mRatio = Ratio.LONG;
			float cos = mK / sizeLong;
			mDegrees = (float) (Math.acos(cos) / Math.PI * 180);
		}

		if (sizeLong > mViewHeight) {
			// ���㡭�������ͼ��AN��~
			float an = sizeLong - mViewHeight;

			// ������AMN��MN��
			float largerTrianShortSize = an / (sizeLong - (mViewHeight - mPointY)) * (mViewWidth - mPointX);

			// ������AQN��QN��
			float smallTrianShortSize = an / sizeLong * sizeShort;

			/*
			 * �������
			 */
			float topX1 = mViewWidth - largerTrianShortSize;
			float topX2 = mViewWidth - smallTrianShortSize;
			float btmX2 = mViewWidth - sizeShort;

			// �����������
			float startXBtm = btmX2 - CURVATURE * sizeShort;
			float startYBtm = mViewHeight;

			// ���������յ�
			float endXBtm = mPointX + (1 - CURVATURE) * (tempAM);
			float endYBtm = mPointY + (1 - CURVATURE) * mL;

			// �������߿��Ƶ�
			float controlXBtm = btmX2;
			float controlYBtm = mViewHeight;

			// �������߶���
			float bezierPeakXBtm = 0.25F * startXBtm + 0.5F * controlXBtm + 0.25F * endXBtm;
			float bezierPeakYBtm = 0.25F * startYBtm + 0.5F * controlYBtm + 0.25F * endYBtm;

			/*
			 * ���ɴ����ߵ��ı���·��
			 */
			mPath.moveTo(startXBtm, startYBtm);
			mPath.quadTo(controlXBtm, controlYBtm, endXBtm, endYBtm);
			mPath.lineTo(mPointX, mPointY);
			mPath.lineTo(topX1, 0);
			mPath.lineTo(topX2, 0);

			/*
			 * �油����Path
			 */
			mPathTrap.moveTo(startXBtm, startYBtm);
			mPathTrap.lineTo(topX2, 0);
			mPathTrap.lineTo(bezierPeakXBtm, bezierPeakYBtm);
			mPathTrap.close();

			/*
			 * �ײ��°�ԲPath
			 */
			mPathSemicircleBtm.moveTo(startXBtm, startYBtm);
			mPathSemicircleBtm.quadTo(controlXBtm, controlYBtm, endXBtm, endYBtm);
			mPathSemicircleBtm.close();

			/*
			 * ���ɰ����۵�����һҳ��·��
			 */
			mPathFoldAndNext.moveTo(startXBtm, startYBtm);
			mPathFoldAndNext.quadTo(controlXBtm, controlYBtm, endXBtm, endYBtm);
			mPathFoldAndNext.lineTo(mPointX, mPointY);
			mPathFoldAndNext.lineTo(topX1, 0);
			mPathFoldAndNext.lineTo(mViewWidth, 0);
			mPathFoldAndNext.lineTo(mViewWidth, mViewHeight);
			mPathFoldAndNext.close();

			// �����°�Բ����
			mRegionSemicircle = computeRegion(mPathSemicircleBtm);
		} else {
			/*
			 * �������
			 */
			float leftY = mViewHeight - sizeLong;
			float btmX = mViewWidth - sizeShort;

			// �����������
			float startXBtm = btmX - CURVATURE * sizeShort;
			float startYBtm = mViewHeight;
			float startXLeft = mViewWidth;
			float startYLeft = leftY - CURVATURE * sizeLong;

			// ���������յ�
			float endXBtm = mPointX + (1 - CURVATURE) * (tempAM);
			float endYBtm = mPointY + (1 - CURVATURE) * mL;
			float endXLeft = mPointX + (1 - CURVATURE) * mK;
			float endYLeft = mPointY - (1 - CURVATURE) * (sizeLong - mL);

			// �������߿��Ƶ�
			float controlXBtm = btmX;
			float controlYBtm = mViewHeight;
			float controlXLeft = mViewWidth;
			float controlYLeft = leftY;

			// �������߶���
			float bezierPeakXBtm = 0.25F * startXBtm + 0.5F * controlXBtm + 0.25F * endXBtm;
			float bezierPeakYBtm = 0.25F * startYBtm + 0.5F * controlYBtm + 0.25F * endYBtm;
			float bezierPeakXLeft = 0.25F * startXLeft + 0.5F * controlXLeft + 0.25F * endXLeft;
			float bezierPeakYLeft = 0.25F * startYLeft + 0.5F * controlYLeft + 0.25F * endYLeft;

			/*
			 * �����Ҳ��������
			 */
			if (startYLeft <= 0) {
				startYLeft = 0;
			}

			/*
			 * ���Ƶײ�����������
			 */
			if (startXBtm <= 0) {
				startXBtm = 0;
			}

			/*
			 * ���ݵײ�������Ƶ����¼��㱴�������߶�������
			 */
			float partOfShortLength = CURVATURE * sizeShort;
			if (btmX >= -mValueAdded && btmX <= partOfShortLength - mValueAdded) {
				float f = btmX / partOfShortLength;
				float t = 0.5F * f;

				float bezierPeakTemp = 1 - t;
				float bezierPeakTemp1 = bezierPeakTemp * bezierPeakTemp;
				float bezierPeakTemp2 = 2 * t * bezierPeakTemp;
				float bezierPeakTemp3 = t * t;

				bezierPeakXBtm = bezierPeakTemp1 * startXBtm + bezierPeakTemp2 * controlXBtm + bezierPeakTemp3 * endXBtm;
				bezierPeakYBtm = bezierPeakTemp1 * startYBtm + bezierPeakTemp2 * controlYBtm + bezierPeakTemp3 * endYBtm;
			}

			/*
			 * �����Ҳ����Ƶ����¼��㱴�������߶�������
			 */
			float partOfLongLength = CURVATURE * sizeLong;
			if (leftY >= -mValueAdded && leftY <= partOfLongLength - mValueAdded) {
				float f = leftY / partOfLongLength;
				float t = 0.5F * f;

				float bezierPeakTemp = 1 - t;
				float bezierPeakTemp1 = bezierPeakTemp * bezierPeakTemp;
				float bezierPeakTemp2 = 2 * t * bezierPeakTemp;
				float bezierPeakTemp3 = t * t;

				bezierPeakXLeft = bezierPeakTemp1 * startXLeft + bezierPeakTemp2 * controlXLeft + bezierPeakTemp3 * endXLeft;
				bezierPeakYLeft = bezierPeakTemp1 * startYLeft + bezierPeakTemp2 * controlYLeft + bezierPeakTemp3 * endYLeft;
			}

			/*
			 * �油����Path
			 */
			mPathTrap.moveTo(startXBtm, startYBtm);
			mPathTrap.lineTo(startXLeft, startYLeft);
			mPathTrap.lineTo(bezierPeakXLeft, bezierPeakYLeft);
			mPathTrap.lineTo(bezierPeakXBtm, bezierPeakYBtm);
			mPathTrap.close();

			/*
			 * ���ɴ����ߵ�������·��
			 */
			mPath.moveTo(startXBtm, startYBtm);
			mPath.quadTo(controlXBtm, controlYBtm, endXBtm, endYBtm);
			mPath.lineTo(mPointX, mPointY);
			mPath.lineTo(endXLeft, endYLeft);
			mPath.quadTo(controlXLeft, controlYLeft, startXLeft, startYLeft);

			/*
			 * ���ɵײ��°�Բ��Path
			 */
			mPathSemicircleBtm.moveTo(startXBtm, startYBtm);
			mPathSemicircleBtm.quadTo(controlXBtm, controlYBtm, endXBtm, endYBtm);
			mPathSemicircleBtm.close();

			/*
			 * �����Ҳ��°�Բ��Path
			 */
			mPathSemicircleLeft.moveTo(endXLeft, endYLeft);
			mPathSemicircleLeft.quadTo(controlXLeft, controlYLeft, startXLeft, startYLeft);
			mPathSemicircleLeft.close();

			/*
			 * ���ɰ����۵�����һҳ��·��
			 */
			mPathFoldAndNext.moveTo(startXBtm, startYBtm);
			mPathFoldAndNext.quadTo(controlXBtm, controlYBtm, endXBtm, endYBtm);
			mPathFoldAndNext.lineTo(mPointX, mPointY);
			mPathFoldAndNext.lineTo(endXLeft, endYLeft);
			mPathFoldAndNext.quadTo(controlXLeft, controlYLeft, startXLeft, startYLeft);
			mPathFoldAndNext.lineTo(mViewWidth, mViewHeight);
			mPathFoldAndNext.close();

			/*
			 * ����ײ����Ҳ����°�Բ����
			 */
			Region regionSemicircleBtm = computeRegion(mPathSemicircleBtm);
			Region regionSemicircleLeft = computeRegion(mPathSemicircleLeft);

			// �ϲ����°�Բ����
			mRegionSemicircle.op(regionSemicircleBtm, regionSemicircleLeft, Region.Op.UNION);
		}

		// ����Path���ɵ��۵�����
		mRegionFold = computeRegion(mPath);

		// �油����
		Region regionTrap = computeRegion(mPathTrap);

		// ���۵��������油�������
		mRegionFold.op(regionTrap, Region.Op.UNION);

		// ����Ӻ���������޳����°�Բ�������������۵�����
		mRegionFold.op(mRegionSemicircle, Region.Op.DIFFERENCE);

		/*
		 * ������һҳ����
		 */
		mRegionNext = computeRegion(mPathFoldAndNext);
		mRegionNext.op(mRegionFold, Region.Op.DIFFERENCE);

		drawBitmaps(canvas);
	}

	/**
	 * ����λͼ����
	 * 
	 * @param canvas
	 *            ��������
	 */
	private void drawBitmaps(Canvas canvas) {
		// ����λͼǰ����isLastPageΪfalse
		isLastPage = false;

		// ����pageIndex��ֵ��Χ
		mPageIndex = mPageIndex < 0 ? 0 : mPageIndex;
		mPageIndex = mPageIndex > mBitmaps.size() ? mBitmaps.size() : mPageIndex;

		// ����������ʼλ��
		int start = mBitmaps.size() - 2 - mPageIndex;
		int end = mBitmaps.size() - mPageIndex;

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

		/*
		 * ���㵱ǰҳ������
		 */
		canvas.save();
		//canvas.clipRegion(mRegionCurrent);
		canvas.drawBitmap(mBitmaps.get(end - 1), 0, 0, null);
		canvas.restore();

		/*
		 * �����۵�ҳ������
		 */
		canvas.save();
		//canvas.clipRegion(mRegionFold);

		canvas.translate(mPointX, mPointY);

		/*
		 * ���ݳ��̱߱�ʶ�����۵�����ͼ��
		 */
		if (mRatio == Ratio.SHORT) {
			canvas.rotate(90 - mDegrees);
			canvas.translate(0, -mViewHeight);
			canvas.scale(-1, 1);
			canvas.translate(-mViewWidth, 0);
		} else {
			canvas.rotate(-(90 - mDegrees));
			canvas.translate(-mViewWidth, 0);
			canvas.scale(1, -1);
			canvas.translate(0, -mViewHeight);
		}

		canvas.drawBitmap(mBitmaps.get(end - 1), 0, 0, null);
		canvas.restore();

		/*
		 * ������һҳ������
		 */
		canvas.save();
		//canvas.clipRegion(mRegionNext);
		canvas.drawBitmap(mBitmaps.get(start), 0, 0, null);
		canvas.restore();
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
	 * ͨ��·����������
	 * 
	 * @param path
	 *            ·������
	 * @return ·����Region
	 */
	private Region computeRegion(Path path) {
		Region region = new Region();
		RectF f = new RectF();
		path.computeBounds(f, true);
		region.setPath(path, new Region((int) f.left, (int) f.top, (int) f.right, (int) f.bottom));
		return region;
	}

	/**
	 * ���㻬�������仯
	 */
	private void slide() {
		/*
		 * ���������ʶֵΪfalse�򷵻�
		 */
		if (!isSlide) {
			return;
		}

		/*
		 * �����ǰҳ�������һҳ
		 * �������Ҫ����һҳ
		 * ������һҳ�ѱ�����
		 */
		if (!isLastPage && isNextPage && (mPointX - mAutoSlideV_BL <= -mViewWidth)) {
			mPointX = -mViewWidth;
			mPointY = mViewHeight;
			mPageIndex++;
			invalidate();
		}

		/*
		 * �����ǰ������ʶΪ�����»���x�����С�ڿؼ����
		 */
		else if (mSlide == Slide.RIGHT_BOTTOM && mPointX < mViewWidth) {
			// ����x�����Լ�
			mPointX += mAutoSlideV_BR;

			// ������x�����ֵ���¼���y�����ֵ
			mPointY = mStart_Y + ((mPointX - mStart_X) * (mViewHeight - mStart_Y)) / (mViewWidth - mStart_X);

			// ��SlideHandler�����ػ�
			mSlideHandler.sleep(25);
		}

		/*
		 * �����ǰ������ʶΪ�����»���x�������ڿؼ���ȵĸ�ֵ
		 */
		else if (mSlide == Slide.LEFT_BOTTOM && mPointX > -mViewWidth) {
			// ����x�����Լ�
			mPointX -= mAutoSlideV_BL;

			// ������x�����ֵ���¼���y�����ֵ
			mPointY = mStart_Y + ((mPointX - mStart_X) * (mViewHeight - mStart_Y)) / (-mViewWidth - mStart_X);

			// ��SlideHandler�����ػ�
			mSlideHandler.sleep(25);
		}
	}

	/**
	 * ΪisSlide�ṩ�����ֹͣ�������ڱ�Ҫʱ�ͷŻ�������
	 */
	public void slideStop() {
		isSlide = false;
	}

	/**
	 * �ṩ����ķ�����ȡView��Handler
	 * 
	 * @return mSlideHandler
	 */
	public SlideHandler getSlideHandler() {
		return mSlideHandler;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		isNextPage = true;

		/*
		 * ��ȡ��ǰ�¼���
		 */
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_UP:// ��ָ̧��ʱ��
			if (isNextPage) {
				/*
				 * �����ǰ�¼���λ�������Ի�����
				 */
				if (x > mAutoAreaRight && y > mAutoAreaButtom) {
					// ��ǰΪ�����»�
					mSlide = Slide.RIGHT_BOTTOM;

					// Ħ����ɧ�꣡
					justSlide(x, y);
				}

				/*
				 * �����ǰ�¼���λ������Ի�����
				 */
				if (x < mAutoAreaLeft) {
					// ��ǰΪ�����»�
					mSlide = Slide.LEFT_BOTTOM;

					// Ħ����ɧ�꣡
					justSlide(x, y);
				}
			}
			break;
		case MotionEvent.ACTION_DOWN:
			isSlide = false;
			/*
			 * ����¼���λ�ڻع�����
			 */
			if (x < mAutoAreaLeft) {
				// �ǾͲ�����һҳ�˶�����һҳ
				isNextPage = false;
				mPageIndex--;
				mPointX = x;
				mPointY = y;
				invalidate();
			}
			downAndMove(event);
			break;
		case MotionEvent.ACTION_MOVE:
			downAndMove(event);
			break;
		}
		return true;
	}

	/**
	 * ����DOWN��MOVE�¼�
	 * 
	 * @param event
	 *            �¼�����
	 */
	private void downAndMove(MotionEvent event) {
		if (!isLastPage) {
			mPointX = event.getX();
			mPointY = event.getY();

			invalidate();
		}
	}

	/**
	 * ����⻬�ĵذ���~
	 * 
	 * @param x
	 *            ��ǰ������x
	 * @param y
	 *            ��ǰ������y
	 */
	private void justSlide(float x, float y) {
		// ��ȡ������ֱ�߷��̵����
		mStart_X = x;
		mStart_Y = y;

		// OKҪ��ʼ������Ŷ~
		isSlide = true;

		// ����
		slide();
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

	/**
	 * ��������Handler
	 */
	@SuppressLint("HandlerLeak")
	public class SlideHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// ѭ�����û�������
			CurveView.this.slide();

			// �ػ���ͼ
			CurveView.this.invalidate();
		}

		/**
		 * �ӳ���Handler������Ϣʵ��ʱ����
		 * 
		 * @param delayMillis
		 *            ���ʱ��
		 */
		public void sleep(long delayMillis) {
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	}
}
