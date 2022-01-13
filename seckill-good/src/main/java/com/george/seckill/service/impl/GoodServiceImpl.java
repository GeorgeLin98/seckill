package com.george.seckill.service.impl;

import com.george.seckill.api.good.service.IGoodService;
import com.george.seckill.api.order.service.IOrderService;
import org.apache.dubbo.config.annotation.Service;

@Service(interfaceClass = IGoodService.class)
public class GoodServiceImpl implements IGoodService {
}
