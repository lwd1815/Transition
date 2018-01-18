package com.example.propertyanimation.lrucache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.propertyanimation.R;

public class BitmapLruCacheActivity extends AppCompatActivity {
  @BindView(R.id.iv) ImageView iv;
  private String TAG = "SIZE";
  private BitmapLruCache lruCache;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bitmap_lru_cache);
    ButterKnife.bind(this);
    int size = (int) (Runtime.getRuntime().maxMemory() / 1024 / 8);
    Log.v(TAG, size + "");
    lruCache = new BitmapLruCache(size);
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
    //给lrucache添加一张图片
    lruCache.put("bitmap", bitmap);
    //从lrucahce中获取一张图片
    Glide.with(getBaseContext()).load(lruCache.get("bitmap")).apply(new RequestOptions().circleCrop()).into(iv);
  }
}
