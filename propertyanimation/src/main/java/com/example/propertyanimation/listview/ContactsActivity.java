package com.example.propertyanimation.listview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AlphabetIndexer;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.example.propertyanimation.R;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
  private static final int TAG_PERMISSION = 1023;
  private CustomButton alphabetButton;
  private TextView sectionToastText;
  private RelativeLayout sectionToastLayout;
  private LinearLayout titleLayout;
  //分组上显示的字母
  private TextView title;
  private ListView contactsListView;
  private ContactAdapter adapter;
  //用于进行字母表分组
  private AlphabetIndexer indexer;
  //存储所有手机中的联系人
  private List<Contact> contacts = new ArrayList<>();
  //定义字母表的排序规则
  private String alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  //上一次第一个可见元素,用于滚动时记录表识
  private int lastFirstVisibleItem = -1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contacts);
    ButterKnife.bind(this);

    adapter = new ContactAdapter(this, R.layout.contact_item, contacts);
    titleLayout = (LinearLayout) findViewById(R.id.title_layout);
    title = (TextView) findViewById(R.id.title);
    contactsListView = (ListView) findViewById(R.id.contacts_list_view);
    alphabetButton= (CustomButton) findViewById(R.id.alphabetButton);
    sectionToastLayout= (RelativeLayout) findViewById(R.id.section_toast_layout);
    sectionToastText= (TextView) findViewById(R.id.section_toast_text);
    /**
     * android6.0权限
     */
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {

      if (ActivityCompat.shouldShowRequestPermissionRationale(this,
          Manifest.permission.READ_CONTACTS)) {
        Toast.makeText(ContactsActivity.this, "deny for what???", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(ContactsActivity.this, "show the request popupwindow", Toast.LENGTH_SHORT)
            .show();
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CONTACTS },
            TAG_PERMISSION);
      }
    } else {
      Toast.makeText(ContactsActivity.this, "agreed", Toast.LENGTH_SHORT).show();
      Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
      //sort_key对应系统联系人数据库中要用到的排序键的列,display_name对应系统联系人中姓名的列
      Cursor cursor =
          getContentResolver().query(uri, new String[] { "display_name", "sort_key" }, null, null,
              "sort_key");
      if (cursor.moveToFirst()) {
        do {
          String name = cursor.getString(0);
          String sortKey = getSortKey(cursor.getString(1));
          Contact contact = new Contact();
          contact.setName(name);
          contact.setSortkey(sortKey);
          contacts.add(contact);
        } while (cursor.moveToNext());
      }
      startManagingCursor(cursor);
      indexer = new AlphabetIndexer(cursor, 1, alphabet);
      adapter.setIndexer(indexer);
      if (contacts.size() > 0) {
        setupContactsListView();
        setAlpabetListener();
      }
    }
  }

  /**
   * 设置字母表上的触摸事件,根据当前触摸的位置结合字母表的高度,计算出当前触摸在那个字母上
   * 当手指按在字母表上时,展示弹出式分组,手指离开字母表时,将弹出式分组隐藏
   */
  @SuppressLint("ClickableViewAccessibility") private void setAlpabetListener() {
    alphabetButton.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View view, MotionEvent motionEvent) {
        float alphabetHeight=alphabetButton.getHeight();
        float y=motionEvent.getY();
        int sectionPosition= (int) ((y/alphabetHeight)/(1f/27f));
        if (sectionPosition<0){
          sectionPosition=0;
        }else if (sectionPosition>26){
          sectionPosition=26;
        }
        String sectionLetter=String.valueOf(alphabet.charAt(sectionPosition));
        int position=indexer.getPositionForSection(sectionPosition);
        //如果联系人中没有的字母,就不显示
        Contact contact=contacts.get(position);
        switch (motionEvent.getAction()){
          case MotionEvent.ACTION_DOWN:
            alphabetButton.setBackgroundResource(R.drawable.a_z);
            sectionToastLayout.setVisibility(View.VISIBLE);
            //sectionToastText.setText(contact.getSortkey());
            sectionToastText.setText(sectionLetter);
            contactsListView.setSelection(position);
            break;
          case MotionEvent.ACTION_MOVE:
            //sectionToastText.setText(contact.getSortkey());
            sectionToastText.setText(sectionLetter);
            contactsListView.setSelection(position);
            break;
          default:
              alphabetButton.setBackgroundResource(R.drawable.a_z);
              sectionToastLayout.setVisibility(View.GONE);
        }
        return true;
      }
    });
  }

  /**
   * 为联系人ListView设置监听事件，根据当前的滑动状态来改变分组的显示位置，从而实现挤压动画的效果。
   */
  private void setupContactsListView() {
    contactsListView.setAdapter(adapter);
    contactsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
      }

      @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
          int totalItemCount) {
        //从第一个可见的位置获取所在的组
        int section = indexer.getSectionForPosition(firstVisibleItem);
        //根据当前的组获取下一个组
        int nextSecPosition = indexer.getPositionForSection(section + 1);
        //如果第一个可见的item不是上一次第一次可见的item
        if (firstVisibleItem != lastFirstVisibleItem) {
          //
          ViewGroup.MarginLayoutParams params =
              (ViewGroup.MarginLayoutParams) titleLayout.getLayoutParams();
          params.topMargin = 0;
          titleLayout.setLayoutParams(params);
          //从字符串alphabet中选取对应的组的所在的字母做标题
          title.setText(String.valueOf(alphabet.charAt(section)));
        }
        //如果获取的下一个组等于第一个可见的item加一说明第一个可见的组中没有其他的成员
        if (nextSecPosition == firstVisibleItem + 1) {
          View childView = view.getChildAt(0);
          if (childView != null) {
            int titleHeight = titleLayout.getHeight();
            int bottom = childView.getBottom();
            ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) titleLayout.getLayoutParams();
            if (bottom < titleHeight) {
              float pushedDistance = bottom - titleHeight;
              params.topMargin = (int) pushedDistance;
              titleLayout.setLayoutParams(params);
            } else {
              if (params.topMargin != 0) {
                params.topMargin = 0;
                titleLayout.setLayoutParams(params);
              }
            }
          }
        }
        lastFirstVisibleItem = firstVisibleItem;
      }
    });
  }

  /**
   * 获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
   *
   * @param sortKeyString 数据库中读取出的sort key
   * @return 英文字母或者#
   */
  private String getSortKey(String sortKeyString) {
    String key = sortKeyString.substring(0, 1).toUpperCase();
    if (key.matches("[A-Z]")) {
      return key;
    }
    return "#";
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case TAG_PERMISSION: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          Toast.makeText(ContactsActivity.this, "allow", Toast.LENGTH_SHORT).show();
          //getContact();
        } else {
          Toast.makeText(ContactsActivity.this, "deny", Toast.LENGTH_SHORT).show();
        }
        return;
      }
    }
  }
}
