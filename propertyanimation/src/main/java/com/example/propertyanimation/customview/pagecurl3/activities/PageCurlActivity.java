package com.example.propertyanimation.customview.pagecurl3.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.example.propertyanimation.R;
import com.example.propertyanimation.customview.pagecurl3.views.CurveView;
import com.example.propertyanimation.customview.pagecurl3.views.FoldView;
import com.example.propertyanimation.customview.pagecurl3.views.PageTurnView;
import java.util.ArrayList;
import java.util.List;



/**
 * Ӧ��������
 * 
 * @author AigeStudio
 * @since 2014/12/15
 * @version 1.0.0
 * 
 */
public class PageCurlActivity extends Activity {
	private PageTurnView mPageCurlView;// ��ҳ�ؼ�
	private FoldView mFoldView;// ��ҳ�ؼ�
	private CurveView mCurveView;// ���߿ؼ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curl_page_v3);

		curvePage();

//		 foldPage();

		// turnPage();
	}

	private void curvePage() {
		mCurveView = (CurveView) findViewById(R.id.main);
		mCurveView.setBitmaps(initBitmaps());
	}

	private void foldPage() {
		mFoldView = (FoldView) findViewById(R.id.main);
		mFoldView.setBitmaps(initBitmaps());
	}

	private void turnPage() {
		mPageCurlView = (PageTurnView) findViewById(R.id.main);
		mPageCurlView.setBitmaps(initBitmaps());
	}

	private List<Bitmap> initBitmaps() {
		Bitmap bitmap = null;
		List<Bitmap> bitmaps = new ArrayList<Bitmap>();

		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page_img_a);
		bitmaps.add(bitmap);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page_img_b);
		bitmaps.add(bitmap);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page_img_c);
		bitmaps.add(bitmap);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page_img_d);
		bitmaps.add(bitmap);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page_img_e);
		bitmaps.add(bitmap);

		return bitmaps;
	}

	@Override
	protected void onDestroy() {
		if (null != mFoldView) {
			mFoldView.slideStop();
			mFoldView.getSlideHandler().removeCallbacksAndMessages(null);
		}
		if (null != mCurveView) {
			mCurveView.slideStop();
			mCurveView.getSlideHandler().removeCallbacksAndMessages(null);
		}
		super.onDestroy();
	}
}
