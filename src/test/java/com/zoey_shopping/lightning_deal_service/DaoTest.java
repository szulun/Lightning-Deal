package com.zoey_shopping.lightning_deal_service;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.zoey_shopping.lightning_deal_service.db.dao.SeckillActivityDao;
import com.zoey_shopping.lightning_deal_service.db.mappers.SeckillActivityMapper;
import com.zoey_shopping.lightning_deal_service.db.po.SeckillActivity;

@SpringBootTest
public class DaoTest {
    
    @Resource
    private SeckillActivityMapper seckillActivityMapper;

    @Resource
    private SeckillActivityDao seckillActivityDao;

    @Test
    void SeckillActivityTest() {
        SeckillActivity seckillActivity = new SeckillActivity();
        seckillActivity.setName("test4");
        seckillActivity.setCommodityId(777L);
        seckillActivity.setTotalStock(150L);
        seckillActivity.setSeckillPrice(new BigDecimal(100));
        seckillActivity.setActivityStatus(7);
        seckillActivity.setOldPrice(new BigDecimal(1000));
        seckillActivity.setAvailableStock(150);
        seckillActivity.setLockStock(0L);
        seckillActivityMapper.insert(seckillActivity);
        System.out.println("====>>>>" + seckillActivityMapper.selectByPrimaryKey(1L));
    }

    @Test
    void setSeckillActivityQuery() {
        List<SeckillActivity> seckillActivitys = seckillActivityDao.querySeckillActivitysByStatus(0);
        System.out.println(seckillActivitys.size());
        seckillActivitys.stream().forEach(seckillActivity -> System.out.println(seckillActivity.toString()));
    }
}
