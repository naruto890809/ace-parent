package com.ace.framework.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource implements ApplicationContextAware
{
  public static Map<Object, Object> _targetDataSources;
  private ApplicationContext ctx;

  protected Object determineCurrentLookupKey()
  {
    String dataSourceName = DynamicHolder.getDBType();
    if (dataSourceName == null) {
      dataSourceName = "dataSource";
    } else {
      selectDataSource(dataSourceName);
      if (dataSourceName.equals("0"))
        dataSourceName = "dataSource";
    }
    StringUtil.pr(dataSourceName);
    return dataSourceName;
  }

  public void setTargetDs(Map<Object, Object> targetDataSources) {
    _targetDataSources = targetDataSources;
    super.setTargetDataSources(_targetDataSources);
    super.afterPropertiesSet();
  }

  public void addTargetDataSource(String key, BasicDataSource dataSource) {
    _targetDataSources.put(key, dataSource);
    setTargetDs(_targetDataSources);
  }

  public void setApplicationContext(ApplicationContext ctx)
    throws BeansException
  {
    this.ctx = ctx;
  }

  public BasicDataSource createDataSource(String driverClassName, String url, String username, String password)
  {
    BasicDataSource dataSource = new BasicDataSource();
    BasicDataSource parent = (BasicDataSource)this.ctx.getBean("parentDataSource");
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setDriverClassName(driverClassName);
    dataSource.setDefaultAutoCommit(parent.getDefaultAutoCommit());
    dataSource.setDefaultReadOnly(parent.getDefaultReadOnly());
    dataSource.setMaxActive(parent.getMaxActive());
    dataSource.setMaxIdle(parent.getMaxIdle());
    dataSource.setMaxWait(parent.getMaxWait());
    return dataSource;
  }

  public void selectDataSource(String serverId)
  {
    Object sid = DynamicHolder.getDBType();
    if ("0".equals(serverId)) {
      DynamicHolder.setDBType("0");
      return;
    }
    if (_targetDataSources == null) {
      _targetDataSources = new HashMap();
    }
    Object obj = _targetDataSources.get(serverId);
    if ((obj != null) && (sid.equals(serverId))) {
      return;
    }
    BasicDataSource dataSource = getDataSource(serverId);
    if (dataSource != null)
      setDataSource(serverId, dataSource);
  }

  public BasicDataSource getDataSource(String serverId)
  {
    selectDataSource("0");
    determineCurrentLookupKey();
    Connection conn = null;
    HashMap map = null;
    try {
      conn = getConnection();
      String sql = "select DBDRIVER,DBURL,DBUSER,DBPASS from tdy_ts_mk7500 a,ts_mk_info7500 b where a.tdy_zid=b.ts_mk_id and b.ts_mk_bh = ?";

      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, serverId);
      ResultSet rs = ps.executeQuery();
      map = new HashMap();
      if (rs.next()) {
        map.put("DBDRIVER", rs.getString("DBDRIVER"));
        map.put("DBURL", rs.getString("DBURL"));
        map.put("DBUSER", rs.getString("DBUSER"));
        map.put("DBPASS", rs.getString("DBPASS"));
      }
      rs.close();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (map != null) {
      String driverClassName = map.get("DBDRIVER").toString();
      String url = map.get("DBURL").toString();
      String userName = map.get("DBUSER").toString();
      String password = map.get("DBPASS").toString();
      BasicDataSource dataSource = createDataSource(driverClassName, 
        url, userName, password);
      return dataSource;
    }
    map.get("DBDRIVER").toString();

    return null;
  }

  public void setDataSource(String serverId, BasicDataSource dataSource)
  {
    addTargetDataSource(serverId, dataSource);
    DynamicHolder.setDBType(serverId);
  }
}