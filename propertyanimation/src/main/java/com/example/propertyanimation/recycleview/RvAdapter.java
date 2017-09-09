package com.example.propertyanimation.recycleview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.propertyanimation.R;
import java.util.Arrays;
import java.util.List;

/**
 * 创建者     李文东
 * 创建时间   2017/9/9 10:57
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

  private int itemlayout;
  private List<String> datalist;
  private List<Integer> mints;
  private boolean isFirstSpecial;

  public RvAdapter(int item_layout, List<String> dataList) {
    this.itemlayout = item_layout;
    this.datalist = dataList;
    mints = Arrays.asList(R.drawable.ic_fruit_icons_01, R.drawable.ic_fruit_icons_02,
        R.drawable.ic_fruit_icons_03, R.drawable.ic_fruit_icons_04, R.drawable.ic_fruit_icons_05,
        R.drawable.ic_fruit_icons_06);
  }

  public RvAdapter(int item_layout, List<String> datalist, boolean isFirstSpecial) {

    this(item_layout, datalist);
    this.isFirstSpecial = isFirstSpecial;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(itemlayout, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    String s = datalist.get(position);
    if (isFirstSpecial && position == 0) {
      holder.itemView.setBackgroundColor(Color.LTGRAY);
      holder.mtextview.setText("iPhone");
      holder.imageView.setImageResource(R.drawable.ic_iphone);
    } else {
      holder.itemView.setBackgroundColor(Color.WHITE);
      holder.mtextview.setText(s);
      holder.imageView.setImageResource(mints.get(position % mints.size()));
    }
  }

  @Override public int getItemCount() {
    return datalist.size() == 0 ? 0 : datalist.size();
  }
  class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mtextview;
    public ImageView imageView;

    public ViewHolder(View itemView) {
      super(itemView);
      mtextview = itemView.findViewById(R.id.tv_item);
      imageView = itemView.findViewById(R.id.img_item);
    }
  }

  public List<String> getdataList(){
    return datalist;
  }
}
