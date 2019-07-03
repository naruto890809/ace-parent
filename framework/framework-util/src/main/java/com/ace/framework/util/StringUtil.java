package com.ace.framework.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import org.apache.commons.lang3.StringUtils;
//import org.boris.expr.Expr;
//import org.boris.expr.ExprEvaluatable;
//import org.boris.expr.parser.ExprParser;
//import org.boris.expr.util.Exprs;
//import org.boris.expr.util.SimpleEvaluationContext;
//import org.htmlparser.Node;
//import org.htmlparser.lexer.Lexer;
//import org.htmlparser.nodes.TextNode;
//import org.htmlparser.util.ParserException;

public class StringUtil
{
  public static int _applyMode = 0;
  public static int _applyGzip = 0;
  public static int _skinStyle = 0;
  private static String delegatePath;
  private static String startPath;
  private static String serverLinkPath;
  private static String staticResourceURL;
  public static boolean _isSkin = false;
  private static Long[] docMk = null;

  static
  {
    try
    {
      Properties sp = System.getProperties();
      Properties properties = new Properties();
      properties.load(StringUtil.class
        .getResourceAsStream("/struts/constants.properties"));
      Enumeration name = properties
        .propertyNames();
      while (name.hasMoreElements()) {
        String property = (String)name.nextElement();
        if (property == null) break; if ("".equals(property)) {
          break;
        }
        String value = properties.getProperty(property);
        property = property.replaceAll("(?:.+)_(\\w+)", "$1");
        sp.setProperty(property.toLowerCase(), value);
      }

      properties.load(StringUtil.class
        .getResourceAsStream("/spring/config.properties"));
      Enumeration name2 = properties
        .propertyNames();
      while (name2.hasMoreElements()) {
        String property = (String)name2.nextElement();
        if (property == null) break; if ("".equals(property)) {
          break;
        }
        String value = properties.getProperty(property);
        property = property.replaceAll("(?:.+)_(\\w+)", "$1");
        sp.setProperty(property.toLowerCase(), value);
      }
      delegatePath = cStr(sp.getProperty("delegatepath"));
      if (equ(delegatePath, "/")) {
        delegatePath = "";
      }
      startPath = cStr(sp.getProperty("startpath"));
      if (equ(startPath, "/")) {
        startPath = "";
      }
      if (equ(startPath, "/")) {
        startPath = "";
      }
      serverLinkPath = cStr(sp.getProperty("serverlinkpath"));
      delegatePath += startPath;
      _skinStyle = cInt(sp.getProperty("skinstyle"));
      _isSkin = !equ("0", Integer.valueOf(_skinStyle));
      staticResourceURL = cStr(sp
        .getProperty("startpath"));
      if (equ(staticResourceURL, "/")) {
        staticResourceURL = "";
      }

      String _applyModeStr = sp.getProperty("applymode");
      if (_applyModeStr == null)
        _applyMode = 0;
      else {
        try {
          _applyMode = cInt(_applyModeStr);
        } catch (Exception ae) {
          _applyMode = 0;
        }

      }

      String _applyGzipStr = sp.getProperty("applygzip");
      if (_applyGzipStr == null)
        _applyGzip = 0;
      else
        try {
          _applyGzip = cInt(_applyGzipStr);
        } catch (Exception ae) {
          _applyGzip = 0;
        }
    }
    catch (Exception localException1)
    {
    }
  }

  public static Long[] getDocMk()
  {
    if (System.getProperty("docmk") != null) {
      String[] array = System.getProperty("docmk").split(",");
      docMk = new Long[array.length];
      for (int i = 0; i < array.length; ++i) {
        if (!isNull(array[i])) {
          docMk[i] = new Long(array[i].trim());
        }
      }
    }

    return docMk;
  }

  public static void pr(String strSql)
  {
  }

  public static boolean isDocMk(Long mkid)
  {
    getDocMk();
    if (!isNull(docMk)) {
      for (int i = 0; i < docMk.length; ++i) {
        if (equ(mkid, docMk[i]))
          return true;
      }
    }
    return false;
  }

  public static String yearMd()
  {
    String now = dateString(new java.util.Date());
    String year = year(now);
    year = year.substring(year.length() - 2, year.length());
    String currentTimeMillis = cStr(Long.valueOf(System.currentTimeMillis()));
    currentTimeMillis = currentTimeMillis.substring(
      currentTimeMillis.length() - 3, currentTimeMillis.length());
    return year + month(now) + day(now) + 
      minute(now) + second(now) + 
      currentTimeMillis;
  }

