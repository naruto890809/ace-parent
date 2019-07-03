package com.ace.framework.util;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbSetUpUtil {
	public static int DBTYPE = 1;

	static {
		try {
			Properties properties = new Properties();
			properties.load(DbSetUpUtil.class.getResourceAsStream("/spring/config.properties"));
			
			String driveName = StringUtil.lcase(StringUtil.cStr(properties.getProperty("driverClassName")));
			
			if (driveName.contains("oracle"))
				DBTYPE = 0;
			else
				DBTYPE = 1;
		} catch (Exception localException) {
		}
	}

	public static String replaceSql(String sql) {
		if (DBTYPE == 1) {
			sql = sql.trim();

			if (sql.startsWith("call")) {
				sql = "{" + sql + "}";
			}
			if (sql.startsWith("CALL")) {
				sql = "{" + sql + "}";
			}

			Pattern p = Pattern.compile("SYSDATE", 2);
			Matcher m = p.matcher(sql);
			while (m.find()) {
				String temp = m.group();
				String seqName = "NOW()";
				sql = sql.replace(temp, seqName);
			}

			p = Pattern.compile("NVL\\s*\\(", 2);
			m = p.matcher(sql);
			while (m.find()) {
				String temp = m.group();
				String seqName = "COALESCE(";
				sql = sql.replace(temp, seqName);
			}

			p = Pattern.compile("SEQ[^\\.]*\\.NEXTVAL", 2);
			m = p.matcher(sql);
			while (m.find()) {
				String temp = m.group();
				String seqName = "NEXTVAL('"
						+ temp.substring(0, temp.indexOf(".")) + "')";
				sql = sql.replace(temp, seqName);
			}

			p = Pattern.compile("TO_DATE\\(", 2);
			m = p.matcher(sql);
			while (m.find()) {
				String temp = m.group();
				String seqName = "TO_TIMESTAMP(";
				sql = sql.replace(temp, seqName);
			}

			sql = sql.replaceAll(
					"\\s+(table\\(|TABLE\\({1})\\s*([^\\)]+)\\s*\\){1}", " $2");
		}
		return sql;
	}

	public static String replaceRowNum(String sql, int brow, int erow) {
		if (DBTYPE == 1)
			sql = "SELECT A.* FROM  (" + sql + ") A LIMIT " + (erow - brow + 1)
					+ " OFFSET " + (brow - 1);
		else {
			sql = "SELECT * FROM (SELECT A.*, rownum rn FROM  (" + sql
					+ ") A where  rownum <= " + erow + ") B WHERE rn >=" + brow;
		}

		return sql;
	}

	public static String getLimitSql(String sql, int fetchcount) {
		if (DBTYPE == 1)
			sql = "SELECT A.* FROM  (" + sql + ") A LIMIT " + fetchcount;
		else {
			sql = "SELECT A.* FROM  (" + sql + ") A where  rownum <= "
					+ fetchcount;
		}
		return sql;
	}
}