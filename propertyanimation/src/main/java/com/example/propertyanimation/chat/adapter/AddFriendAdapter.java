package com.example.propertyanimation.chat.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.propertyanimation.R;
import com.example.propertyanimation.chat.bean.UserBean;
import com.example.propertyanimation.chat.utils.ThreadUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import java.util.List;

/**
 * Created by huang on 2017/2/25.
 */

public class AddFriendAdapter extends BaseAdapter {
    private List<UserBean> data;

    public void setData(List<UserBean> data) {
        this.data = data;
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
    public View getView(int i, View view, final ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.item_addfriend, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        // 展示已是好友
        final UserBean userBean = data.get(i);
        if(usernames.contains(userBean.getUsername())){
            holder.btAdd.setEnabled(false);
            holder.btAdd.setText("已是好友");
        }else {
            holder.btAdd.setEnabled(true);
            holder.btAdd.setText("添加好友");
            holder.btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 添加好友
                    //参数为要添加的好友的username和添加理由
                    AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                    final AlertDialog dialog = builder.create();
                    builder.setTitle("添加好友");
                    final EditText editText = new EditText(viewGroup.getContext());
                    builder.setView(editText);
                    builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ThreadUtil.executeSubThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        EMClient.getInstance().contactManager().addContact(userBean.getUsername(), editText.getText().toString());

                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            dialog.dismiss();
                        }
                    });
                    builder.show();

                }
            });
        }
        holder.tvUsername.setText(userBean.getUsername());

        return view;
    }
    private  List<String> usernames;
    public void setContacts(List<String> usernames) {
        this.usernames = usernames;
    }

    static class ViewHolder {
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.bt_add)
        Button btAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
