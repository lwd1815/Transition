package com.example.propertyanimation.javadesignpattern;

import com.example.propertyanimation.javadesignpattern.signpattern.Strategy1;
import com.example.propertyanimation.javadesignpattern.signpattern.Strategy2;

/**
 * 创建者     李文东
 * 创建时间   2017/12/29 12:00
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class Test {
  public static void main(String[]args) throws Exception{
    QueryUtil queryUtil=new QueryUtil();
    //queryUtil.findUserInfo(new String[]{"tom","jim","anna"},2);
    queryUtil.findUserInfo(new String[]{"Tom","Jim","Anna"},new Strategy1());
    queryUtil.findUserInfo(new String[]{"Tom","Jim","Anna"},new Strategy2());
  }
}
