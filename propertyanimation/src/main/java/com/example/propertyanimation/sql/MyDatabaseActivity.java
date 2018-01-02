package com.example.propertyanimation.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.propertyanimation.R;

public class MyDatabaseActivity extends AppCompatActivity {
  private SQLiteDatabase db;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_database);
    MyDatabaseHelper dbHlper=new MyDatabaseHelper(this,"demo.db",null,1);
    db=dbHlper.getWritableDatabase();
    Button addData = (Button) findViewById(R.id.add_data);
    Button queryData = (Button) findViewById(R.id.query_data);
    addData.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        ContentValues values=new ContentValues();
        values.put("name","达芬奇密码");
        values.put("pages",566);
        db.insert("Book",null,values);
      }
    });

    queryData.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Cursor cursor =db.query("Book",null,null,null,null,null,null);
        if (cursor!=null){
          while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("name"));
            int pages=cursor.getInt(cursor.getColumnIndex("pages"));
            Log.d("TAG", "book name is " + name);
            Log.d("TAG", "book pages is " + pages);
          }
        }
        cursor.close();
      }
    });
  }
}
