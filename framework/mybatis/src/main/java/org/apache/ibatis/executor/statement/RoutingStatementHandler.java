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
package org.apache.ibatis.executor.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.ace.framework.util.DbSetUpUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.PreparedStatement;


/**
 * 
 * 修改于 2015-09-22
 * 
 * @author WuZhiWei
 */
public class RoutingStatementHandler implements StatementHandler {

	private final StatementHandler delegate;
	private PreparedStatement countPreparedStatement;

	public RoutingStatementHandler(Executor executor, MappedStatement ms,
			Object parameter, RowBounds rowBounds, ResultHandler resultHandler,
			BoundSql boundSql) {

		switch (ms.getStatementType()) {
		case STATEMENT:
			delegate = new SimpleStatementHandler(executor, ms, parameter,
					rowBounds, resultHandler, boundSql);
			break;
		case PREPARED:
			delegate = new PreparedStatementHandler(executor, ms, parameter,
					rowBounds, resultHandler, boundSql);
			break;
		case CALLABLE:
			delegate = new CallableStatementHandler(executor, ms, parameter,
					rowBounds, resultHandler, boundSql);
			break;
		default:
			throw new ExecutorException("Unknown statement type: "
					+ ms.getStatementType());
		}

	}

	public Statement prepare(Connection connection) throws SQLException {
//		 return delegate.prepare(connection);
		{
			RowBounds rowBounds = getRowBounds();
			String countSQL = null;
			if (rowBounds != null) {
				int offset = rowBounds.getOffset();
				int limit = rowBounds.getLimit();
				if ((offset != 0) || (limit != Integer.MAX_VALUE)) {
					BoundSql boundSql = getBoundSql();
					String sql = boundSql.getSql().trim();
					countSQL = "select count(1) from (" + sql + ") tempcount";

					if (DbSetUpUtil.DBTYPE == 0)
						sql = "select * from (select tmp_tb.*,rownum row_id from ("
								+ sql
								+ ") tmp_tb where rownum<="
								+ (limit + offset)
								+ ")"
								+ " where row_id>"
								+ offset;
					else if (DbSetUpUtil.DBTYPE == 1) {
						sql = sql + " limit " + limit + " offset " + offset;
					}

					boundSql.setSql(sql);

					limit = Integer.MAX_VALUE;
					rowBounds.setDefault();
				}
				if (rowBounds.isCountRowNum()) {
					this.countPreparedStatement = connection.prepareStatement(countSQL);
				}
			}
			return this.delegate.prepare(connection);
		}

	}

	public void parameterize(Statement statement) throws SQLException {
		delegate.parameterize(statement);
	}

	public void batch(Statement statement) throws SQLException {
		delegate.batch(statement);
	}

	public int update(Statement statement) throws SQLException {
		return delegate.update(statement);
	}
	
	public <E> List<E> query(Statement statement, ResultHandler resultHandler)
			throws SQLException {
		List<E> list = delegate.<E> query(statement, resultHandler);
		if (getRowBounds().isCountRowNum()) {
		    int count = getCount();
			getRowBounds().setRowcount(count);
			return list;
		}
		
		return list;
	}

	private int getCount() throws SQLException {
		if (getRowBounds().isCountRowNum()) {
			getParameterHandler().setParameters(this.countPreparedStatement);
			ResultSet rs = this.countPreparedStatement.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			rs.close();
			this.countPreparedStatement.close();
		}
		return 0;
	}

	public BoundSql getBoundSql() {
		return delegate.getBoundSql();
	}

	public RowBounds getRowBounds() {
		return this.delegate.getRowBounds();
	}

	public ParameterHandler getParameterHandler() {
		return delegate.getParameterHandler();
	}

}
