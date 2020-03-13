package com.xxs.example.blog.biz.service.impl;

import com.google.common.collect.Lists;
import com.xxs.example.blog.biz.converter.ContentDTOConverter;
import com.xxs.example.blog.biz.dto.ContentDTO;
import com.xxs.example.blog.biz.service.ColumnService;
import com.xxs.example.blog.biz.service.ContentService;
import com.xxs.example.blog.dal.entity.ContentDO;
import com.xxs.example.blog.dal.template.ContentTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ContentServiceImpl
 * @description
 * @date created in 11:36 2020/3/12
 */
@Component
@Slf4j
public class ContentServiceImpl implements ContentService {

    @Resource
    private ContentTemplate contentTemplate;

    @Resource
    private ColumnService columnService;

    @Resource
    private TransactionTemplate demoTransactionTemplate;

    @Resource
    private ExecutorService demoFixedThreadPool;

    @Override
    public void create(ContentDTO contentDTO) {
        ContentDO contentDO = ContentDTOConverter.convertToContentDO(contentDTO);
        demoTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    // 1：保存
                    contentTemplate.save(contentDO);

//                    // 2：做其他事
//                    Thread.sleep(4000);
                } catch (Exception e) {
                    log.error("ContentServiceImpl.create transaction exception", e);
                    // 回滚
                    transactionStatus.setRollbackOnly();
                }
            }
        });

        demoFixedThreadPool.execute(() -> {
            try {
                log.info("ContentServiceImpl.create, start update column content count");
                columnService.updateContentCount(contentDO.getColumnId());
            } catch (Exception e) {
                log.error("ContentServiceImpl.create, update content count error", e);
            }
        });




    }

    @Override
    public Integer countByColumnId(Long columnId) {
        List<ContentDO> contentDOList = contentTemplate.getByColumnId(columnId);
        return Optional.ofNullable(contentDOList).orElse(Lists.newArrayList())
                .size();
    }

    @Override
    public void createSync(ContentDTO contentDTO) {
        ContentDO contentDO = ContentDTOConverter.convertToContentDO(contentDTO);
        demoTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    // 1：保存
                    contentTemplate.save(contentDO);
                } catch (Exception e) {
                    log.error("ContentServiceImpl.create transaction exception", e);
                    // 回滚
                    transactionStatus.setRollbackOnly();
                }
            }
        });

        columnService.updateContentCount(contentDO.getColumnId());
    }

}