  public static String getIp(HttpServletRequest request)
  {
    String ip = request.getHeader("x-forwarded-for");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("X-Forwarded-For");
      if ((ip != null) && (ip.length() != 0) && 
        (!"unknown".equalsIgnoreCase(ip)))
      {
        int index = ip.indexOf(',');
        if (index != -1) {
          ip = ip.substring(0, index);
        }
      }
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

//  public static String tstr(String str)
//  {
//    str = nul(str);
//    str = str.replace('<', 65308);
//    str = str.replace('>', 65310);
//    str = str.replace("'", "''");
//    str = str.replace('"', 65282);
//    str = str.replace('%', 65285);
//    str = str.replace("--", "－－");
//
//    return str;
//  }

  public static String iif(boolean Con1, String Rev1, String Rev2)
  {
    return (Con1) ? Rev1 : Rev2;
  }

  public static boolean isNull(Object obj)
  {
    return (obj == null) || ("".equals(obj));
  }

  public static String nul(Object obj)
  {
    return (isNull(obj)) ? "" : String.valueOf(obj);
  }

  public static boolean equ(Object obj1, Object obj2)
  {
    return nul(obj1).equals(nul(obj2));
  }

  public static boolean neq(Object obj1, Object obj2)
  {
    return !nul(obj1).equals(nul(obj2));
  }

  public static boolean notEmpty(Object obj)
  {
    String str = nul(obj);
    return (!equ(str, "")) && (!equ(str, "undefined"));
  }

  public static boolean notZero(Object obj)
  {
    String str = nul(obj);
    return (!equ(str, Integer.valueOf(0))) && (!equ(str, "")) && (!equ(str, "undefined"));
  }

  public static String lef(String str)
  {
    int len = str.length();
    return (len > 0) ? str.substring(0, len - 1) : "";
  }

  public static boolean equr(Object obj1, Object obj2)
  {
    String[] str = nul(obj2).split("\\,");
    for (int i = 0; i < str.length; ++i) {
      if (equ(obj1, str[i]))
        return true;
    }
    return false;
  }

  public static String pick(Object obj1, Object obj2)
  {
    if (equ(obj1, ""))
      return nul(obj2);
    return nul(obj1);
  }

  public static String ubbTstr(Object obj)
  {
    return nul(obj).replace("'", "''");
  }

  public static String urlStr(String str)
  {
    try
    {
      return URLEncoder.encode(str, "UTF-8"); } catch (Exception ex) {
    }
    return str;
  }

  public static String deUrlStr(String str)
  {
    try
    {
      return URLDecoder.decode(str, "UTF-8"); } catch (Exception ex) {
    }
    return str;
  }

  public static int cInt(String str)
  {
    if (equ(str, ""))
      return 0;
    try
    {
      return Integer.parseInt(str.trim()); } catch (Exception e) {
    }
    return 0;
  }

  public static int cInt(Object str)
  {
    return Integer.parseInt(nul(str).trim());
  }

  public static String getWeekOfDate(java.util.Date dt)
  {
    String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
    Calendar cal = Calendar.getInstance();
    cal.setTime(dt);
    int w = cal.get(7) - 1;
    if (w < 0) {
      w = 0;
    }
    return weekDays[w];
  }

  public static boolean isDate(String sDate)
  {
    if (sDate.indexOf(" ") + sDate.indexOf(":") == -2)
      sDate = sDate + " 0:0:0";
    SimpleDateFormat df = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");
    try {
      df.setLenient(false);
      df.parse(sDate);
      return true;
    } catch (ParseException ex) {
      df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      try {
        df.setLenient(false);
        df.parse(sDate);
        return true; } catch (ParseException ey) {
      }
    }
    return false;
  }

  public static java.util.Date getDate()
  {
    return new java.util.Date();
  }

  public static java.util.Date getDate(String sDate)
  {
    if (sDate.indexOf(" ") + sDate.indexOf(":") == -2)
      sDate = sDate + " 0:0:0";
    SimpleDateFormat df = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");
    try {
      df.setLenient(false);
      return df.parse(sDate);
    } catch (ParseException ex) {
      df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      try {
        df.setLenient(false);
        return df.parse(sDate); } catch (ParseException ey) {
      }
    }
    return new java.util.Date();
  }

  public static String dateString(Object dat)
  {
    if (dat == null)
      return "";
    SimpleDateFormat formatter = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");
    String dateStr = formatter.format(dat);
    return dateStr;
  }

  public static String shortDateString(Object dat)
  {
    if (dat == null)
      return "";
    String strFormat = "";
    if (instr(dat.toString(), ":") > 0)
      strFormat = "yyyy-MM-dd HH:mm:ss";
    else {
      strFormat = "yyyy-MM-dd";
    }
    SimpleDateFormat formatter = new SimpleDateFormat(
      strFormat);
    String dateStr = formatter.format(dat);
    return dateStr;
  }

  public static String cDate(String dat)
  {
    return dateString(getDate(dat));
  }

  public static String date()
  {
    return new java.sql.Date(System.currentTimeMillis()).toString();
  }

  public static String now()
  {
    return dateString(getDate());
  }

  public static String year(String dat)
  {
    if (isDate(dat))
      return dateString(getDate(dat)).substring(0, 4);
    return null;
  }

  public static String month(String dat)
  {
    if (isDate(dat))
      return dateString(getDate(dat)).substring(5, 7);
    return null;
  }

  public static String day(String dat)
  {
    if (isDate(dat))
      return dateString(getDate(dat)).substring(8, 10);
    return null;
  }

  public static String hour(String dat)
  {
    if (isDate(dat))
      return dateString(getDate(dat)).substring(11, 13);
    return null;
  }

  public static String minute(String dat)
  {
    if (isDate(dat))
      return dateString(getDate(dat)).substring(14, 16);
    return null;
  }

  public static String second(String dat)
  {
    if (isDate(dat))
      return dateString(getDate(dat)).substring(17, 19);
    return null;
  }

  public static int getLastDateOfMonth(String dat)
  {
    int Y = cInt(year(dat));
    int[] A = { 31, (((Y % 4 == 0) && (Y % 100 != 0)) || (Y % 400 == 0)) ? 29 : 28, 
      31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    return A[(cInt(month(dat)) - 1)];
  }
//
//  public static long dateDiff(String interval, String date1, String date2)
//  {
//    if ((!isDate(date1)) || (!isDate(date2)))
//      return 0L;
//    long inter;
//    long inter;
//    if (interval == "s") {
//      inter = 1000L;
//    }
//    else
//    {
//      long inter;
//      if (interval == "n") {
//        inter = 60000L;
//      }
//      else
//      {
//        long inter;
//        if (interval == "h") {
//          inter = 3600000L;
//        }
//        else
//        {
//          long inter;
//          if (interval == "d")
//            inter = 86400000L;
//          else
//            inter = 1L; 
//        }
//      }
//    }
//    long day = (getDate(date2).getTime() - getDate(date1).getTime()) / 
//      inter;
//    return day;
//  }

//  public static String dateAdd(String interval, int num, String date)
//  {
//    Calendar rightNow = Calendar.getInstance();
//    rightNow.setTime(getDate(date));
//    int inter;
//    if (interval == "s") {
//      inter = 13;
//    }
//    else
//    {
//      int inter;
//      if (interval == "n") {
//        inter = 12;
//      }
//      else
//      {
//        int inter;
//        if (interval == "h") {
//          inter = 10;
//        }
//        else
//        {
//          int inter;
//          if (interval == "d") {
//            inter = 5;
//          }
//          else
//          {
//            int inter;
//            if (interval == "m") {
//              inter = 2;
//            }
//            else
//            {
//              int inter;
//              if (interval == "y")
//                inter = 1;
//              else
//                return null;
//            }
//          }
//        }
//      }
//    }
//    int inter;
//    rightNow.add(inter, num);
//    java.util.Date dt = rightNow.getTime();
//    return dateString(dt);
//  }

  public static int instr(String str1, String str2)
  {
    if (isNull(str1))
      return 0;
    return str1.indexOf(str2) + 1;
  }

  public static int instrRev(String str1, String str2)
  {
    if (isNull(str1))
      return 0;
    return str1.lastIndexOf(str2) + 1;
  }

  public static boolean where(String str1, String str2)
  {
    return ("," + str1 + ",").indexOf("," + str2 + ",") > -1;
  }

  public static int len(String str)
  {
    if (isNull(str))
      return 0;
    return str.length();
  }

  public static String lcase(String str)
  {
    if (isNull(str))
      return str;
    return str.toLowerCase();
  }

  public static String ucase(String str)
  {
    if (isNull(str))
      return str;
    return str.toUpperCase();
  }

  public static String replace(String str1, String str2, String str3)
  {
    if (isNull(str1))
      return str1;
    return str1.replace(cStr(str2), cStr(str3));
  }

  public static String[] split(String str1, String str2)
  {
    str1 = str1 + " ";
    if (equ(str2, ","))
      str2 = "\\,";
    if (equ(str2, "|"))
      str2 = "\\|";
    if (equ(str2, "("))
      str2 = "\\(";
    if (equ(str2, "{"))
      str2 = "\\{";
    if (equ(str2, "$"))
      str2 = "\\$";
    if (equ(str2, "@"))
      str2 = "\\@";
    return str1.split(str2);
  }

  public static String join(String[] arr, String str)
  {
    if (arr == null)
      return "";
    StringBuffer buff = new StringBuffer();
    int i = 0; for (int len = arr.length; i < len; ++i) {
      buff.append(String.valueOf(arr[i]));
      if (i < len - 1)
        buff.append(str);
    }
    return buff.toString();
  }

  public static int ubound(String[] arr)
  {
    return arr.length - 1;
  }

  public static String left(String str, int alt)
  {
    if (equ(nul(str), "")) {
      return "";
    }
    return nul(str).substring(0, alt);
  }

  public static String getStartChar(String str)
  {
    return nul(str).replaceAll("^(.).+", "$1");
  }

  public static String delStartChar(String str)
  {
    return nul(str).replaceAll("^(?:.)(.+)$", "$1");
  }

  public static String getEndChar(String str)
  {
    return nul(str).replaceAll("^.+(.)$", "$1");
  }

  public static String delEndChar(String str)
  {
    return nul(str).replaceAll("^(.+).$", "$1");
  }

  public static String[] splitString(String str, int areaLong)
  {
    int ln = str.length();
    String[] sArr = null;
    if (ln % areaLong > 0)
      sArr = new String[ln / areaLong + 1];
    else {
      sArr = new String[ln / areaLong];
    }
    for (int i = 0; i < ln; i += areaLong) {
      if (i >= ln - areaLong)
        sArr[(i / areaLong)] = str.substring(i);
      else {
        sArr[(i / areaLong)] = str.substring(i, i + areaLong);
      }
    }
    return sArr;
  }

  public static void main(String[] arg)
  {
  }

  public static String right(String str, int alt)
  {
    if (equ(nul(str), "")) {
      return "";
    }
    return nul(str).substring(str.length() - alt, str.length());
  }

  public static String trim(Object str)
  {
    return nul(str).trim();
  }

  public static String cStr(Object str)
  {
    return nul(str);
  }

  public static Double cDbl(String str)
  {
    try
    {
      return Double.valueOf(Double.parseDouble(str.trim())); } catch (Exception e) {
    }
    return null;
  }

  public static boolean cBool(String str)
  {
    return neq(nul(str), "");
  }

  public static boolean cBool(int num) {
    return num != 0;
  }

  public static double round(double dbl, int cnt)
  {
    return Math.round(dbl * Math.pow(10.0D, cnt)) / Math.pow(10.0D, cnt);
  }

  public static boolean isNumeric(String str)
  {
    Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]*");
    Matcher isNum = pattern.matcher(str);

    return isNum.matches();
  }

//  public static String chr(int ascCode)
//  {
//    return (char)ascCode;
//  }

  public static String to0(Object str)
  {
    String temp = nul(str);
    if (equ(temp, "")) {
      return "0";
    }
    return temp;
  }

  public static boolean isPad(String sAgent)
  {
    return (instr(sAgent, "iPhone") > 0) || (instr(sAgent, "iPad") > 0) || (
      instr(sAgent, "iPod") > 0);
  }

  public static boolean isMob(String sAgent)
  {
    return (instr(sAgent, "Mobile") > 0) || (instr(sAgent, "android") > 0) || 
      (isPad(sAgent));
  }

  public static String getColValue(Map<String, Object> map, int index)
  {
    int i = 0;
    if (!map.isEmpty()) {
      Iterator it = map.entrySet().iterator();
      while (it.hasNext()) {
        Entry next = (Entry)it.next();
        if (i == index) {
          return nul(next.getValue());
        }
        ++i;
      }
    }
    return "";
  }

  public static String sqlEqu(String str)
  {
    if (equ(str, "")) {
      return " IS NULL ";
    }
    return "='" + nul(str) + "' ";
  }

  public static String sqlNeq(String str)
  {
    if (equ(str, "")) {
      return " IS NOT NULL ";
    }
    return "<>'" + nul(str) + "' ";
  }

  public static String mid(String str, int Start, int Length)
  {
    str = nul(str);
    if ((str == null) || (Start <= 0))
      return null;
    if (str.equals(""))
      return "";
    if (Start + Length - 1 >= str.length()) {
      return str.substring(Start - 1);
    }
    return str.substring(Start - 1, Start + Length - 1);
  }

  public static String join(Map<String, Object> map, String Spliter)
  {
    String Rev = "";
    if (!map.isEmpty()) {
      Iterator it = map.entrySet()
        .iterator();
      while (it.hasNext()) {
        Entry next =
          (Entry)it
          .next();
        Rev = Rev + nul(next.getValue()) + ((it.hasNext()) ? Spliter : "");
      }
    }
    return Rev;
  }

  public static int mod(double dbl1, double dbl2)
  {
    return (int)(dbl1 % dbl2);
  }

  public static int mod(int dbl1, double dbl2) {
    return (int)(dbl1 % dbl2);
  }

  public static int mod(int dbl1, int dbl2) {
    return dbl1 % dbl2;
  }

  public static int mod(double dbl1, int dbl2) {
    return (int)(dbl1 % dbl2);
  }

  public static void cpy(String code)
  {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable trandata = new StringSelection(code);
    clipboard.setContents(trandata, null);
  }

  public static boolean isWindowsOS()
  {
    String os = System.getProperty("os.name");
    return os.startsWith("Windows");
  }

  public static boolean isLinuxOS()
  {
    String os = System.getProperty("os.name");
    return os.startsWith("Linux");
  }

  public static String getSysProperty(String propertyName)
  {
    return System.getProperty(propertyName);
  }

  public static String runLocalCmd(String cmdName, String param)
  {
    StringBuffer sbf = new StringBuffer();
    try {
      ProcessBuilder pb = new ProcessBuilder(new String[] { "dmidecode", "-t 2" });
      Process p = pb.start();
      BufferedReader br = new BufferedReader(
        new InputStreamReader(p.getInputStream()));
      String line = "";
      String EntChar = (isWindowsOS()) ? "\r\n" : "\n";
      while ((line = br.readLine()) != null) {
        sbf.append(line).append(EntChar);
      }
      br.close();
    } catch (IOException localIOException) {
    }
    return sbf.toString();
  }

  public static String[] mapToArray(Map<String, Object> map)
  {
    String[] Rev = null;
    int i = 0;
    if (!map.isEmpty()) {
      Iterator it = map.entrySet()
        .iterator();
      Rev = new String[map.size()];
      while (it.hasNext()) {
        Entry next =
          (Entry)it
          .next();
        Rev[i] = nul(next.getValue());
        ++i;
      }
    }
    return Rev;
  }

  public static String mapToParam(Map<String, Object> map)
  {
    String rtn = "";
    if (!map.isEmpty()) {
      Iterator it = map.entrySet()
        .iterator();
      while (it.hasNext()) {
        Entry next =
          (Entry)it
          .next();
        rtn = rtn + (String)next.getKey() + "=" + next.getValue() + "&";
      }
      if ((rtn.length() > 0) && (rtn.endsWith("&"))) {
        rtn = rtn.substring(0, rtn.length() - 1);
      }
    }
    return rtn;
  }

  public static Map<String, Object> arrayToMap(String[] array)
  {
    Map map = new HashMap();
    for (int i = 0; i < array.length; ++i) {
      map.put(nul(Integer.valueOf(i)), array[i]);
    }
    return map;
  }

  public static String Object2String(Object obj)
  {
    if (obj == null)
      return "";
    return obj.toString();
  }

  public static Long Object2Long(Object obj)
  {
    if (obj == null)
      return null;
    try {
      return Long.decode(obj.toString()); } catch (NumberFormatException e) {
    }
    return null;
  }

  public static Long cLng(Object obj)
  {
    return Object2Long(obj);
  }

  public static void init()
  {
  }

  public static boolean applyModeIs(int i)
  {
    return _applyMode == i;
  }

  public static boolean applyGzipIs(int i)
  {
    return _applyGzip == i;
  }

  public static String vbcrlf()
  {
    return "\r\n";
  }

  public static String getServerAddr(HttpServletRequest request)
  {
    String rurl = cStr(request.getRequestURL());
    String ruri = cStr(request.getRequestURI());
    String strAddr = rurl.substring(0, rurl.length() - ruri.length());
    strAddr = strAddr + getDelegatePath();
    if (neq("", serverLinkPath)) {
      return serverLinkPath;
    }
    return strAddr;
  }

  public static String getDomain(HttpServletRequest request)
  {
    String rurl = cStr(request.getRequestURL());
    String ruri = cStr(request.getRequestURI());
    String dm1 = rurl.substring(0, rurl.length() - ruri.length());
    if (equ(right(dm1, 1), "/")) {
      dm1 = lef(dm1);
    }
    dm1 = dm1.replace("http://", "").replace("https://", "");
    if (dm1.indexOf(":") > -1) {
      dm1 = dm1.split(":")[0];
    }
    return dm1;
  }

  public static String handelUrl(String url)
  {
    if (url == null) {
      return null;
    }
    url = url.trim();
    if ((url.equals("")) || (url.startsWith("http://")) || 
      (url.startsWith("https://"))) {
      return url;
    }
    return "http://" + url.trim();
  }

//  public static String[] splitAndTrim(String str, String sep, String sep2)
//  {
//    if (StringUtils.isBlank(str)) {
//      return null;
//    }
//    if (!StringUtils.isBlank(sep2)) {
//      str = StringUtils.replace(str, sep2, sep);
//    }
//    String[] arr = StringUtils.split(str, sep);
//
//    int i = 0; for (int len = arr.length; i < len; ++i) {
//      arr[i] = arr[i].trim();
//    }
//    return arr;
//  }

//  public static String txt2htm(String txt)
//  {
//    if (StringUtils.isBlank(txt)) {
//      return txt;
//    }
//    StringBuilder sb = new StringBuilder((int)(txt.length() * 1.2D));
//
//    boolean doub = false;
//    for (int i = 0; i < txt.length(); ++i) {
//      char c = txt.charAt(i);
//      if (c == ' ') {
//        if (doub) {
//          sb.append(' ');
//          doub = false;
//        } else {
//          sb.append("&nbsp;");
//          doub = true;
//        }
//      } else {
//        doub = false;
//        switch (c)
//        {
//        case '&':
//          sb.append("&amp;");
//          break;
//        case '<':
//          sb.append("&lt;");
//          break;
//        case '>':
//          sb.append("&gt;");
//          break;
//        case '"':
//          sb.append("&quot;");
//          break;
//        case '\n':
//          sb.append("<br/>");
//          break;
//        default:
//          sb.append(c);
//        }
//      }
//    }
//
//    return sb.toString();
//  }

//  public static String textCut(String s, int len, String append)
//  {
//    if (s == null) {
//      return null;
//    }
//    int slen = s.length();
//    if (slen <= len) {
//      return s;
//    }
//
//    int maxCount = len * 2;
//    int count = 0;
//    int i = 0;
//    for (; (count < maxCount) && (i < slen); ++i) {
//      if (s.codePointAt(i) < 256)
//        ++count;
//      else {
//        count += 2;
//      }
//    }
//    if (i < slen) {
//      if (count > maxCount) {
//        --i;
//      }
//      if (!StringUtils.isBlank(append)) {
//        if (s.codePointAt(i - 1) < 256)
//          i -= 2;
//        else {
//          --i;
//        }
//        return s.substring(0, i) + append;
//      }
//      return s.substring(0, i);
//    }
//
//    return s;
//  }

//  public static String htmlCut(String s, int len, String append)
//  {
//    String text = html2Text(s, len * 2);
//    return textCut(text, len, append);
//  }

//  public static String html2Text(String html, int len) {
//    try {
//      Lexer lexer = new Lexer(html);
//
//      StringBuilder sb = new StringBuilder(html.length());
//      Node node;
//      while ((node = lexer.nextNode()) != null)
//      {
//        Node node;
//        if (node instanceof TextNode) {
//          sb.append(node.toHtml());
//        }
//        if (sb.length() > len) {
//          break;
//        }
//      }
//      return sb.toString();
//    } catch (ParserException e) {
//      throw new RuntimeException(e);
//    }
//  }

//  public static boolean contains(String str, String search)
//  {
//    if ((StringUtils.isBlank(str)) || (StringUtils.isBlank(search))) {
//      return false;
//    }
//    String reg = StringUtils.replace(search, "*", ".*");
//    Pattern p = Pattern.compile(reg);
//    return p.matcher(str).matches();
//  }

//  public static boolean containsKeyString(String str) {
//    if (StringUtils.isBlank(str)) {
//      return false;
//    }
//
//    return (str.contains("'")) || (str.contains("\"")) || (str.contains("\r")) || 
//      (str.contains("\n")) || (str.contains("\t")) || 
//      (str.contains("\b")) || (str.contains("\f"));
//  }

//  public static String replaceKeyString(String str)
//  {
//    if (containsKeyString(str)) {
//      return str.replace("'", "\\'").replace("\"", "\\\"")
//        .replace("\r", "\\r").replace("\n", "\\n")
//        .replace("\t", "\\t").replace("\b", "\\b")
//        .replace("\f", "\\f");
//    }
//    return str;
//  }

  public static String getAppPath(Class<?> cls)
  {
    if (cls == null)
      throw new IllegalArgumentException("");
    ClassLoader loader = cls.getClassLoader();
    String clsName = cls.getName() + ".class";
    Package pack = cls.getPackage();
    String path = "";
    if (pack != null) {
      String packName = pack.getName();
      if ((packName.startsWith("java.")) || (packName.startsWith("javax.")))
        throw new IllegalArgumentException("");
      clsName = clsName.substring(packName.length() + 1);
      if (packName.indexOf(".") < 0) {
        path = packName + "/";
      } else {
        int start = 0; int end = 0;
        end = packName.indexOf(".");
        while (end != -1) {
          path = path + packName.substring(start, end) + "/";
          start = end + 1;
          end = packName.indexOf(".", start);
        }
        path = path + packName.substring(start) + "/";
      }
    }
    URL url = loader.getResource(path + clsName);
    String realPath = url.getPath();
    int pos = realPath.indexOf("file:");
    if (pos > -1)
      realPath = realPath.substring(pos + 5);
    pos = realPath.indexOf(path + clsName);
    realPath = realPath.substring(0, pos - 1);
    if (realPath.endsWith("!"))
      realPath = realPath.substring(0, realPath.lastIndexOf("/"));
    try {
      realPath = URLDecoder.decode(realPath, "utf-8");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return realPath;
  }

  public static String getGroupCacheKey(HttpServletRequest request, String M, String L)
  {
    String SessionID = "";
    if (request != null) {
      SessionID = request.getSession().getId();
    }
    return "GroupCache_" + M + "_" + L + "_" + SessionID;
  }

  public static Map<String, Object> putGroupCaption(Map<String, Object> groupMap, String labelID, String labelHTML)
  {
    if (groupMap == null)
      return null;
    String captionHTML = nul((String)groupMap.get("Caption"));
    String panelHTML = nul((String)groupMap.get("Panel_" + labelID));
    if (equ(captionHTML, "")) {
      captionHTML = captionHTML + "<div class=\"block_group_t active\" groupID=\"" + 
        labelID + "\" style=\"cursor:pointer;\">";
      panelHTML = "<div class=\"block_group_w\" groupPanelID=\"" + 
        labelID + "\">";
    } else {
      captionHTML = captionHTML + "<div class=\"block_group_t\" groupID=\"" + labelID + 
        "\" style=\"cursor:pointer;\">";
      panelHTML = "<div class=\"block_group_w\" style=\"display:none\" groupPanelID=\"" + 
        labelID + "\">";
    }

    captionHTML = captionHTML + labelHTML;
    captionHTML = captionHTML + "</div>";
    groupMap.put("Caption", captionHTML);
    groupMap.put("Panel_" + labelID, panelHTML);
    return groupMap;
  }

  public static Map<String, Object> putGroupPanel(Map<String, Object> groupMap, String labelID, String elemHTML)
  {
    if (groupMap == null) {
      groupMap = new LinkedHashMap();
    }
    String html = nul((String)groupMap.get("Panel_" + labelID));
    groupMap.put("Panel_" + labelID, html + elemHTML);
    return groupMap;
  }

  public static String getGroupHTML(Map<String, Object> groupMap)
  {
    if (groupMap == null)
      return "";
    StringBuffer html = new StringBuffer();
    if (!groupMap.isEmpty()) {
      html.append("<div class=\"block_group block block_large\" id=\"fieldGroup\" style=\"\">");
      html.append("<div class=\"block_group_tab\">");
      html.append(nul(groupMap.get("Caption")));
      html.append("</div><div class=\"block_group_panel\">");
      Iterator it = groupMap
        .entrySet().iterator();
      while (it.hasNext()) {
        Entry next =
          (Entry)it
          .next();
        if (!((String)next.getKey()).equals("Caption")) {
          html.append(nul(next.getValue()) + "</div>");
        }
      }
      html.append("</div></div>");
      return nul(html.toString());
    }
    return "";
  }

  public static String parseURL(String URL, String Arg)
  {
    String Args = "";
    if (URL.indexOf("?") == -1) {
      return Args;
    }
    Args = URL.split("\\?")[1];

    String[] argList = Args.split("\\&");
    String[] emp = null;
    for (String str : argList) {
      emp = str.split("=");
      if (emp[0].equalsIgnoreCase(Arg)) {
        return emp[1];
      }
    }
    return "";
  }

  public static String mapjson(Map<?, ?> map)
  {
    StringBuilder json = new StringBuilder();
    json.append("{");
    if ((map != null) && (map.size() > 0)) {
      for (Iterator localIterator = map.keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
        json.append(objectjson(key));
        json.append(":");
        json.append(objectjson(map.get(key)));
        json.append(","); }

      json.setCharAt(json.length() - 1, '}');
    } else {
      json.append("}");
    }
    return json.toString();
  }

  public static String objectjson(Object obj)
  {
    StringBuilder json = new StringBuilder();
    if (obj == null)
      json.append("\"\"");
    else if ((obj instanceof String) || (obj instanceof Integer) || 
      (obj instanceof Float) || (obj instanceof Boolean) || 
      (obj instanceof Short) || (obj instanceof Double) || 
      (obj instanceof Long) || (obj instanceof BigDecimal) || 
      (obj instanceof BigInteger) || (obj instanceof Byte))
      json.append("\"").append(stringjson(obj.toString())).append("\"");
    else if (obj instanceof Map) {
      json.append(mapjson((Map)obj));
    }
    return json.toString();
  }

  public static String stringjson(String s)
  {
    if (s == null)
      return "";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); ++i) {
      char ch = s.charAt(i);
      switch (ch)
      {
      case '"':
        sb.append("\\\"");
        break;
      case '\\':
        sb.append("\\\\");
        break;
      case '\b':
        sb.append("\\b");
        break;
      case '\f':
        sb.append("\\f");
        break;
      case '\n':
        sb.append("\\n");
        break;
      case '\r':
        sb.append("\\r");
        break;
      case '\t':
        sb.append("\\t");
        break;
      case '/':
        sb.append("/");
        break;
      default:
        if ((ch >= 0) && (ch <= '\037')) {
          String ss = Integer.toHexString(ch);
          sb.append("\\u");
          for (int k = 0; k < 4 - ss.length(); ++k) {
            sb.append('0');
          }
          sb.append(ss.toUpperCase());
        } else {
          sb.append(ch);
        }
      }
    }
    return sb.toString();
  }

