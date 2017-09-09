package com.example.propertyanimation.recycleview.view;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 创建者     李文东
 * 创建时间   2017/9/9 11:16
 * 描述	      recyclerView的点击和长按监听
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public abstract class OnRecycleviewrItemClickListener implements OnItemTouchListener{

  private RecyclerView mRecyclerView;
  private final GestureDetectorCompat gestureDetectorCompat;

  public OnRecycleviewrItemClickListener(RecyclerView recyclerView) {
    this.mRecyclerView=recyclerView;
    gestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext()
        ,new ItemTouchHelperGestureListener());
  }

  //处理拦截事件
  @Override public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
    gestureDetectorCompat.onTouchEvent(e);
    return false;
  }

  //处理触摸事件的
  @Override public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    gestureDetectorCompat.onTouchEvent(e);

  }

  //处理冲突
  @Override public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

  }
  public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);
  public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);
  private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
    @Override public boolean onSingleTapConfirmed(MotionEvent e) {

      //用于获取指定位置的view
      View childViewUnder=mRecyclerView.findChildViewUnder(e.getX(),e.getY());
      if (childViewUnder!=null){
        //只会在当前显示的children中查找
        RecyclerView.ViewHolder childerViewHolder=mRecyclerView.getChildViewHolder(childViewUnder);
        onItemClick(childerViewHolder);
      }
      return true;
    }

    @Override public void onLongPress(MotionEvent e) {
      View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
      if (childViewUnder!=null){
        RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
        onLongClick(childViewHolder);
      }
    }
  }
}
