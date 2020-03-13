package com.xxs.example.blog.dal.template;

import com.xxs.example.blog.dal.model.FieldName;
import com.xxs.example.blog.dal.template.util.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName BaseTemplate
 * @description
 * @date created in 11:04 2020/3/12
 */
@Slf4j
public abstract class BaseTemplate<T> {

    @Resource
    protected JdbcTemplate templateDemo;

    protected Class<T> clazz;

    protected Map<String, String> entityToDbMap;

    @PostConstruct
    public void init() {
        Class tClazz = getClass();

        while (!Objects.equals(Object.class, tClazz)) {
            Type t = tClazz.getGenericSuperclass();
            if (t instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                if (args[0] instanceof Class) {
                    this.clazz = (Class<T>) args[0];
                    break;
                }
            }
            tClazz = tClazz.getSuperclass();
        }

        tClazz = this.clazz;

        entityToDbMap = new HashMap<>();

        List<Field> fields = new ArrayList<>();

        try {
            while (tClazz != null) {
                fields.addAll(Arrays.asList(tClazz.getDeclaredFields()));
                tClazz = (Class<T>) tClazz.getSuperclass();
            }

            for (Field field : fields) {
                field.setAccessible(true);
                String key = field.getName();
                boolean fieldHasAnno = field.isAnnotationPresent(FieldName.class);
                if (fieldHasAnno) {
                    FieldName fieldName = field.getAnnotation(FieldName.class);
                    //数据库对应的字段名
                    key = fieldName.name();
                }
                entityToDbMap.put(field.getName(), key);
            }
        } catch (Exception e) {
            log.error("init base template error", e);
        }
    }

    /**
     * 需要分表分表
     * @param entity
     * @return
     */
    public int save(T entity) {
        JdbcUtil.JdbcResult jdbcResult = JdbcUtil.getInsert(getTableName(), entity, entityToDbMap);
        return templateDemo.update(jdbcResult.getSql(), jdbcResult.getParams());
    }

    public int update(T entity, Map<String, Object> criteria) {
        JdbcUtil.JdbcResult jdbcResult = JdbcUtil.getUpdate(getTableName(), entity, entityToDbMap, criteria, null);
        return templateDemo.update(jdbcResult.getSql(), jdbcResult.getParams());
    }



    public T get(Map<String, Object> criteria) {
        JdbcUtil.JdbcResult jdbcResult = JdbcUtil.getSelect(getTableName(),
                clazz, entityToDbMap, criteria, null);

        try {
            Map<String, Object> dbRow = templateDemo.queryForMap(jdbcResult.getSql(), jdbcResult.getParams());
            return JdbcUtil.transferDbResultToEntity(dbRow, entityToDbMap, clazz);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<T> getList(Map<String, Object> criteria) {
        JdbcUtil.JdbcResult jdbcResult = JdbcUtil.getSelect(getTableName(),
                clazz, entityToDbMap, criteria, null);
        try {
            List<Map<String, Object>> dbRows = templateDemo.queryForList(jdbcResult.getSql(), jdbcResult.getParams());

            List<T> entities = new ArrayList<>();
            for (Map<String, Object> dbRow : dbRows) {
                entities.add(JdbcUtil.transferDbResultToEntity(dbRow, entityToDbMap, clazz));
            }
            return entities;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * 获取表名
     * @return
     */
    protected abstract String getTableName();
}
