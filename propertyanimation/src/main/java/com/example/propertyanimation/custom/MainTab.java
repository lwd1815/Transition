package com.example.propertyanimation.custom;

import com.example.propertyanimation.R;
import com.example.propertyanimation.fragment.KotlinFragment;
import com.example.propertyanimation.fragment.MeFragment;
import com.example.propertyanimation.fragment.TopicFragment;
import com.example.propertyanimation.fragment.UiFragment;
import com.example.propertyanimation.fragment.VideoFragment;

/**
 * 创建者     李文东
 * 创建时间   2017/8/22 14:34
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public enum MainTab {
  UI(0, R.string.main_tab_name_ui, R.drawable.tab_icon_ui, UiFragment.class),
  VIDEO(0, R.string.main_tab_name_video, R.drawable.tab_icon_wechat, VideoFragment.class),
  KOTLIN(0, R.string.main_tab_name_kotlin, R.drawable.tab_icon_kotlin, KotlinFragment.class),
  TOPIC(0, R.string.main_tab_name_other, R.drawable.tab_icon_topic, TopicFragment.class),
  ME(0, R.string.main_tab_name_me, R.drawable.tab_icon_me, MeFragment.class);
  private int id;
  private int resName;
  private int resIcon;
  private Class<?> clz;
  private MainTab(int id, int resName, int resIcon, Class<?> clz) {
    this.id = id;
    this.resName = resName;
    this.resIcon = resIcon;
    this.clz = clz;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getResName() {
    return resName;
  }
  public void setResName(int resName) {
    this.resName = resName;
  }
  public int getResIcon() {
    return resIcon;
  }
  public void setResIcon(int resIcon) {
    this.resIcon = resIcon;
  }
  public Class<?> getClz() {
    return clz;
  }
  public void setClz(Class<?> clz) {
    this.clz = clz;
  }

}
