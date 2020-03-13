package com.xxs.example.blog.dal.template;

import com.google.common.collect.ImmutableMap;
import com.xxs.example.blog.dal.entity.ContentDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ContentTemplate
 * @description
 * @date created in 11:18 2020/3/12
 */
@Repository
@Slf4j
public class ContentTemplate extends BaseTemplate<ContentDO> {

    @Override
    protected String getTableName() {
        return "t_content";
    }

    public List<ContentDO> getByColumnId(Long columnId) {
        Map<String, Object> criteria = ImmutableMap.<String, Object>builder()
                .put("column_id", columnId)
                .build();
        return getList(criteria);
    }
}
