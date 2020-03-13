package com.xxs.example.blog.dal.template;

import com.google.common.collect.ImmutableMap;
import com.xxs.example.blog.dal.entity.ColumnDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ColumnTemplate
 * @description
 * @date created in 11:43 2020/3/12
 */
@Repository
@Slf4j
public class ColumnTemplate extends BaseTemplate<ColumnDO> {

    @Override
    protected String getTableName() {
        return "t_column";
    }

    public ColumnDO getById(Long id) {
        Map<String, Object> criteria = ImmutableMap.<String, Object>builder()
                .put("id", id)
                .build();
        return get(criteria);
    }

    public int update(ColumnDO columnDO) {
        Map<String, Object> criteria = ImmutableMap.<String, Object>builder()
                .put("id", columnDO.getId())
                .build();
        return update(columnDO, criteria);
    }


}
