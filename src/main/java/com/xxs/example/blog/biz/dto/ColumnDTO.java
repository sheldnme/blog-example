package com.xxs.example.blog.biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ColumnDTO
 * @description
 * @date created in 11:33 2020/3/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDTO {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 内容数量
     */
    private Integer contentCount;
}
