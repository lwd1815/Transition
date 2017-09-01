package com.example.propertyanimation.chat.qq;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseDeepActivity;
import com.example.propertyanimation.chat.adapter.ChatAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class QQChatActivity extends BaseDeepActivity
    implements TextWatcher, SwipeRefreshLayout.OnRefreshListener {

  @BindView(R.id.tv_title) TextView tvTitle;
  @BindView(R.id.ib_back) ImageButton ibBack;
  @BindView(R.id.ib_add) ImageButton ibAdd;
  @BindView(R.id.lv_chat) ListView lvChat;
  @BindView(R.id.srl_refresh) SwipeRefreshLayout srlRefresh;
  @BindView(R.id.et_msg) EditText etMsg;
  @BindView(R.id.bt_send) Button btSend;

  private List<EMMessage> emMessages = new ArrayList<>();
  private String username;
  private EMConversation conversation;
  private ChatAdapter chatAdapter;

  @Override protected void initActivity() {
    setContentView(R.layout.activity_qqchat);
    ButterKnife.bind(this);
    username = getIntent().getStringExtra("username");
    tvTitle.setText("与" + username + "聊天中");

    //监听消息输入
    etMsg.addTextChangedListener(this);

    chatAdapter = new ChatAdapter();
    chatAdapter.setData(emMessages);
    lvChat.setAdapter(chatAdapter);

    getMessageHistory();
    srlRefresh.setOnRefreshListener(this);
  }

  @Override public void onRefresh() {
    EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
    EMMessage emmessage = emMessages.get(0);
    //初始化加载的聊天记录为20,到订时需要去db里获取更多
    //获取startMsgId之前的pagesize条消息,此方法获取的message SDK会自动存入到此会话中,app中无需再次把获取的message添加到会话中
    conversation.loadMoreMsgFromDB(emmessage.getMsgId(),20);
    List<EMMessage> messages=conversation.getAllMessages();
    emMessages.clear();
    emMessages.addAll(messages);
    chatAdapter.notifyDataSetChanged();
    srlRefresh.setRefreshing(false);
  }

  @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override public void afterTextChanged(Editable editable) {
      if (editable.length()>0){
        btSend.setEnabled(true);
      }else {
        btSend.setEnabled(false);
      }
  }

  public void getMessageHistory() {
    conversation = EMClient.getInstance().chatManager().getConversation(username);
    if (conversation == null) {
      return;
    }

    //把所有消息设置为已读,指定会话消息的未读数清零
    conversation.markAllMessagesAsRead();
    //获取此会话的所有消息
    List<EMMessage> messages = conversation.getAllMessages();

    emMessages.addAll(messages);
    chatAdapter.notifyDataSetChanged();
    lvChat.setSelection(emMessages.size());
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  @OnClick(R.id.bt_send) public void onViewClicked() {
    //发消息
    //创建一条文本消息,content为消息文字内容,toChatUsername为对方用户或者群聊的id,后文皆是如此
    EMMessage emMessage = EMMessage.createTxtSendMessage(etMsg.getText().toString(),username);
    //发消息
    EMClient.getInstance().chatManager().sendMessage(emMessage);

    chatAdapter.notifyDataSetChanged();
    lvChat.setSelection(emMessages.size());
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(EMMessage message) {
    //指定会话消息未读数清零
    conversation.markAllMessagesAsRead();
    emMessages.add(message);
    chatAdapter.notifyDataSetChanged();
    lvChat.setSelection(emMessages.size());
  }


  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  }
