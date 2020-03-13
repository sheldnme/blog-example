package com.xxs.example.blog.dal.entity;

import com.xxs.example.blog.dal.model.FieldName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ContentDO
 * @description 内容实体
 * @date created in 10:45 2020/3/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 关联的专栏id
     */
    @FieldName(name = "column_id")
    private Long columnId;

    /**
     * 创建时间
     */
    @FieldName(name = "created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @FieldName(name = "updated_at")
    private Date updatedAt;
}
