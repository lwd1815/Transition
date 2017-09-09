package com.example.propertyanimation.recycleview.view;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.example.propertyanimation.recycleview.RvAdapter;
import java.util.Collections;

/**
 * 创建者     李文东
 * 创建时间   2017/9/9 11:16
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class RecyclerItemTouchHelperCallback extends ItemTouchHelper.Callback {

  private RecyclerView.Adapter adapters;
  boolean isSwipeEnables;
  boolean isFirstDragUnables;
  public RecyclerItemTouchHelperCallback(RecyclerView.Adapter adapter) {
    adapters=adapter;
    isFirstDragUnables=false;
    isSwipeEnables=true;
  }
  public RecyclerItemTouchHelperCallback(RecyclerView.Adapter adapter,boolean isFirstDragUnable,boolean isSwipeEnable) {
    adapters=adapter;
    isFirstDragUnables=isFirstDragUnable;
    isSwipeEnables=isSwipeEnable;
  }
  @Override
  public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    if (recyclerView.getLayoutManager() instanceof GridLayoutManager){
      int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT;
      int swipeFlags=0;
      return makeMovementFlags(dragFlags,swipeFlags);
    }else {
      int dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
      int siwpeFlags = ItemTouchHelper.START|ItemTouchHelper.END;
      return makeMovementFlags(dragFlags, siwpeFlags);
    }
  }

  @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
      RecyclerView.ViewHolder target) {
    int fromPosition = viewHolder.getAdapterPosition();
    int toPosition = target.getAdapterPosition();
    if (isFirstDragUnables&&toPosition==0){
      return false;
    }

    if (fromPosition<toPosition){
      for (int i = fromPosition; i < toPosition; i++) {
        Collections.swap( ((RvAdapter) adapters).getdataList(),i,i+1);
      }
    }else {
      for (int i = fromPosition; i > toPosition; i--) {
        Collections.swap(((RvAdapter)adapters).getdataList(),i,i-1);
      }
    }
    adapters.notifyItemMoved(fromPosition,toPosition);
    return false;
  }

  @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    int adapterPosition = viewHolder.getAdapterPosition();
    adapters.notifyItemRemoved(adapterPosition);
    ((RvAdapter) adapters).getdataList().remove(adapterPosition);
  }

  @Override public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
    if (actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
      viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
    }
    super.onSelectedChanged(viewHolder, actionState);
  }

  @Override public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    super.clearView(recyclerView, viewHolder);
    viewHolder.itemView.setBackgroundColor(Color.WHITE);
  }

  @Override public boolean isLongPressDragEnabled() {
    return !isFirstDragUnables;
  }

  @Override public boolean isItemViewSwipeEnabled() {
    return isSwipeEnables;
  }
}
