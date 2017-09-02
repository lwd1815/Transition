package com.example.propertyanimation.chat.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.propertyanimation.R;
import com.example.propertyanimation.chat.adapter.ContactsAdapter;
import com.example.propertyanimation.chat.base.BaseFragment;
import com.example.propertyanimation.chat.qq.QQAddFriendActivity;
import com.example.propertyanimation.chat.qq.QQChatActivity;
import com.example.propertyanimation.chat.utils.ThreadUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2017/2/24.
 */

public class ContactsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.ib_add) ImageButton ibAdd;
    @BindView(R.id.lv_contacts) ListView lvContacts;
    @BindView(R.id.srl_refresh) SwipeRefreshLayout srlRefresh;
    private ContactsAdapter contactsAdapter;
    private List<String> usernames = new ArrayList<>();

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_contacts, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        tvTitle.setText("联系人");
        ibAdd.setVisibility(View.VISIBLE);
        contactsAdapter = new ContactsAdapter();
        contactsAdapter.setData(usernames);
        lvContacts.setAdapter(contactsAdapter);
        // 获取联系人
        getContacts();
        srlRefresh.setOnRefreshListener(this);
        lvContacts.setOnItemLongClickListener(this);
        lvContacts.setOnItemClickListener(this);
    }

    private void getContacts() {
        ThreadUtil.executeSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<String> list = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    ThreadUtil.executeMainThread(new Runnable() {
                        @Override
                        public void run() {
                            usernames.clear();
                            usernames.addAll(list);
                            contactsAdapter.notifyDataSetChanged();
                            srlRefresh.setRefreshing(false);
                        }
                    });


                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        getContacts();
    }


    @OnClick(R.id.ib_add)
    public void onClick() {
        startActivity(new Intent(mActivity, QQAddFriendActivity.class));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        ThreadUtil.executeSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(usernames.get(i));
                    getContacts();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(mActivity, QQChatActivity.class);
        intent.putExtra("username",usernames.get(i));
        startActivity(intent);
    }
}
