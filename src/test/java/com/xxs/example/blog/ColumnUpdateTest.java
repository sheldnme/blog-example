package com.xxs.example.blog;

import com.xxs.example.blog.biz.dto.ColumnDTO;
import com.xxs.example.blog.biz.dto.ContentDTO;
import com.xxs.example.blog.biz.service.ColumnService;
import com.xxs.example.blog.biz.service.ContentService;
import com.xxs.example.blog.dal.entity.ColumnDO;
import com.xxs.example.blog.dal.template.ColumnTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName ColumnUpdateTest
 * @description
 * @date created in 14:13 2020/3/12
 */
@SpringBootTest
@Slf4j
public class ColumnUpdateTest {

    @Resource
    private ContentService contentService;

    @Resource
    private ColumnService columnService;

    @Resource
    private ColumnTemplate columnTemplate;

    @Resource
    private TransactionTemplate demoTransactionTemplate;

    /**
     * 测试创建专栏
     */
    @Test
    public void testCreateColumn() {
        ColumnDTO columnDTO = new ColumnDTO();
        columnDTO.setName("测试专栏");
        columnDTO.setContentCount(0);

        columnService.create(columnDTO);

        System.out.println("1212121");

    }

    /**
     * 测试更新专栏
     */
    @Test
    public void testUpdateColumn() {
        ColumnDO columnDO = columnTemplate.getById(2L);
        columnDO.setContentCount(1);
        log.info("column.update:{}", columnTemplate.update(columnDO));
    }

    @Test
    public void testCreateContent() {
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setColumnId(2L);
        contentDTO.setName("内容测试1");
        demoTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                contentService.create(contentDTO);

                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                }
//
                Integer count = contentService.countByColumnId(2L);
//                Integer count = 0;
                System.out.println("count: " + count);
            }
        });
        log.info("contentlist:{}", contentService.countByColumnId(2L));

        try {
            Thread.sleep(5 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量添加
     */
    @Test
    public void testBatchCreateContent() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 800; i ++) {
            ContentDTO contentDTO = new ContentDTO();
            contentDTO.setColumnId(2L);
            contentDTO.setName("内容测试-" + i);
            executorService.execute(() -> {
                demoTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                        contentService.create(contentDTO);

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });
        }

        try {
            Thread.sleep(5 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateContent1() {
        testCreateSyncContent();
    }


    @Test
    public void testCreateContent2() {
        testCreateSyncContent();
    }


    private void testCreateSyncContent() {
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setColumnId(2L);
        contentDTO.setName("内容测试1");
        demoTransactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                contentService.createSync(contentDTO);

                System.out.println("pause");

                System.out.println("end");
            }
        });

    }

}
