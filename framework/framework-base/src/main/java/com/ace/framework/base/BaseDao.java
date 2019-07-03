package com.ace.framework.base;

import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 基础dao层，提供基础的增删改查方法
 *
 * @author WuZhiWei
 * @since 2015-11-03 17:24:13
 */
public class BaseDao<E> extends SqlSessionDaoSupport {
    private Class<E> entityClass;

    public BaseDao() {
        Type type = super.getClass().getGenericSuperclass();
        Type trueType = ((java.lang.reflect.ParameterizedType) type).getActualTypeArguments()[0];
        this.entityClass = ((Class) trueType);
    }

    /**
     * 根据sql的id生成该sql的具体声明
     *
     * @param sqlId sql的id
     * @return 生成该sql的具体声明
     */
    public String generateStatement(String sqlId) {
        return this.entityClass.getName() + "." + sqlId;
    }

    /**
     * 插入记录,默认是sqlId为insert，不需要参数（根据具体sql，可批量，可单个）
     *
     * @return 成功返回影响的行数，否则返回0
     */
    public int insert() {
        return insert(generateStatement("insert"), null);
    }

    /**
     * 插入记录,默认sqlId为insert（根据具体sql，可批量，可单个）
     *
     * @param parameter sql参数
     * @return 成功返回影响的行数，否则返回0
     */
    public int insert(Object parameter) {
        return insert(generateStatement("insert"), parameter);
    }

