package com.yclin_shopping.lightning_deal_service.db.dao;

import com.yclin_shopping.lightning_deal_service.db.po.Order;

public interface OrderDao {
    
    void insertOrder(Order order);

    Order queryOrder(String orderNo);

    void updateOrder(Order order);
}
