package com.example.propertyanimation.chat.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.propertyanimation.R;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import java.util.Date;
import java.util.List;

/**
 * Created by huang on 2017/2/26.
 */

public class ConversationAdapter extends BaseAdapter {

    private List<EMConversation> emConversations;

    public void setData(List<EMConversation> data) {
        emConversations = data;
    }

    @Override
    public int getCount() {
        return emConversations.size();
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
            view = View.inflate(viewGroup.getContext(), R.layout.item_conversation, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        // 判断是否有未读消息
        EMConversation emConversation = emConversations.get(i);

        if(emConversation.getUnreadMsgCount()>0){
            holder.tvUnreadmsg.setVisibility(View.VISIBLE);
            holder.tvUnreadmsg.setText(emConversation.getUnreadMsgCount()+"");
        }else{
            holder.tvUnreadmsg.setVisibility(View.GONE);
        }

        EMMessage lastMessage = emConversation.getLastMessage();
        EMTextMessageBody body = (EMTextMessageBody) lastMessage.getBody();
        holder.tvUsername.setText(emConversation.getLastMessage().getUserName());
        holder.tvLastmsg.setText(body.getMessage());
        holder.tvLastmsgtime.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_username) TextView tvUsername;
        @BindView(R.id.tv_lastmsg) TextView tvLastmsg;
        @BindView(R.id.tv_lastmsgtime) TextView tvLastmsgtime;
        @BindView(R.id.tv_unreadmsg) TextView tvUnreadmsg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
