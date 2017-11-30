package com.example.propertyanimation.mvp.bean;

/**
 * 创建者     李文东
 * 创建时间   2017/11/30 18:51
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class MyIp {
  String City;
  String Country;
  String Province;

  public String getCity() {
    return City;
  }

  public void setCity(String city) {
    City = city;
  }

  public String getCountry() {
    return Country;
  }

  public void setCountry(String country) {
    Country = country;
  }

  public String getProvince() {
    return Province;
  }

  public void setProvince(String province) {
    Province = province;
  }

  @Override public String toString() {

    return this.getCountry() + "\n" +
        this.getProvince() + "\n" +
        this.getCity() + "\n";
  }
}
