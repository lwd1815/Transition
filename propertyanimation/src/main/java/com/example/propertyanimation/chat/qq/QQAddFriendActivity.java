package com.example.propertyanimation.chat.qq;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseDeepActivity;
import com.example.propertyanimation.chat.adapter.AddFriendAdapter;
import com.example.propertyanimation.chat.bean.UserBean;
import com.example.propertyanimation.chat.utils.ThreadUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import java.util.ArrayList;
import java.util.List;

public class QQAddFriendActivity extends BaseDeepActivity {

  @BindView(R.id.tv_title) TextView tvTitle;
  @BindView(R.id.ib_back) ImageButton ibBack;
  @BindView(R.id.ib_add) ImageButton ibAdd;
  @BindView(R.id.et_username) EditText etUsername;
  @BindView(R.id.ib_search) ImageButton ibSearch;
  @BindView(R.id.lv_friend) ListView lvFriend;
  private AddFriendAdapter addFriendAdapter;
  private List<UserBean> userBeanList = new ArrayList<>();
  @Override protected void initActivity() {
    setContentView(R.layout.activity_qqadd_friend);
    ButterKnife.bind(this);

    tvTitle.setText("四方好汉来相聚");
    addFriendAdapter = new AddFriendAdapter();
    addFriendAdapter.setData(userBeanList);
    lvFriend.setAdapter(addFriendAdapter);
    getUsers();
  }

  private void getUsers() {

    // 从bmob查询所有用户
    // select * from user where username like %a% and username != 自己
    BmobQuery<UserBean> query = new BmobQuery<UserBean>();
    query.addWhereContains("username", etUsername.getText().toString());
    query.addWhereNotEqualTo("username", EMClient.getInstance().getCurrentUser());
    query.findObjects(new FindListener<UserBean>() {

      @Override
      public void done(final List<UserBean> object, BmobException e) {
        if(e==null){

          ThreadUtil.executeSubThread(new Runnable() {
            @Override
            public void run() {
              try {
                // 获取环信自己的好友列表
                List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                addFriendAdapter.setContacts(usernames);


                ThreadUtil.executeMainThread(new Runnable() {
                  @Override
                  public void run() {
                    // 更新Listview
                    userBeanList.addAll(object);
                    addFriendAdapter.notifyDataSetChanged();
                  }
                });

              } catch (HyphenateException e1) {
                e1.printStackTrace();
              }
            }
          });

        }
      }

    });
  }
}
