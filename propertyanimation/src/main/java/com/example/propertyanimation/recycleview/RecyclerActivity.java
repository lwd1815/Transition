package com.example.propertyanimation.recycleview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.propertyanimation.R;

public class RecyclerActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recycler);
  }

  public void showListView(View view){

    startActivity(new Intent(this,ListViewActivity.class));
  }

  public void showGridView(View view){
    startActivity(new Intent(this,GridViewActivity.class));
  }

}
