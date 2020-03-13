package com.xxs.example.blog.biz.service;

import com.xxs.example.blog.biz.dto.ColumnDTO;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ColumnService
 * @description
 * @date created in 11:34 2020/3/12
 */
public interface ColumnService {

    /**
     * 创建专栏
     * @param columnDTO
     */
    void create(ColumnDTO columnDTO);

    /**
     * 更新内容期数
     * @param columnId
     */
    void updateContentCount(Long columnId);
}
