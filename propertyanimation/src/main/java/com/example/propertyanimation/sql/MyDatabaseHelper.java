package com.example.propertyanimation.sql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建者     李文东
 * 创建时间   2018/1/2 12:15
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class MyDatabaseHelper extends SQLiteOpenHelper{
  public static final String CREATE_TABLE="create table Book(name text,pages integer)";
  public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
      int version) {
    super(context, name, factory, version);
  }

  public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
      int version, DatabaseErrorHandler errorHandler) {
    super(context, name, factory, version, errorHandler);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

  }
}
