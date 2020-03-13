package com.xxs.example.blog.biz.converter;

import com.xxs.example.blog.biz.dto.ContentDTO;
import com.xxs.example.blog.dal.entity.ContentDO;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ContentDTOConverter
 * @description
 * @date created in 11:39 2020/3/12
 */
public class ContentDTOConverter {

    /**
     * Convert ContentDO to ContentDTO
     * @param contentDO
     * @return
     */
    public static ContentDTO convertToContentDTO(ContentDO contentDO) {
        if (contentDO == null) {
            return null;
        }
        ContentDTO contentDTO = new ContentDTO();

        contentDTO.setId(contentDO.getId());
        contentDTO.setName(contentDO.getName());
        contentDTO.setColumnId(contentDO.getColumnId());

        return contentDTO;
    }

    /**
     * Convert ContentDTO to ContentDO
     * @param contentDTO
     * @return
     */
    public static ContentDO convertToContentDO(ContentDTO contentDTO) {
        if (contentDTO == null) {
            return null;
        }
        ContentDO contentDO = new ContentDO();

        contentDO.setId(contentDTO.getId());
        contentDO.setName(contentDTO.getName());
        contentDO.setColumnId(contentDTO.getColumnId());

        return contentDO;
    }
}
