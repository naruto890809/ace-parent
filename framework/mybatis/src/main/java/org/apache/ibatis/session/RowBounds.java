/*
 *    Copyright 2009-2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.session;

/**
 * @author Clinton Begin
 */
/*public class RowBounds {

  public final static int NO_ROW_OFFSET = 0;
  public final static int NO_ROW_LIMIT = Integer.MAX_VALUE;
  public final static RowBounds DEFAULT = new RowBounds();

  private int offset;
  private int limit;

  public RowBounds() {
    this.offset = NO_ROW_OFFSET;
    this.limit = NO_ROW_LIMIT;
  }

  public RowBounds(int offset, int limit) {
    this.offset = offset;
    this.limit = limit;
  }

  public int getOffset() {
    return offset;
  }

  public int getLimit() {
    return limit;
  }

}*/


public class RowBounds
{
  public static final int NO_ROW_OFFSET = 0;
  public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;
  public static final RowBounds DEFAULT = new RowBounds();
  private int offset;
  private int limit;
  private int rowcount;
  private String countSQL;
  private boolean isCountRowNum = false;

  public RowBounds() {
    this.offset = 0;
    this.limit = Integer.MAX_VALUE;
    this.isCountRowNum = false;
    this.rowcount = 0;
  }

  public RowBounds(int offset, int limit) {
    this.offset = offset;
    this.limit = limit;
    this.isCountRowNum = false;
    this.rowcount = 0;
  }

  public RowBounds(int offset, int limit, boolean isCountRowNum) {
    this.offset = offset;
    this.limit = limit;
    this.isCountRowNum = isCountRowNum;
    this.rowcount = 0;
  }

  public int getOffset() {
    return this.offset;
  }

  public int getLimit() {
    return this.limit;
  }

  public void setDefault() {
    this.limit = DEFAULT.limit;
    this.offset = DEFAULT.offset;
  }

  public boolean isCountRowNum() {
    return this.isCountRowNum;
  }

  public void setCountRowNum(boolean isCountRowNum) {
    this.isCountRowNum = isCountRowNum;
  }

  public String getCountSQL() {
    return this.countSQL;
  }

  public int getRowcount() {
    return this.rowcount;
  }

  public void setRowcount(int rowcount) {
    this.rowcount = rowcount;
  }
}
