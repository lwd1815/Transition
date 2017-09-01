package com.example.propertyanimation.chat.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.propertyanimation.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import java.util.Date;
import java.util.List;

/**
 * Created by huang on 2017/2/25.
 */

public class ChatAdapter extends BaseAdapter {

    private List<EMMessage> emMessages;


    public void setData(List<EMMessage> emMessages) {
        this.emMessages = emMessages;
    }

    @Override
    public int getCount() {
        return emMessages.size();
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
            view = View.inflate(viewGroup.getContext(), R.layout.item_chat, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        EMMessage emMessage = emMessages.get(i);
        if(i==0){
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(DateUtils.getTimestampString(new Date(emMessage.getMsgTime())));
        }else{
            // 与上一条消息时间比较，如果距离近 就不展示
            if(DateUtils.isCloseEnough(emMessages.get(i-1).getMsgTime(),emMessage.getMsgTime())){
                holder.tvTime.setVisibility(View.GONE);
            }else{
                holder.tvTime.setVisibility(View.VISIBLE);
                holder.tvTime.setText(DateUtils.getTimestampString(new Date(emMessage.getMsgTime())));
            }
        }
        EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
        // 判断消息是发送还是接受
        if(emMessage.direct().equals(EMMessage.Direct.RECEIVE)){
            holder.llRight.setVisibility(View.GONE);
            holder.llLeft.setVisibility(View.VISIBLE);
            holder.tvMsgReceive.setText(body.getMessage());
        }else{
            holder.llRight.setVisibility(View.VISIBLE);
            holder.llLeft.setVisibility(View.GONE);
            holder.tvMsgSend.setText(body.getMessage());
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_msg_receive)
        TextView tvMsgReceive;
        @BindView(R.id.ll_left)
        LinearLayout llLeft;
        @BindView(R.id.tv_msg_send)
        TextView tvMsgSend;
        @BindView(R.id.ll_right)
        LinearLayout llRight;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
