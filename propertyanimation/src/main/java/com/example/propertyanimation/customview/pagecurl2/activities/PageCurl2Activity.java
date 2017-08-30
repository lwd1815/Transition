package com.example.propertyanimation.customview.pagecurl2.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.example.propertyanimation.R;
import com.example.propertyanimation.customview.pagecurl2.views.FoldView;
import com.example.propertyanimation.customview.pagecurl2.views.PageTurnView;
import java.util.ArrayList;
import java.util.List;



/**
 * 应用主界面
 * 
 * @author AigeStudio
 * @since 2014/12/15
 * @version 1.0.0
 * 
 */
public class PageCurl2Activity extends Activity {
	private PageTurnView mPageCurlView;// 翻页控件
	private FoldView mFoldView;// 折页控件

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curl_page_v2);

		foldPage();

		// turnPage();
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
		super.onDestroy();
	}
}
