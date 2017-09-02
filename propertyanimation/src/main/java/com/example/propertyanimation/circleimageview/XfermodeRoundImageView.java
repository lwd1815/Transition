package com.example.propertyanimation.circleimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import com.example.propertyanimation.R;
import java.lang.ref.WeakReference;

/**
 * 创建者     李文东
 * 创建时间   2017/5/23 11:43
 * 描述	      显示圆形图片的imageview
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class XfermodeRoundImageView extends ImageView {

	// ImageView����
	private int type;
	// Բ��ͼƬ
	private static final int TYPE_CIRCLE = 0;
	// Բ��ͼƬ
	private static final int TYPE_ROUND = 1;
	// Ĭ��Բ�ǿ��
	private static final int BORDER_RADIUS_DEFAULT = 10;
	// ��ȡԲ�ǿ��
	private int mBorderRadius;
	// ����
	private Paint mPaint;
	// ʹ�û�����������洦��õ�bitmap,����GC
	private WeakReference<Bitmap> mWeakBitmap;
	// ����Xfermode��ģʽΪDST_IN
	private Xfermode xfermode = new PorterDuffXfermode(Mode.DST_IN);
	// �ɰ�ͼ��
	private Bitmap mMaskBitmap;

	public XfermodeRoundImageView(Context context) {
		this(context, null);
	}

	public XfermodeRoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public XfermodeRoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// ��ʼ������
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		// ��ȡ�Զ�������ֵ
		TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.XfermodeRoundImageView, defStyle, 0);
		int count = array.getIndexCount();
		for (int i = 0; i < count; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
			case R.styleable.XfermodeRoundImageView_borderRadius:
				// ��ȡԲ�Ǵ�С
				mBorderRadius = array.getDimensionPixelSize(R.styleable.XfermodeRoundImageView_borderRadius, (int) TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, BORDER_RADIUS_DEFAULT, getResources().getDisplayMetrics()));
				break;
			case R.styleable.XfermodeRoundImageView_imageType:
				// ��ȡImageView������
				type = array.getInt(R.styleable.XfermodeRoundImageView_imageType, TYPE_CIRCLE);
				break;
			}
		}
		// Give back a previously retrieved StyledAttributes, for later re-use.
		array.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// �����Բ�Σ���ǿ�ƿ��һ�£�����С��ֵΪ׼
		if (type == TYPE_CIRCLE) {
			int mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
			setMeasuredDimension(mWidth, mWidth);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// �ӻ�����ȡ��ͼƬ
		Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();
		// ���û�л�����߱������ˣ������»���
		if (bitmap == null || bitmap.isRecycled()) {
			// ��ȡ����drawable
			Drawable drawable = getDrawable();
			// ����б���ͼ�����
			if (drawable != null) {
				// �õ�drawable�ĳ��ȺͿ��
				int dWidth = drawable.getIntrinsicWidth();
				int dHeight = drawable.getIntrinsicHeight();
				bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
				// ��������
				Canvas canvas1 = new Canvas(bitmap);
				// ����ͼƬ���ű���
				float scale = 1.0f;
				if (type == TYPE_CIRCLE) {
					scale = Math.max(getWidth() * 1.0f / dWidth, getHeight() * 1.0f / dHeight);
				} else {
					scale = getWidth() * 1.0F / Math.min(dWidth, dHeight);
				}
				// ����ͼƬ
				drawable.setBounds(0, 0, (int) (scale * dWidth), (int) (scale * dHeight));
				// ����DSTͼƬ
				drawable.draw(canvas1);
				// ����SRCͼƬ
				if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
					mMaskBitmap = drawType();
				}
				// ���û���
				mPaint.reset();
				// �������˲�
				mPaint.setFilterBitmap(false);
				mPaint.setXfermode(xfermode);
				canvas1.drawBitmap(mMaskBitmap, 0, 0, mPaint);
				// ���ƴ���õ�ͼ��
				mPaint.setXfermode(null);
				canvas.drawBitmap(bitmap, 0, 0, mPaint);
				// drawable.draw(canvas);
				// ����ͼƬ
				mWeakBitmap = new WeakReference<Bitmap>(bitmap);
			}
		}
		if (bitmap != null) {
			mPaint.setXfermode(null);
			canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
		}
	}

	/**
	 * ������״����Ϊsrc
	 *
	 * @return
	 */
	private Bitmap drawType() {
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		// ��������
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);
		// ���typeΪԲ��
		if (type == TYPE_CIRCLE) {
			canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);
		} else {
			canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), mBorderRadius, mBorderRadius, paint);
		}
		return bitmap;
	}

	// ���ػ��н���mask��dst���ڴ����
	@Override
	public void invalidate() {
		mWeakBitmap = null;
		if (mMaskBitmap != null) {
			mMaskBitmap.recycle();
			mMaskBitmap = null;
		}
		super.invalidate();
	}

}
