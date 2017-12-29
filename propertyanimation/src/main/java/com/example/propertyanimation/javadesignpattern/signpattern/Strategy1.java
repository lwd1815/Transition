package com.example.propertyanimation.javadesignpattern.signpattern;

/**
 * 创建者     李文东
 * 创建时间   2017/12/29 12:12
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class Strategy1 implements Strategy {
  @Override public String getSQL(String[] usernames) {
    StringBuilder sql=new StringBuilder("select*from user_info where");
    for (String user : usernames) {
      sql.append("username='");
      sql.append(user);
      sql.append("'or");
    }
    sql.delete(sql.length() - "or".length(), sql.length());
    return sql.toString();
  }
}
