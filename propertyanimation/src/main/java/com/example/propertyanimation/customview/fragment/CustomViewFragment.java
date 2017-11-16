package com.example.propertyanimation.customview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * 创建者     李文东
 * 创建时间   2017/8/24 16:25
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class CustomViewFragment extends BaseFragment {

  private ImageView backMyOrders;
  private TextView toolbarMyOrders;
  private RelativeLayout toolbarMyOrder;
  private SmartTabLayout myOrderTabLayout;
  private ViewPager vpMyOrder;

  public CustomViewFragment() {
  }

  private View mView;
  private FragmentPagerItems pages;
  private FragmentPagerItemAdapter adapter;
  private int type;

  public static CustomViewFragment getInstance(int type) {
    Bundle args = new Bundle();
    args.putInt("type", type);
    CustomViewFragment fragment = new CustomViewFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    type = getArguments().getInt("type", 0);
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mView = View.inflate(container.getContext(), R.layout.fragment_customview, null);

    backMyOrders = (ImageView) mView.findViewById(R.id.back_myOrders);
    toolbarMyOrders = mView.findViewById(R.id.toolbar_myOrders);
    toolbarMyOrder = mView.findViewById(R.id.toolbar_myOrder);
    myOrderTabLayout = mView.findViewById(R.id.myOrder_tabLayout);
    vpMyOrder = mView.findViewById(R.id.vp_myOrder);

    initToolBar();
    inittab();
    // TODO: 2017/5/27 有待优化
    /**
     * 强制设置viewpage的子页面是4,防止滑动之后销毁
     */
    vpMyOrder.setOffscreenPageLimit(6);
    return mView;
  }

  private void initToolBar() {
    backMyOrders.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().finish();
      }
    });
  }

  private void inittab() {
    Bundle bundle1 = new Bundle();
    bundle1.putInt("id", 1);
    Bundle bundle2 = new Bundle();
    bundle2.putInt("id", 2);
    Bundle bundle3 = new Bundle();
    bundle3.putInt("id", 3);
    Bundle bundle4 = new Bundle();
    bundle4.putInt("id", 4);
    Bundle bundle5 = new Bundle();
    bundle5.putInt("id", 5);
    Bundle bundle6 = new Bundle();
    bundle6.putInt("id", 6);
    Bundle bundle7 = new Bundle();
    bundle1.putInt("id", 7);
    Bundle bundle8 = new Bundle();
    bundle2.putInt("id", 8);
    Bundle bundle9 = new Bundle();
    bundle3.putInt("id", 9);
    Bundle bundle10 = new Bundle();
    bundle4.putInt("id", 10);
    Bundle bundle11 = new Bundle();
    bundle5.putInt("id", 11);
    Bundle bundle12 = new Bundle();
    bundle6.putInt("id", 12);

    pages = new FragmentPagerItems(_mActivity);
    pages.add(FragmentPagerItem.of("待定义", FirstFragment.class, bundle1));
    pages.add(FragmentPagerItem.of("待付款", SecondFragment.class, bundle2));
    pages.add(FragmentPagerItem.of("待成团", ThridFragment.class, bundle3));
    pages.add(FragmentPagerItem.of("待发货", FourFragment.class, bundle4));
    pages.add(FragmentPagerItem.of("待收货", FiveFragment.class, bundle5));
    pages.add(FragmentPagerItem.of("待评价", SixFragment.class, bundle6));
    pages.add(FragmentPagerItem.of("待定义", SevenFragment.class, bundle7));
    pages.add(FragmentPagerItem.of("待付款", EgihtFragment.class, bundle8));
    pages.add(FragmentPagerItem.of("待成团", NineFragment.class, bundle9));
    pages.add(FragmentPagerItem.of("待发货", TenFragment.class, bundle10));
    pages.add(FragmentPagerItem.of("待收货", SYFragment.class, bundle11));
    pages.add(FragmentPagerItem.of("待评价", SRFragment.class, bundle12));
    adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
    vpMyOrder.setAdapter(adapter);
    myOrderTabLayout.setViewPager(vpMyOrder);
    if (type == 0) {
      vpMyOrder.setCurrentItem(0);
    } else if (type == 1) {
      vpMyOrder.setCurrentItem(1);
    } else if (type == 2) {
      vpMyOrder.setCurrentItem(2);
    } else if (type == 3) {
      vpMyOrder.setCurrentItem(3);
    } else if (type == 4) {
      vpMyOrder.setCurrentItem(4);
    } else if (type == 5) {
      vpMyOrder.setCurrentItem(5);
    } else if (type == 6) {
      vpMyOrder.setCurrentItem(6);
    } else if (type == 7) {
      vpMyOrder.setCurrentItem(7);
    } else if (type == 8) {
      vpMyOrder.setCurrentItem(8);
    } else if (type == 9) {
      vpMyOrder.setCurrentItem(9);
    } else if (type == 10) {
      vpMyOrder.setCurrentItem(10);
    } else if (type == 11) {
      vpMyOrder.setCurrentItem(11);
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }
}
