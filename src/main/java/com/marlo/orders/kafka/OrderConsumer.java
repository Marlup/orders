package com.marlo.orders.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlo.orders.entity.Order;
import com.marlo.orders.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@AllArgsConstructor
public class OrderConsumer {

    private final OrderRepository orderRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "#{@environment.getProperty('spring.kafka.template.default-topic')}", groupId ="1")
    public void consumeMessage(ConsumerRecord<String, String> consumerRecord) {
        // Deserialize the JSON object into JSONString
        String jsonMessage = consumerRecord.value();

        try {
            // Convert the JSON string into an Order object
            Order order = objectMapper.readValue(jsonMessage, Order.class);

            // Validate the order
            order.setValidated(true);
            order.setValidationDate(LocalDate.now());

            orderRepository.save(order);
        } catch (Exception e) {
            System.out.println("Failed to deserialize message: " + jsonMessage);
        }
    }
}