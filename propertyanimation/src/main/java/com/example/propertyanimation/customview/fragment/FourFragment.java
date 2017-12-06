package com.example.propertyanimation.customview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseFragment;
import com.example.propertyanimation.customview.view.FourView;

/**
 * 创建者     李文东
 * 创建时间   2017/8/24 16:25
 * 描述	      自定义view绘图篇之区域
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class FourFragment extends BaseFragment {
  public FourFragment(){}

  public static FourFragment getInstance(){
    FourFragment fragment = new FourFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = View.inflate(container.getContext(), R.layout.fragment_child_four,null);
    FrameLayout frameLayout = view.findViewById(R.id.four_child);
    FourView fourView= new FourView(container.getContext());
    frameLayout.addView(fourView);
    return view;
  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
  }
}