    private void fillKeyAndString(E entity, Class<?> clazz) {
        if (BaseEntity.class.equals(clazz)) {
            BaseEntity base = (BaseEntity) entity;
            Date now = new Date();
            if (base.getCreateTime() == null) {
                base.setCreateTime(now);
            }
            if (base.getLastModifyTime() == null) {
                base.setLastModifyTime(now);
            }


            if(StringUtils.isBlank(base.getCorpCode())){
                base.setCorpCode(ExecutionContext.getCorpCode());
            }

            if(StringUtils.isBlank(base.getParentCorpCode())){
                base.setParentCorpCode(ExecutionContext.getParentCorpCode());
            }

            if(StringUtils.isBlank(base.getCreateBy())){
                if(StringUtils.isNotBlank(ExecutionContext.getUserId())){
                    base.setCreateBy(ExecutionContext.getUserId());
                }
            }
        }
        if (clazz == null) {
            clazz = entity.getClass();
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {

                if(Modifier.isFinal(field.getModifiers())||Modifier.isStatic(field.getModifiers())){
                    //static 或 final 类型
                    continue;
                };

                if (!field.getGenericType().equals(String.class)) {
                    //非String类型
                    continue;
                }
                String methodName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method getMethod = entity.getClass().getMethod("get" + methodName);
                if (getMethod.invoke(entity) != null) {
                    //字段非空
                    continue;
                }
                Nullable nullable = field.getAnnotation(Nullable.class);
                if (nullable != null) {
                    //字段允许空
                    continue;
                }
                Method method = entity.getClass().getMethod("set" + methodName, String.class);
                PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                if (primaryKey != null) {
                    //空主键自动填充UUID
                    method.invoke(entity, UUIDUtil.genUUID());
                } else {
                    //非主键填充空字符串
                    method.invoke(entity, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!clazz.getSuperclass().equals(Object.class)) {
            fillKeyAndString(entity, clazz.getSuperclass());
        }
    }

    /**
     * 指定sql声明，插入记录（根据具体sql，可批量，可单个）
     *
     * @param statement sql声明
     * @param parameter sql参数
     * @return 成功返回影响的行数，否则返回0
     */
    public int insert(String statement, Object parameter) {
        Assert.notNull(statement, "Property statement is required");
        if (parameter != null) {
            if (parameter.getClass().equals(entityClass)) {
                E entity = (E) parameter;
                fillKeyAndString(entity, null);
            } else if (parameter instanceof Collection) {
                for (Object obj : (Collection) parameter) {
                    try {
                        E entity = (E) obj;
                        fillKeyAndString(entity, null);
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }
        return getSqlSession().insert(statement, parameter);
    }

    /**
     * 更新记录，默认sqlId为update（根据具体sql，可批量，可单个）
     *
     * @return 成功返回影响的行数，否则返回0
     */
    public int update() {

        return update(generateStatement("update"), null);
    }

    private void fillUpdateEntity(E entity, Class<?> clazz) {
        if (BaseEntity.class.equals(clazz)) {
            BaseEntity base = (BaseEntity) entity;
            Date now = new Date();
            //插入最后操作时间
            if (base.getLastModifyTime() == null) {
                base.setLastModifyTime(now);
            }
            //插入最后修改操作人
            if(StringUtils.isBlank(base.getLastModifyBy())){
                if(StringUtils.isNotBlank(ExecutionContext.getUserId())){
                    base.setLastModifyBy(ExecutionContext.getUserId());
                }
            }
            if(StringUtils.isBlank(base.getCorpCode())){
                base.setCorpCode(ExecutionContext.getCorpCode());
            }

            if(StringUtils.isBlank(base.getParentCorpCode())){
                base.setParentCorpCode(ExecutionContext.getParentCorpCode());
            }
        }
        if (clazz == null) {
            clazz = entity.getClass();
        }

        if (!clazz.getSuperclass().equals(Object.class)) {
            fillUpdateEntity(entity, clazz.getSuperclass());
        }
    }

    /**
     *  默认更新 lastModifyBy lastModifyTime
     *
     * 根据指定参数，更新记录，默认sqlId为update（根据具体sql，可批量，可单个）
     *
     * @param parameter sql参数
     * @return 成功返回影响的行数，否则返回0
     */
    public int update(Object parameter) {
        if (parameter != null) {
            if (parameter.getClass().equals(entityClass)) {
                E entity = (E) parameter;
                fillUpdateEntity(entity, null);
            } else if (parameter instanceof Collection) {
                for (Object obj : (Collection) parameter) {
                    try {
                        E entity = (E) obj;
                        fillUpdateEntity(entity, null);
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }
        return update(generateStatement("update"), parameter);
    }

    /**
     * 指定sql声明，更新记录（根据具体sql，可批量，可单个）
     *
     * @param statement sql声明
     * @param parameter sql参数
     * @return 成功返回影响的行数，否则返回0
     */
    public int update(String statement, Object parameter) {
        Assert.notNull(statement, "Property statement is required");
        return getSqlSession().update(statement, parameter);
    }

    /**
     * 删除记录，默认sqlId为delete
     *
     * @return 成功返回影响的行数，否则返回0
     */
    public int delete() {
        return delete(generateStatement("delete"));
    }

    /**
     * 根据指定参数删除记录，默认sqlId为delete（根据具体sql，可批量，可单个）
     *
     * @param parameter sql参数
     * @return 成功返回影响的行数，否则返回0
     */
    public int delete(Object parameter) {
        return delete(generateStatement("delete"), parameter);
    }

    /**
     * 指定sql声明，删除记录（根据具体sql，可批量，可单个）
     *
     * @param parameter sql参数
     * @param statement 指定sql声明
     * @return 成功返回影响的行数，否则返回0
     */
    public int delete(String statement, Object parameter) {
        Assert.notNull(statement, "Property statement is required");
        return getSqlSession().delete(statement, parameter);
    }

    /**
     * 根据默认sqlId为getObject的sql获取一个对象
     *
     * @return Object
     */
    public <T> T getObject() {
        return getObject(generateStatement("getObject"));
    }

    /**
     * 根据给定参数获取一个对象，默认sqlId为getObject
     *
     * @param parameter sql声明
     * @return Object
     */
    public <T> T getObject(Object parameter) {
        return getObject(generateStatement("getObject"), parameter);
    }

    /**
     * 指定sql声明，根据给定sql参数获取对象
     *
     * @param statement sql声明
     * @param parameter sql参数
     * @return Object
     */
    public <T> T getObject(String statement, Object parameter) {
        Assert.notNull(statement, "Property statement is required");
        return getSqlSession().selectOne(statement, parameter);
    }

    /**
     * 根据默认sqlId为findList的sql获取一组对象
     *
     * @return List
     */
    public <T> List<T> findList() {
        return findList(generateStatement("findList"));
    }

    /**
     * 根据默认sqlId为findList的sql获取一组对象
     *
     * @return List
     */
    public <T> List<T> findList(Object parameter) {
        return findList(generateStatement("findList"), parameter);
    }

    /**
     * 指定sql声明，根据给定sql参数获取一组对象
     *
     * @param statement sql声明
     * @param parameter sql参数
     * @return List
     */
    public <T> List<T> findList(String statement, Object parameter) {
        Assert.notNull(statement, "Property statement is required");
        return getSqlSession().selectList(statement, parameter);
    }

    /**
     * 指定sql声明及参数获取一条记录或者一个字段
     *
     * @param statement sql声明
     * @param parameter sql参数
     * @return 一条记录或者一个字段
     */
    public <T> T getOne(String statement, Object parameter) {
        Assert.notNull(statement, "Property statement is required");
        return getSqlSession().selectOne(statement, parameter);
    }

    /**
     * 从第0条开始，查询limit条记录
     * 默认sqlId为findList的sql声明
     *
     * @param parameter sql参数
     * @param limit     查询记录数
     * @return List
     */
    public List<E> findLimitList(Object parameter, int limit) {
        return findLimitList(generateStatement("findList"), parameter, 0, limit);
    }

    /**
     * 从第0条开始，查询limit条记录
     *
     * @param statement sql声明
     * @param parameter sql参数
     * @param limit     查询记录数
     * @return List
     */
    public List<E> findLimitList(String statement, Object parameter, int limit) {
        return findLimitList(statement, parameter, 0, limit);
    }

    /**
     * 从offset处，查询limit条记录
     *
     * @param statement sql声明
     * @param parameter sql参数
     * @param offset    查询起始记录数
     * @param limit     查询记录数
     * @return List
     */
    public List<E> findLimitList(String statement, Object parameter, int offset, int limit) {
        Assert.notNull(statement, "Property statement is required");
        RowBounds rowBounds = new RowBounds(offset, limit, false);
        return getSqlSession().selectList(statement, parameter, rowBounds);
    }

    /**
     * 分页查询
     *
     * @param statement sql声明，不可以为空
     * @param condition 查询条件，不可以为空
     * @param page      分页信息
     * @return Page<T>
     */
    public <T> Page<T> search(String statement, Object condition, Page<T> page) {
        Assert.notNull(statement, "Property statement is required");
        Assert.notNull(page, "Property page is required");

        List<T> rows;
        if (page.isAutoPaging()) {
            RowBounds rowBounds = new RowBounds(page.getFirst() - 1, page.getPageSize(), true);
            rows = getSqlSession().selectList(statement, condition, rowBounds);
            page.setTotal(rowBounds.getRowcount());
        } else {
            rows = findList(statement, condition);
        }

        page.setRows(rows);
        return page;
    }

}