  public static String sumFormat(String sum, int xsw) {
    if (isNull(sum)) {
      sum = "0";
    }
    BigDecimal big = new BigDecimal(sum);
    big = big.setScale(xsw, 4);
    return big.toString();
  }

//  public static String calc(String expr)
//  {
//    SimpleEvaluationContext context = new SimpleEvaluationContext();
//    try {
//      Expr e = ExprParser.parse(expr);
//      Exprs.toUpperCase(e);
//      if (e instanceof ExprEvaluatable) {
//        e = ((ExprEvaluatable)e).evaluate(context);
//      }
//      return e.toString(); } catch (Exception e) {
//    }
//    return null;
//  }

  public static String replaceIgnoerCase(String origString, String reps)
  {
    return Pattern.compile(reps, 2)
      .matcher(origString).replaceAll(reps);
  }

//  public static String getColumnName(int columnIndex)
//  {
//    int b = columnIndex / 26; int c = columnIndex - b * 26 + 65; int d = b + 64;
//    return ((d == 64) ? "" : Character.valueOf((char)d)) + (char)c;
//  }

//  public static String doUrl(String urlString, String[] requestMethod)
//  {
//    String res = "";
//    try {
//      URL url = new URL(urlString);
//      HttpURLConnection conn = (HttpURLConnection)url
//        .openConnection();
//      conn.setDoOutput(true);
//      String strMethod = "GET";
//      if ((requestMethod == null) || (requestMethod.length == 0))
//        strMethod = "GET";
//      else
//        strMethod = requestMethod[0];
//      conn.setRequestMethod(strMethod);
//      BufferedReader in = new BufferedReader(
//        new InputStreamReader(conn.getInputStream(), 
//        "UTF-8"));
//      String line;
//      while ((line = in.readLine()) != null)
//      {
//        String line;
//        res = res + line;
//      }
//      in.close();
//    } catch (Exception e) {
//      res = e.getMessage();
//    }
//    return res;
//  }

