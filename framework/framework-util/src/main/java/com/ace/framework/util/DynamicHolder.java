package com.ace.framework.util;

public class DynamicHolder
{
  private static final ThreadLocal<String> contextHolder = new ThreadLocal();

  public static void setDBType(String dbType) {
    contextHolder.set(dbType);
  }

  public static String getDBType() {
    return (String)contextHolder.get();
  }

  public static void clearDBType() {
    contextHolder.remove();
  }
}