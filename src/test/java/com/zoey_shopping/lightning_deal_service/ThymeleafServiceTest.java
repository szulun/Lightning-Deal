package com.zoey_shopping.lightning_deal_service;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.zoey_shopping.lightning_deal_service.service.ActivityHtmlPageService;

@SpringBootTest
public class ThymeleafServiceTest {

    @Resource
    private ActivityHtmlPageService activityHtmlPageService;

    @Test
    public void createHtmlTest() {
        activityHtmlPageService.createActivityHtml(19);
    }
}