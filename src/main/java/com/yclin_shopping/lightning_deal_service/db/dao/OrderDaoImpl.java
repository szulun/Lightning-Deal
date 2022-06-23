package com.yclin_shopping.lightning_deal_service.db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yclin_shopping.lightning_deal_service.db.mappers.OrderMapper;
import com.yclin_shopping.lightning_deal_service.db.po.Order;

@Repository
public class OrderDaoImpl implements OrderDao {
    
    @Resource
    private OrderMapper mapper;

    @Override
    public void insertOrder(Order order) {
        mapper.insert(order);
    }

    @Override
    public Order queryOrder(String orderNo) {
        return mapper.selectByOrderNo(orderNo);
    }

    @Override
    public void updateOrder(Order order) {
        mapper.updateByPrimaryKey(order);
    }
}
