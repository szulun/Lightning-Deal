package com.yclin_shopping.lightning_deal_service.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yclin_shopping.lightning_deal_service.service.SeckillOverSellService;

@Controller
public class SeckillOverSellController {
    
    @Resource
    private SeckillOverSellService seckillOverSellService;

    @ResponseBody
    @RequestMapping("/seckill/{seckillActivityId}")
    public String  seckil(@PathVariable long seckillActivityId){
        return seckillOverSellService.processSeckill(seckillActivityId);
    }
}
