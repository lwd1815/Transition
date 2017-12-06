package com.example.propertyanimation.customview.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseFragment;
import com.example.propertyanimation.customview.view.FirstView;

/**
 * 创建者     李文东
 * 创建时间   2017/8/24 16:25
 * 描述	      自定义第一学
 *
 * android中自定义一个view类并一般都是集成view类或者view的自类
 *
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   在fragment中开一个线程,通过handler来定时间段的设置半径的值并刷新页面
 */

public class FirstFragment extends BaseFragment {
  private FirstView mFirstView;
  private int radiu;

  private Handler mhandler = new Handler(){
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
      mFirstView.setRadiu(radiu);
    }
  };


  public FirstFragment(){}

  public static FirstFragment  getInstance(){
    FirstFragment fragment = new FirstFragment();
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
    View view = View.inflate(container.getContext(), R.layout.fragment_child_first,null);

    //获取控件
    mFirstView=view.findViewById(R.id.first_view);
    /*//开线程
    new Thread(new Runnable() {
      @Override public void run() {
        //确保线程不断的刷新
        while (true) {
          try {
            if (radiu <= 200) {
              radiu += 10;

              //发消息给handler处理
              mhandler.obtainMessage().sendToTarget();
            }else {
              radiu=0;
            }
            //每执行一次暂停40毫秒
            Thread.sleep(40);
          }catch (Exception e){
            e.printStackTrace();
          }
        }
      }
    }).start();*/

    /**
     * 将开启线程的逻辑放在自定义view中后
     */

    new Thread(mFirstView).start();
    return view;
  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);

  }

  @Override public void onDestroy() {
    super.onDestroy();
    //销毁界面清除handler的引用
    mhandler.removeCallbacksAndMessages(null);
  }
}
