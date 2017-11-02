package com.example.propertyanimation.customview.pagecurl.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.example.propertyanimation.R;
import com.example.propertyanimation.customview.pagecurl.views.PageTurnView;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * 
 * @author AigeStudio
 * @since 2014/12/15
 * @version 1.0.0
 * 
 */
public class PageCurlActivity extends Activity {
	private PageTurnView mPageCurlView;// ��ҳ�ؼ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curl_page);

		mPageCurlView = (PageTurnView) findViewById(R.id.main_pcv);

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

		mPageCurlView.setBitmaps(bitmaps);
	}
}
