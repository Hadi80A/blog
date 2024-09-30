package org.hammasir.blog.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LoggerService {
    RabbitTemplate rabbitTemplate;
    static String ROUTING_KEY="log";
    static String EXCHANGE_NAME="my-direct-exchange";


    public void send(String message) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTING_KEY, message);
        System.out.println("Sent message: " + message);
    }
}
