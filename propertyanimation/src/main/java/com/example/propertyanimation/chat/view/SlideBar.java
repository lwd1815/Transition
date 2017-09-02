package com.example.propertyanimation.chat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.propertyanimation.R;
import com.example.propertyanimation.chat.adapter.ContactsAdapter;
import java.util.List;

/**
 * Created by huang on 2017/2/25.
 */

public class SlideBar extends View {
    private String[] letters = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private int letterX;
    private int letterHeight;
    private final Paint paint;
    private ViewGroup viewGroup;
    private TextView textView;
    private ListView listView;

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.parseColor("#8c8c8c"));
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 计算字母的x坐标，高度
        letterX = getMeasuredWidth()/2;
        letterHeight = getMeasuredHeight()/letters.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < letters.length; i++) {
            canvas.drawText(letters[i],letterX,letterHeight*(i+1),paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 找中间的字母控件
        if(viewGroup==null){
            viewGroup = (ViewGroup) getParent();
            textView = (TextView) viewGroup.findViewById(R.id.tv_center);
            listView = (ListView) viewGroup.findViewById(R.id.lv_contacts);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setBackgroundResource(R.drawable.slidebar_bg);
                // 根据手指触摸的y坐标，计算手指触摸字母的索引
                int index = (int) (event.getY() / letterHeight);
                textView.setVisibility(VISIBLE);
                textView.setText(letters[index]);

                // 根据当前点击的字母，移动ListView的位置
                ContactsAdapter contactsAdapter = (ContactsAdapter) listView.getAdapter();
                List<String> data = contactsAdapter.getData();
                // 根据当前点击的字母，获取Listview中对应数据的位置
                for (int i = 0; i < data.size(); i++) {
                    if(data.get(i).substring(0,1).toUpperCase().equals(letters[index])){
                        listView.setSelection(i);
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                textView.setVisibility(GONE);
                setBackgroundColor(Color.TRANSPARENT);
                break;
            default:
                break;
        }
        return true;
    }
}
