package com.xxs.example.blog.biz.service.impl;

import com.xxs.example.blog.biz.converter.ColumnDTOConverter;
import com.xxs.example.blog.biz.dto.ColumnDTO;
import com.xxs.example.blog.biz.service.ColumnService;
import com.xxs.example.blog.biz.service.ContentService;
import com.xxs.example.blog.dal.entity.ColumnDO;
import com.xxs.example.blog.dal.template.ColumnTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ColumnServiceImpl
 * @description
 * @date created in 11:42 2020/3/12
 */
@Component
@Slf4j
public class ColumnServiceImpl implements ColumnService {

    @Resource
    private ColumnTemplate columnTemplate;

    @Resource
    private ContentService contentService;

    @Override
    public void create(ColumnDTO columnDTO) {
        ColumnDO columnDO = ColumnDTOConverter.convertToColumnDO(columnDTO);
        columnTemplate.save(columnDO);
    }

    @Override
    public void updateContentCount(Long columnId) {
        // 1: 查询专栏
        ColumnDO columnDO = columnTemplate.getById(columnId);
        if (Objects.isNull(columnDO)) {
            log.error("专栏不存在，columnId:{}", columnId);
            return;
        }

        // 2: 查询关联专栏的内容数量
        Integer contentCount = contentService.countByColumnId(columnId);
        if (Objects.equals(columnDO.getContentCount(), contentCount)) {
            log.error("统计的内容数量和查询专栏中保存的内容数量一致，不需要更新. column_count:{}, content_count:{}",
                    columnDO.getContentCount(), contentCount);
            return;
        }

        log.info("update. column_count:{}, content_count:{}", columnDO.getContentCount(), contentCount);

        // 3: 更新专栏的数量
        columnDO.setContentCount(contentCount);
        columnDO.setUpdatedAt(new Date());
        columnTemplate.update(columnDO);

    }
}
