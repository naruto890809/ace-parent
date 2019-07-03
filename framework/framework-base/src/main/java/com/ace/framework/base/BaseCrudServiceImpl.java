package com.ace.framework.base;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author WuZhiWei
 * @since 2015-11-10 16:48:00
 */
public abstract class BaseCrudServiceImpl<T> implements BaseCrudService<T> {

    public abstract BaseDao<T> getEntityDao();
    private Class<T> entityClass;

    public BaseCrudServiceImpl()
    {
        Type type = super.getClass().getGenericSuperclass();
        Type trueType = ((java.lang.reflect.ParameterizedType)type).getActualTypeArguments()[0];
        this.entityClass = ((Class)trueType);
    }

    @Override
    public T getById(String id) {
        return getEntityDao().getObject(id);
    }

    @Override
    public int save(T entity) {
        return getEntityDao().insert(entity);
    }

    @Override
    public int update(T entity) {
        return getEntityDao().update(entity);
    }

    @Override
    public int delete(T entity) {
        return getEntityDao().delete(entity);
    }
    @Override
    public int deleteById(String id) {
        return getEntityDao().delete(this.entityClass.getName() + "." +"deleteById",id);
    }
    @Override
    public int saveBatch(Collection<T> entities) {
        return getEntityDao().insert(this.entityClass.getName() + "." +"saveBatch", entities);
    }
    @Override
    public Integer getCount(){
        return getEntityDao().getOne(this.entityClass.getName() + "." +"getCount",null);
    }
    @Override
    public Page<T> search(T entity, Page<T> page){
        return getEntityDao().search(this.entityClass.getName() + "." + "findList",entity,page);
    }
    public Integer getCountBySelective(T entity){
        return getEntityDao().getObject(this.entityClass.getName() + "." +"getCountBySelective",entity);
    }
    public T getBySelective(T entity){
        return getEntityDao().getObject(this.entityClass.getName() + "." +"getBySelective",entity);
    }

    @Override
    public List<T> findList(T entity){
        return getEntityDao().findList(entity);
    }

    public void batchRemove(String ids[],String corpCode,String parentCorpCode){
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("item",ids);
        map.put("corpCode",corpCode);
        map.put("parentCorpCode",parentCorpCode);
        getEntityDao().delete(this.entityClass.getName() + ".batchRemove",map);
    }

    public int batchRemoveInt(String ids[],String corpCode,String parentCorpCode){
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("item",ids);
        map.put("corpCode",corpCode);
        map.put("parentCorpCode",parentCorpCode);
        return getEntityDao().delete(this.entityClass.getName() + ".batchRemove",map);
    }

}
