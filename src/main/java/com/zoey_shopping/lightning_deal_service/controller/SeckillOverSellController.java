package com.zoey_shopping.lightning_deal_service.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zoey_shopping.lightning_deal_service.service.SeckillActivityService;
import com.zoey_shopping.lightning_deal_service.service.SeckillOverSellService;

@Controller
public class SeckillOverSellController {
    
    @Resource
    private SeckillOverSellService seckillOverSellService;

    @Resource
    private SeckillActivityService seckillActivityService;

    // @ResponseBody
    // @RequestMapping("/seckill/{seckillActivityId}")
    // public String  seckil(@PathVariable long seckillActivityId){
    //     return seckillOverSellService.processSeckill(seckillActivityId);
    // }

    @ResponseBody
    @RequestMapping("/seckill/{seckillActivityId}")
    public String seckillCommodity(@PathVariable long seckillActivityId) {
        boolean stockValidateResult = seckillActivityService.seckillStockValidator(seckillActivityId);
        return stockValidateResult ? "恭喜搶購成功！" : "商品已售完，下次再來";
    }
}
