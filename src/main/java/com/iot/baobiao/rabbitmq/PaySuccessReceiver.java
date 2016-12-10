package com.iot.baobiao.rabbitmq;

import com.iot.baobiao.jooq.tables.pojos.Baobiaoorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

/**
 * Created by jia on 2016/10/16.
 */
public class PaySuccessReceiver {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpMessageSendingOperations operations;

    public void handleMessage(Baobiaoorder order) {
        log.info("收到来自RabbitMQ的消息:" + order.getOuttradeno());
//        operations.convertAndSend("/topic/pay-result", order);
        operations.convertAndSendToUser(order.getPhonenum(), "/topic/pay-result", order);
    }
}
