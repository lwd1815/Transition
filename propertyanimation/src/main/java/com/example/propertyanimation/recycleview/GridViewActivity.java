package com.example.propertyanimation.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;
import com.example.propertyanimation.R;
import com.example.propertyanimation.recycleview.divideItemDeration.DividerGridItemDecoration;
import com.example.propertyanimation.recycleview.model.DataManager;
import com.example.propertyanimation.recycleview.view.OnRecycleviewrItemClickListener;
import com.example.propertyanimation.recycleview.view.RecyclerItemTouchHelperCallback;
import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {
  RecyclerView mRecyclerView;
  List<String> mStringList;
  RvAdapter mRecyAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_view);
    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mRecyclerView = (RecyclerView) findViewById(R.id.recy);
    initRecy();
  }

  private void initRecy() {
    if (mStringList == null) {
      mStringList = new ArrayList<>();
    }
    mStringList.addAll(DataManager.getData(20 - mStringList.size()));
    mRecyAdapter = new RvAdapter(R.layout.item_gridview, mStringList, true);

    mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
    mRecyclerView.setHasFixedSize(true);

    RecyclerItemTouchHelperCallback itemTouchHelperCallback = new RecyclerItemTouchHelperCallback(mRecyAdapter, false, true);
    final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
    itemTouchHelper.attachToRecyclerView(mRecyclerView);

    mRecyclerView.addOnItemTouchListener(new OnRecycleviewrItemClickListener(mRecyclerView) {
      @Override
      public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        RvAdapter.ViewHolder viewHolder1 = (RvAdapter.ViewHolder) viewHolder;
        String tvString = viewHolder1.mtextview.getText().toString();
        Toast.makeText(GridViewActivity.this, "逗逗~" + tvString, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onLongClick(RecyclerView.ViewHolder viewHolder) {
        Toast.makeText(GridViewActivity.this, "" + "讨厌，不要老是摸人家啦...", Toast.LENGTH_SHORT).show();
        if (viewHolder.getLayoutPosition() != 0) {
          itemTouchHelper.startDrag(viewHolder);
        }
      }
    });
    mRecyclerView.setAdapter(mRecyAdapter);
  }

  //@Override
  //public boolean onOptionsItemSelected(MenuItem item) {
  //  if (item.getItemId() == android.R.id.home) {
  //    finish();
  //  }
  //  return super.onOptionsItemSelected(item);
  //}
}
