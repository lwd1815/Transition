package com.example.propertyanimation.chat.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.propertyanimation.R;
import com.example.propertyanimation.chat.adapter.ConversationAdapter;
import com.example.propertyanimation.chat.base.BaseFragment;
import com.example.propertyanimation.chat.qq.QQChatActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by huang on 2017/2/24.
 */

public class ConversationFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.lv_conversation) ListView lvConversation;
    private List<EMConversation> emConversations = new ArrayList<>();
    private ConversationAdapter conversationAdapter;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_conversation, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        tvTitle.setText("消息");
        conversationAdapter = new ConversationAdapter();
        conversationAdapter.setData(emConversations);
        lvConversation.setAdapter(conversationAdapter);

        lvConversation.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getConversation();
    }

    private void getConversation() {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        emConversations.clear();
        emConversations.addAll(conversations.values());
        Collections.sort(emConversations, new Comparator<EMConversation>() {
            @Override
            public int compare(EMConversation emConversation, EMConversation t1) {

                return (int) (t1.getLastMessage().getMsgTime()-emConversation.getLastMessage().getMsgTime());
            }
        });
        conversationAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EMMessage message) {
        getConversation();
    }

    ;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(mActivity, QQChatActivity.class);
        intent.putExtra("username",emConversations.get(i).getAllMessages().get(0).getUserName());
        mActivity.startActivity(intent);
    }
}
