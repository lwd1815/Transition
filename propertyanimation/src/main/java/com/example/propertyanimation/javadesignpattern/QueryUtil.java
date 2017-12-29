package com.example.propertyanimation.javadesignpattern;

import com.example.propertyanimation.javadesignpattern.signpattern.Strategy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 创建者     李文东
 * 创建时间   2017/12/29 11:52
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class QueryUtil {
  public void findUserInfo(String[] usernames,Strategy strategy) throws Exception{
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123456");
    Statement stat=conn.createStatement();
    //StringBuilder sql=new StringBuilder("select*from user_info where");
    //if (strategy==1) {
    //  for (String user : usernames) {
    //    sql.append("username='");
    //    sql.append(user);
    //    sql.append("'or");
    //  }
    //  sql.delete(sql.length() - "or".length(), sql.length());
    //}else if (strategy==2){
    //  for (String user:usernames) {
    //    boolean needor = false;
    //    if (needor) {
    //      sql.append("or");
    //    }
    //    sql.append("username='");
    //    sql.append(user);
    //    sql.append("'");
    //    needor = true;
    //  }
    //}
    String sql=strategy.getSQL(usernames);
    System.out.println(sql);
    ResultSet resultSet=stat.executeQuery(sql.toString());
    while (resultSet.next()){
      //处理从数据库读出来的数据
    }
    //后面应该将读到的数据组装成对象返回
  }
}
