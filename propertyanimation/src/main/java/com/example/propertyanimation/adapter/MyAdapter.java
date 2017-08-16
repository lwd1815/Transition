package com.example.propertyanimation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.example.propertyanimation.R;

/**
 * 创建者     李文东
 * 创建时间   2017/8/16 12:39
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = View.inflate(parent.getContext(), R.layout.item,null);
    return new MyViewHolder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 50;
  }
  public class MyViewHolder extends RecyclerView.ViewHolder{

    public MyViewHolder(View itemView) {
      super(itemView);
    }
  }
}
