package com.xxs.example.blog.biz.dto;

import com.xxs.example.blog.dal.model.FieldName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ContentDTO
 * @description
 * @date created in 11:32 2020/3/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDTO {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 关联的专栏id
     */
    private Long columnId;
}
