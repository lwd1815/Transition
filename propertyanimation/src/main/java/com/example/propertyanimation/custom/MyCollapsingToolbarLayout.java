package com.example.propertyanimation.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.example.propertyanimation.R;

/**
 * 创建者     李文东
 * 创建时间   2017/8/16 16:18
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class MyCollapsingToolbarLayout extends CollapsingToolbarLayout{
  public MyCollapsingToolbarLayout(Context context) {
    //super(context);
    this(context,null);
  }

  public MyCollapsingToolbarLayout(Context context, AttributeSet attrs) {
    //super(context, attrs);
    this(context,attrs,-1);
  }

  public MyCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    View view = View.inflate(getContext(), R.layout.custom,null);
    addView(view);
  }

  @Override public void setTitle(@Nullable CharSequence title) {
    //super.setTitle(title);
    TextView textView = new TextView(getContext());
    textView.setBackground(getContext().getResources().getDrawable(R.color.white));
    textView.setHeight(20);
    textView.setWidth(100);
    textView.layout(0,0,100,20);

  }
  public void setTitle(int drawable) {
    //super.setTitle(title);
  }
}