  public static List<Map<String, Object>> getCatchList(List<Map<String, Object>> dataList, Map<String, String> condMap)
  {
    if ((condMap != null) && (condMap.size() > 0)) {
      List finalDataList = new ArrayList();
      for (Map dataMap : dataList) {
        boolean flag = true;
        Iterator iterator = dataMap.keySet().iterator();
        while (iterator
          .hasNext()) {
          String key = (String)iterator.next();
          Object obj = dataMap.get(key);
          boolean flag1 = false;
          Iterator it = condMap.keySet().iterator();
          while (it
            .hasNext()) {
            String condKey = (String)it.next();
            String condValue = (String)condMap.get(condKey);
            if ((condValue == null) || (condValue.trim().length() <= 0) || 
              (!condKey.equalsIgnoreCase(key))) continue;
            if (obj != null) {
              if (obj.toString().indexOf(condValue) != -1) continue;
              flag1 = true;
              flag = false;
              break;
            }

            flag1 = true;
            flag = false;
            break;
          }

          if (flag1) {
            break;
          }
        }
        if (flag) {
          finalDataList.add(dataMap);
        }

      }

      return finalDataList;
    }
    return dataList;
  }

  public static String arrToStr(String[] arr)
  {
    if (arr == null) {
      return "arr is empty.";
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < arr.length; ++i)
      sb.append("[").append(arr[i]).append("] ");
    return sb.toString();
  }

//  public static String joinStr(List<String> list, int level)
//  {
//    StringBuilder sb = new StringBuilder();
//    for (String str : list) {
//      if (sb.length() != 0) {
//        sb.append(getSplitChar(level));
//      }
//      if (level == 1) {
//        str = cleanSplitStr(str);
//      }
//      sb.append(str);
//    }
//    return sb.toString();
//  }

//  public static List<String> splitStr(String str, int level)
//  {
//    List list = new ArrayList();
//    String[] ss = split(str, getSplitChar(level));
//    for (String s : ss) {
//      list.add(s);
//    }
//    return list;
//  }

//  private static String getSplitChar(int level)
//  {
//    char splitChar = '\001';
//    if (level == 1)
//      splitChar = '\001';
//    else if (level == 2)
//      splitChar = '\002';
//    else if (level == 3)
//      splitChar = '\003';
//    else if (level == 4)
//      splitChar = '\004';
//    else if (level == 5) {
//      splitChar = '\005';
//    }
//    return splitChar;
//  }

