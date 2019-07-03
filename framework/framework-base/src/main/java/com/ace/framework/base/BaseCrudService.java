package com.ace.framework.base;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 增删改查服务接口
 * Created by lsx on 2015/10/31.
 */
public interface BaseCrudService<T> {

    public abstract T getById(String id);

    public abstract int save(T entity);

    public abstract int update(T entity);

    public abstract int delete(T entity);

    public abstract int saveBatch(Collection<T> entities);

    public abstract List<T> findList(T entity);

    public abstract Integer getCount();

    public abstract Integer getCountBySelective(T entity);

    public abstract T getBySelective(T entity);

    public abstract Page<T> search(T entity, Page<T> page);

    public abstract int deleteById(String id) ;

    public abstract void batchRemove(String ids[],String corpCode,String parentCorpCode) ;

    public abstract int batchRemoveInt(String ids[],String corpCode,String parentCorpCode) ;
}
