package com.example.propertyanimation.chat.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.propertyanimation.R;
import java.util.List;

/**
 * Created by huang on 2017/2/24.
 */

public class ContactsAdapter extends BaseAdapter {
    private List<String> data;

    public void setData(List<String> usernames) {
        data = usernames;
    }
    public List<String> getData(){
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.item_contacts, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }

        String username = data.get(i);
        String firstLetter = getFirstLetter(username);
        // 判断隐藏索引
        if(i==0){
            holder.tvSection.setVisibility(View.VISIBLE);
            holder.tvSection.setText(firstLetter);
        }else{
            String preFirstLetter = getFirstLetter(data.get(i - 1));// 获取前一条数据的首字母
            if(firstLetter.equals(preFirstLetter)){
                holder.tvSection.setVisibility(View.GONE);
            }else{
                holder.tvSection.setVisibility(View.VISIBLE);
                holder.tvSection.setText(firstLetter);
            }
        }


        holder.tvSection.setText(firstLetter);
        holder.tvUsername.setText(username);
        return view;
    }
    private String getFirstLetter(String str){
        return str.substring(0,1).toUpperCase();
    }

    static class ViewHolder {
        @BindView(R.id.tv_section) TextView tvSection;
        @BindView(R.id.tv_username) TextView tvUsername;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
