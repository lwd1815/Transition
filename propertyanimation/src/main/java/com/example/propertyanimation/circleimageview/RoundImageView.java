package com.example.propertyanimation.circleimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import com.example.propertyanimation.R;

/**
 * 创建者     李文东
 * 创建时间   2017/5/23 11:43
 * 描述	      显示圆形图片的imageview
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class RoundImageView extends ImageView {
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
	// �뾶
	private int mRadius;
	// ���ž���
	private Matrix mMatrix;
	// ��Ⱦ��,ʹ��ͼƬ�����״
	private BitmapShader mBitmapShader;
	// ���
	private int mWidth;
	// Բ�Ƿ�Χ
	private RectF mRectF;

	public RoundImageView(Context context) {
		this(context, null);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// ��ʼ�����ʵ�����
		mMatrix = new Matrix();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		// ��ȡ�Զ�������ֵ
		TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyle, 0);
		int count = array.getIndexCount();
		for (int i = 0; i < count; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
			case R.styleable.RoundImageView_borderRadius:
				// ��ȡԲ�Ǵ�С
				mBorderRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_borderRadius, (int) TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, BORDER_RADIUS_DEFAULT, getResources().getDisplayMetrics()));
				break;
			case R.styleable.RoundImageView_imageType:
				// ��ȡImageView������
				type = array.getInt(R.styleable.RoundImageView_imageType, TYPE_CIRCLE);
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
			mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
			mRadius = mWidth / 2;
			setMeasuredDimension(mWidth, mWidth);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null) {
			return;
		}
		// ������Ⱦ��
		setShader();
		if (type == TYPE_ROUND) {
			canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);
		} else {
			canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
		}
	}

	private void setShader() {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}
		Bitmap bitmap = drawable2Bitmap(drawable);
		mBitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
		float scale = 1.0f;
		if (type == TYPE_ROUND) {
			scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(), getHeight() * 1.0f / bitmap.getHeight());
		} else if (type == TYPE_CIRCLE) {
			// ȡСֵ�����ȡ��ֵ�Ļ������ܸ���view
			int bitmapWidth = Math.min(bitmap.getWidth(), getHeight());
			scale = mWidth * 1.0f / bitmapWidth;
		}
		mMatrix.setScale(scale, scale);
		mBitmapShader.setLocalMatrix(mMatrix);
		mPaint.setShader(mBitmapShader);
	}

	/**
	 * ��Drawableת��ΪBitmap
	 * 
	 * @param drawable
	 * @return
	 */
	private Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		// ��������
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mRectF = new RectF(0, 0, getWidth(), getHeight());
	}

	/**
	 * ���⹫��������borderRadius����
	 * 
	 * @param borderRadius
	 */
	public void setBorderRadius(int borderRadius) {
		int pxValue = dp2px(borderRadius);
		if (this.mBorderRadius != pxValue) {
			this.mBorderRadius = pxValue;
			// ��ʱ����Ҫ�����ֵ�onLayout,����ֻ��Ҫ����onDraw����
			invalidate();
		}
	}

	/**
	 * ���⹫����������״�ķ���
	 * 
	 * @param type
	 */
	public void setType(int type) {
		if (this.type != type) {
			this.type = type;
			if (this.type != TYPE_CIRCLE && this.type != TYPE_ROUND) {
				this.type = TYPE_CIRCLE;
			}
			// ���ʱ��ı���״�ˣ�����Ҫ���ø����ֵ�onLayout����ô��view��onMeasure����Ҳ�ᱻ����
			requestLayout();
		}
	}

	/**
	 * dp2px
	 */
	public int dp2px(int val) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, getResources().getDisplayMetrics());
	}
}
