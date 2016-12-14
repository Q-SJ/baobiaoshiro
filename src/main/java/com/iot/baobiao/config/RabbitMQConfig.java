package com.iot.baobiao.config;

import com.iot.baobiao.rabbitmq.PaySuccessReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jia on 2016/10/17.
 */
@Configuration
public class RabbitMQConfig {

    private final String QUEUE_NAME = "pay-success-queue-not-durable";
    //    以下配置RabbitMQ消息服务
    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
//        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory());
        template.setMessageConverter(jsonMessageConverter());
        template.setMandatory(true);
        return template;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
//        JsonMessageConverter messageConverter = new JsonMessageConverter();
//        Jackson2JsonMessageConverter
//        messageConverter.setJsonObjectMapper();
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    TopicExchange paySuccessExchange() {
        return new TopicExchange("pay-success-exchange");
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("pay-success");
    }

    @Bean
    RabbitAdmin admin() {
        RabbitAdmin admin = new RabbitAdmin(rabbitConnectionFactory());
        admin.setIgnoreDeclarationExceptions(true); //这样即使有关rabbitmq的bean初始化失败整个web应用还能正常启动
        return admin;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory rabbitConnectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitConnectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        container.setMessageConverter(jsonMessageConverter());
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(PaySuccessReceiver receiver) {
        MessageListenerAdapter m = new MessageListenerAdapter(receiver, "handleMessage");
        m.setMessageConverter(jsonMessageConverter());
        return m;
    }

    @Bean
    public PaySuccessReceiver receiver() {
        return new PaySuccessReceiver();
    }
}
