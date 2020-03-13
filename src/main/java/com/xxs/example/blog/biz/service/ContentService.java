package com.xxs.example.blog.biz.service;

import com.xxs.example.blog.biz.dto.ContentDTO;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ContentService
 * @description
 * @date created in 11:31 2020/3/12
 */
public interface ContentService {


    /**
     * 创建内容
     * @param contentDTO
     */
    void create(ContentDTO contentDTO);

    /**
     * 获取关联专栏的内容数量
     * @param columnId
     * @return
     */
    Integer countByColumnId(Long columnId);

    /**
     * 同步创建内容
     * @param contentDTO
     */
    void createSync(ContentDTO contentDTO);
}
