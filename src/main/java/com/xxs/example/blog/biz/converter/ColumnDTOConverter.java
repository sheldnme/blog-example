package com.xxs.example.blog.biz.converter;

import com.xxs.example.blog.biz.dto.ColumnDTO;
import com.xxs.example.blog.dal.entity.ColumnDO;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ColumnDTOConverter
 * @description
 * @date created in 11:40 2020/3/12
 */
public class ColumnDTOConverter {

    /**
     * Convert ColumnDO to ColumnDTO
     * @param columnDO
     * @return
     */
    public static ColumnDTO convertToColumnDTO(ColumnDO columnDO) {
        if (columnDO == null) {
            return null;
        }
        ColumnDTO columnDTO = new ColumnDTO();

        columnDTO.setId(columnDO.getId());
        columnDTO.setName(columnDO.getName());
        columnDTO.setContentCount(columnDO.getContentCount());

        return columnDTO;
    }

    /**
     * Convert ColumnDTO to ColumnDO
     * @param columnDTO
     * @return
     */
    public static ColumnDO convertToColumnDO(ColumnDTO columnDTO) {
        if (columnDTO == null) {
            return null;
        }
        ColumnDO columnDO = new ColumnDO();

        columnDO.setId(columnDTO.getId());
        columnDO.setName(columnDTO.getName());
        columnDO.setContentCount(columnDTO.getContentCount());

        return columnDO;
    }
}
