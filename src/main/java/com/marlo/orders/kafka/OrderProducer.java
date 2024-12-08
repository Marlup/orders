package com.marlo.orders.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlo.orders.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper orderMapper = new ObjectMapper();

    public void sendOrder(Order order) throws JsonProcessingException {
        String orderString = orderMapper.writeValueAsString(order);

        kafkaTemplate.sendDefault(orderString);
        System.out.println("Message sent to default topic. Order id: {}" + order.getId());
    }
}