package com.example.propertyanimation.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.example.propertyanimation.R;
import java.util.List;

/**
 * 创建者     李文东
 * 创建时间   2017/12/26 15:35
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
  private int resourses;

  /**
   * 字母表分组工具
   */
  private SectionIndexer mIndexer;
  public ContactAdapter(@NonNull Context context, int resource, List<Contact> objects) {
    super(context, resource, objects);
    resourses=resource;
  }

  @NonNull @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Contact contact=getItem(position);
    LinearLayout layout=null;
    if (convertView==null){
      layout= (LinearLayout) LayoutInflater.from(getContext()).inflate(resourses,null);
    }else{
      layout= (LinearLayout) convertView;
    }

    TextView name=layout.findViewById(R.id.name);
    LinearLayout sortkeyLayout=layout.findViewById(R.id.sort_key_layout);
    TextView sortkey=layout.findViewById(R.id.sort_key);
    name.setText(contact.getName());
    int section=mIndexer.getSectionForPosition(position);
    if (position==mIndexer.getPositionForSection(section)){
      sortkey.setText(contact.getSortkey());
      sortkeyLayout.setVisibility(View.VISIBLE);
    }else{
      sortkeyLayout.setVisibility(View.GONE);
    }
    return layout;
  }

  /**
   * 给当前适配器传入一个分组工具
   */
  public void setIndexer(SectionIndexer indexer){
    mIndexer=indexer;
  }
}
