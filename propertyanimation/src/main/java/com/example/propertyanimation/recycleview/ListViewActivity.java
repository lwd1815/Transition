package com.example.propertyanimation.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;
import com.example.propertyanimation.R;
import com.example.propertyanimation.recycleview.divideItemDeration.DividerListItemDecoration;
import com.example.propertyanimation.recycleview.model.DataManager;
import com.example.propertyanimation.recycleview.view.OnRecycleviewrItemClickListener;
import com.example.propertyanimation.recycleview.view.RecyclerItemTouchHelperCallback;
import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

  RecyclerView mRecycleView;
  List<String> mstringList;
  RvAdapter mRvAdapter;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_view);
    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mRecycleView= (RecyclerView) findViewById(R.id.recy);
    initRecy();
  }

  private void initRecy() {
    if (mstringList==null){
      mstringList=new ArrayList<>();
    }

    mstringList.addAll(DataManager.getData(15-mstringList.size()));
    mRvAdapter =new RvAdapter(R.layout.item_listview,mstringList);

    LinearLayoutManager line = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
    mRecycleView.setLayoutManager(line);
    mRecycleView.addItemDecoration(new DividerListItemDecoration(this,LinearLayoutManager.VERTICAL));
    mRecycleView.setHasFixedSize(true);

    RecyclerItemTouchHelperCallback itemTouchhelperCallback = new RecyclerItemTouchHelperCallback(
        mRvAdapter);
    final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchhelperCallback);
    itemTouchHelper.attachToRecyclerView(mRecycleView);

    mRecycleView.addOnItemTouchListener(new OnRecycleviewrItemClickListener(mRecycleView) {
      @Override public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        RvAdapter.ViewHolder viewHolder1 = (RvAdapter.ViewHolder) viewHolder;
        String tvs = viewHolder1.mtextview.getText().toString();
        Toast.makeText(ListViewActivity.this, "逗逗~" + tvs, Toast.LENGTH_SHORT).show();
      }

      @Override public void onLongClick(RecyclerView.ViewHolder viewHolder) {
        Toast.makeText(ListViewActivity.this, "" + "讨厌，不要老是摸人家啦...", Toast.LENGTH_SHORT).show();
      }
    });

    mRecycleView.setAdapter(mRvAdapter);

  }

  //@Override public boolean onOptionsItemSelected(MenuItem item) {
  //  if (item.getItemId()==android.R.id.home){
  //    finish();
  //  }
  //  return super.onOptionsItemSelected(item);
  //}
}
