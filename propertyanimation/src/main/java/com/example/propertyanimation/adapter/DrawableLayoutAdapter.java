package com.example.propertyanimation.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.propertyanimation.CollapsingToolbarLayoutActivity;
import com.example.propertyanimation.MainActivity;
import com.example.propertyanimation.R;
import com.example.propertyanimation.customview.CustomViewActivity;
import com.example.propertyanimation.customview.pageCurl4.activities.PageCurl4Activity;
import com.example.propertyanimation.customview.pagecurl.activities.PageCurlActivity;
import com.example.propertyanimation.customview.pagecurl2.activities.PageCurl2Activity;
import com.example.propertyanimation.customview.pagecurl3.activities.PageCurl3Activity;
import com.example.propertyanimation.expand.ExpandMainActivity;
import com.example.propertyanimation.expand.Main2Activity;
import com.example.propertyanimation.testcollapsingtoolbarlayout.TestMyRectActivity;

/**
 * 创建者     李文东
 * 创建时间   2017/8/23 10:08
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class DrawableLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = View.inflate(parent.getContext(), R.layout.item_drawable,null);
    return new NormalViewHolder(view);
  }

  @Override public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
    if (holder instanceof NormalViewHolder){

      ((NormalViewHolder) holder).tv.setText("书架 "+position);
      ((NormalViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (position==0) {
            Intent intent = new Intent(holder.itemView.getContext(), CollapsingToolbarLayoutActivity.class);
            holder.itemView.getContext().startActivity(intent);
          }else if (position==1){
            Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
            holder.itemView.getContext().startActivity(intent);
          }else if (position==2){
            Intent intent = new Intent(holder.itemView.getContext(), ExpandMainActivity.class);
            holder.itemView.getContext().startActivity(intent);
          }else if (position==3){
            Intent intent = new Intent(holder.itemView.getContext(), Main2Activity.class);
            holder.itemView.getContext().startActivity(intent);
          }else if (position==4){
            Intent intent = new Intent(holder.itemView.getContext(), CustomViewActivity.class);
            holder.itemView.getContext().startActivity(intent);
          }else if (position==5){
            Intent intent = new Intent(holder.itemView.getContext(), PageCurlActivity.class);
            holder.itemView.getContext().startActivity(intent);
          }else if (position==6){
            Intent intent = new Intent(holder.itemView.getContext(), PageCurl2Activity.class);
            holder.itemView.getContext().startActivity(intent);
          }else if (position==7){
            Intent intent = new Intent(holder.itemView.getContext(), PageCurl3Activity.class);
            holder.itemView.getContext().startActivity(intent);
          }else if (position==8){
            Intent intent = new Intent(holder.itemView.getContext(), PageCurl4Activity.class);
            holder.itemView.getContext().startActivity(intent);
          }else if (position==9){
            Intent intent = new Intent(holder.itemView.getContext(), TestMyRectActivity.class);
            holder.itemView.getContext().startActivity(intent);
          }
        }
      });
    }
  }

  @Override public int getItemCount() {
    return 20;
  }

  public static class NormalViewHolder extends RecyclerView.ViewHolder{
    TextView tv;
    public NormalViewHolder(View itemView) {
      super(itemView);
      tv= itemView.findViewById(R.id.tv);
    }
  }
}
