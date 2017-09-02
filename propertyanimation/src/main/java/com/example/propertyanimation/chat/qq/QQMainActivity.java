package com.example.propertyanimation.chat.qq;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.propertyanimation.R;
import com.example.propertyanimation.base.BaseDeepActivity;
import com.example.propertyanimation.chat.utils.FragmentFactory;
import com.example.propertyanimation.chat.utils.ThreadUtil;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class QQMainActivity extends BaseDeepActivity implements BottomNavigationBar.OnTabSelectedListener,
    EMContactListener{

  @BindView(R.id.fl_fragment) FrameLayout flFragment;
  @BindView(R.id.bnb_bottom) BottomNavigationBar bnbBottom;
  @BindView(R.id.activity_main) LinearLayout activityMain;
  private BadgeItem numberBadgeItem;
  private AlertDialog.Builder builder;
  @Override protected void initActivity() {
    setContentView(R.layout.activity_qqmain);
    ButterKnife.bind(this);

    // 初始化Fragment
    showFragment(0);
    // 初始化底部导航
    initBottom();

    // 监听好友请求
    EMClient.getInstance().contactManager().setContactListener(this);
    builder = new AlertDialog.Builder(this);
  }

  private void showFragment(int index) {
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();
    transaction.replace(R.id.fl_fragment, FragmentFactory.getFragment(index));
    transaction.commit();
    // add findviewbyid Viewgroup  Viewgroup.addview(Fragment.onCreateView)
    // replace Viewgroup.remove(之前的Fragment.onCreateView)
  }

  private void initBottom() {
    numberBadgeItem = new BadgeItem();
    numberBadgeItem.setText("6");
    bnbBottom.setActiveColor(R.color.mainColor)
        .addItem(new BottomNavigationItem(R.drawable.conversation_selected_2, "消息").setBadgeItem(
            numberBadgeItem))
        .addItem(new BottomNavigationItem(R.drawable.contact_selected_2, "联系人"))
        .addItem(new BottomNavigationItem(R.drawable.plugin_selected_2, "动态"))
        .setFirstSelectedPosition(0)
        .initialise();
    // 监听点击事件
    bnbBottom.setTabSelectedListener(this);
  }

  @Override public void onTabSelected(int position) {
    showFragment(position);
  }

  @Override public void onTabUnselected(int position) {

  }

  @Override public void onTabReselected(int position) {

  }

  @Override public void onContactInvited(final String username, final String reason) {
    ThreadUtil.executeMainThread(new Runnable() {
      @Override public void run() {

        //收到好友邀请
        builder.setTitle("收到好友请求");
        builder.setMessage(username + ":" + reason);
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            ThreadUtil.executeSubThread(new Runnable() {
              @Override public void run() {
                try {
                  EMClient.getInstance().contactManager().acceptInvitation(username);
                } catch (HyphenateException e) {
                  e.printStackTrace();
                }
              }
            });
          }
        });
        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            ThreadUtil.executeSubThread(new Runnable() {
              @Override public void run() {
                try {
                  EMClient.getInstance().contactManager().declineInvitation(username);
                } catch (HyphenateException e) {
                  e.printStackTrace();
                }
              }
            });
          }
        });
        builder.show();
      }
    });
  }

  @Override public void onFriendRequestAccepted(String s) {
    //好友请求被同意
    ThreadUtil.executeMainThread(new Runnable() {
      @Override public void run() {
        Toast.makeText(getApplicationContext(), "同意", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override public void onFriendRequestDeclined(String s) {
    //好友请求被拒绝
    ThreadUtil.executeMainThread(new Runnable() {
      @Override public void run() {
        Toast.makeText(getApplicationContext(), "拒绝", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override public void onContactDeleted(String username) {
    //被删除时回调此方法
  }

  @Override public void onContactAdded(String username) {
    //增加了联系人时回调此方法
    ThreadUtil.executeMainThread(new Runnable() {
      @Override public void run() {
        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override protected void onResume() {
    super.onResume();
    // 获取所有的未读消息
    getUnReanMsg();
  }

  private void getUnReanMsg() {
    int unreadMsgsCount = EMClient.getInstance().chatManager().getUnreadMsgsCount();
    if (unreadMsgsCount > 0) {
      numberBadgeItem.setText(unreadMsgsCount + "");
      numberBadgeItem.show();
    } else {
      numberBadgeItem.hide();
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessageEvent(EMMessage message) {
    getUnReanMsg();
  }

  ;

  @Override public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }
}
