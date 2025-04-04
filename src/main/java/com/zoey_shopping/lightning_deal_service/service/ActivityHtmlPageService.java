package com.zoey_shopping.lightning_deal_service.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.zoey_shopping.lightning_deal_service.db.dao.SeckillActivityDao;
import com.zoey_shopping.lightning_deal_service.db.dao.SeckillCommodityDao;
import com.zoey_shopping.lightning_deal_service.db.po.SeckillActivity;
import com.zoey_shopping.lightning_deal_service.db.po.SeckillCommodity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActivityHtmlPageService {

    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private SeckillActivityDao seckillActivityDao;

    @Resource
    private SeckillCommodityDao seckillCommodityDao;

    public void createActivityHtml (long seckillActivityId) {

        PrintWriter writer = null;
        try {
            SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(seckillActivityId);
            SeckillCommodity seckillCommodity = seckillCommodityDao.querySeckillCommodityById(seckillActivity.getCommodityId());

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("seckillActivity", seckillActivity);
            resultMap.put("seckillCommodity", seckillCommodity);
            resultMap.put("seckillPrice", seckillActivity.getSeckillPrice());
            resultMap.put("oldPrice", seckillActivity.getOldPrice());
            resultMap.put("commodityId", seckillActivity.getCommodityId());
            resultMap.put("commodityName", seckillCommodity.getCommodityName());
            resultMap.put("commodityDesc", seckillCommodity.getCommodityDesc());

            Context context = new Context();
            context.setVariables(resultMap);

            File file = new File("src/main/resources/templates/" + "seckill_item_" + seckillActivityId + ".html");
            writer = new PrintWriter(file);
            templateEngine.process("seckill_item", context, writer);
        } catch (Exception e) {
            log.error(e.toString());
            log.error("Fail to form static page: " + seckillActivityId);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}