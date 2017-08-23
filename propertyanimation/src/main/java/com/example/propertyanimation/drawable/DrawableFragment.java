package com.example.propertyanimation.drawable;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.example.propertyanimation.R;
import com.example.propertyanimation.adapter.DrawableLayoutAdapter;
import com.example.propertyanimation.base.BaseFragment;

/**
 * 创建者     李文东
 * 创建时间   2017/8/23 9:28
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class DrawableFragment extends BaseFragment implements View.OnClickListener {
  /**
   * Remember the position of the selected item.
   */
  private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
  @InjectView(R.id.rv) RecyclerView rv;

  /**
   * A pointer to the current callbacks instance (the Activity).
   */
  private NavigationDrawerCallbacks mCallbacks;

  /**
   * Helper component that ties the action bar to the navigation drawer.
   */
  private DrawerLayout mDrawerLayout;
  private View mDrawerListView;
  private View mFragmentContainerView;

  private int mCurrentSelectedPosition = 0;
  private boolean mFromSavedInstanceState;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState != null) {
      mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
      mFromSavedInstanceState = true;
    }

    selectItem(mCurrentSelectedPosition);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mDrawerListView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    mDrawerListView.setOnClickListener(this);
    ButterKnife.inject(this, mDrawerListView);
    initView(mDrawerListView);
    initData();
    return mDrawerListView;
  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
     initRv();
  }
  private void initRv() {
    LinearLayoutManager line = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
    rv.setLayoutManager(line);
    DrawableLayoutAdapter adapter = new DrawableLayoutAdapter();
    rv.setAdapter(adapter);
  }
  @Override public void onClick(View v) {
    int id = v.getId();
    switch (id) {
      default:
        break;
    }
    mDrawerLayout.postDelayed(new Runnable() {

      @Override public void run() {
        mDrawerLayout.closeDrawers();
      }
    }, 800);
  }

  public void initView(View view) {

    //mMenu_item_rss.setOnClickListener(this);
    //mMenu_item_opensoft.setOnClickListener(this);
    //mMenu_item_blog.setOnClickListener(this);
    //mMenu_item_quests.setOnClickListener(this);
    //
    //mMenu_item_setting.setOnClickListener(this);
    //mMenu_item_exit.setOnClickListener(this);
    //
    //mMenu_item_gitapp.setOnClickListener(this);
  }

  public void initData() {
  }

  public boolean isDrawerOpen() {
    return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
  }

  /**
   * Users of this fragment must call this method to set up the navigation
   * drawer interactions.
   *
   * @param fragmentId The android:id of this fragment in its activity's layout.
   * @param drawerLayout The DrawerLayout containing this fragment's UI.
   */
  public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
    mFragmentContainerView = getActivity().findViewById(fragmentId);
    mDrawerLayout = drawerLayout;

    // set a custom shadow that overlays the main content when the drawer
    // opens
    // 给DrawerLayout设置阴影
    mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
        //		mDrawerLayout.setDrawerShadow(R.drawable.ic_launcher,
        GravityCompat.START);

    //		// 设置按钮监听, 点击之后, 或者拖拽过程中返回按钮可以实时更新其动画位置.
    ActionBarDrawerToggle mDrawerToggle =
        new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    mDrawerLayout.setDrawerListener(mDrawerToggle);
    mDrawerToggle.syncState();
  }

  public void openDrawerMenu() {
    mDrawerLayout.openDrawer(mFragmentContainerView);
  }

  private void selectItem(int position) {
    mCurrentSelectedPosition = position;
    if (mDrawerLayout != null) {
      mDrawerLayout.closeDrawer(mFragmentContainerView);
    }
    if (mCallbacks != null) {
      mCallbacks.onNavigationDrawerItemSelected(position);
    }
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mCallbacks = (NavigationDrawerCallbacks) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    mCallbacks = null;
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    //		mDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    //		if (mDrawerToggle.onOptionsItemSelected(item)) {
    //			return true;
    //		}

    return super.onOptionsItemSelected(item);
  }

  private ActionBar getActionBar() {
    return ((ActionBarActivity) getActivity()).getSupportActionBar();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.reset(this);
  }

  public static interface NavigationDrawerCallbacks {
    void onNavigationDrawerItemSelected(int position);
  }
}