  private static String cleanSplitStr(String str)
  {
    str = str.replaceAll("\001", "");
    str = str.replaceAll("\002", "");
    str = str.replaceAll("\003", "");
    str = str.replaceAll("\004", "");
    str = str.replaceAll("\005", "");
    return str;
  }

  public static String readFileContent(File file)
  {
    if ((file == null) || (!file.exists())) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    try {
      InputStream is = new FileInputStream(file);
      BufferedReader fr = new BufferedReader(
        new InputStreamReader(is, 
        "utf-8"));
      char[] temp = new char[1024];
      int i = -1;
      while ((i = fr.read(temp)) != -1) {
        sb.append(temp, 0, i);
      }
      fr.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  public static String FormetFileSize(long fileS)
  {
    DecimalFormat df = new DecimalFormat("#.00");
    String fileSizeString = "";
    if (fileS < 1024L)
      fileSizeString = df.format(fileS) + "B";
    else if (fileS < 1048576L)
      fileSizeString = df.format(fileS / 1024.0D) + "K";
    else if (fileS < 1073741824L)
      fileSizeString = df.format(fileS / 1048576.0D) + "M";
    else {
      fileSizeString = df.format(fileS / 1073741824.0D) + "G";
    }
    return fileSizeString;
  }

  public static String getServerLinkPath()
  {
    return serverLinkPath;
  }

  public static String getDelegatePath()
  {
    return delegatePath;
  }

  public static String getStartPath()
  {
    return startPath;
  }

  public static String getStaticResourceURL() {
    return staticResourceURL;
  }

  public static String wrapDelegatePath(String url)
  {
    url = cStr(url);
    if (url.startsWith(getDelegatePath())) {
      url = url.replaceAll(getDelegatePath(), "");
    }
    if (url.startsWith("/")) {
      url = getDelegatePath() + url;
    }
    return url;
  }

  public static String wrapStartPath(String url)
  {
    url = cStr(url);
    if (url.startsWith(getStartPath())) {
      url = url.replaceAll(getStartPath(), "");
    }
    if (url.startsWith("/")) {
      url = getStartPath() + url;
    }
    return url;
  }

  public String unWrapStartPath(String url) {
    url = cStr(url);
    if (url.startsWith(getStartPath()))
    {
      url = url.replaceAll(getStartPath(), "");
    }
    return url;
  }

  public String unWrapDelegatePath(String url) {
    url = cStr(url);
    if (url.startsWith(getDelegatePath()))
    {
      url = url.replaceAll(getDelegatePath(), "");
    }
    return url;
  }
}