package io.github.ust.mico.msgvalidator;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ust.mico.msgvalidator.configuration.KafkaConfig;
import io.github.ust.mico.msgvalidator.kafka.MicoCloudEventImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class MessageListener {


    /**
     * Used for messaging via stomp and websockets
     */
    @Autowired
    SimpMessagingTemplate websocketsTemplate;

    /**
     * Entry point for incoming messages from kafka.
     *
     * @param cloudEvent
     */
    @KafkaListener(topics = "${kafka.input-topic}")
    public void receive(MicoCloudEventImpl<JsonNode> cloudEvent) {
        log.info("Received CloudEvent message: {}", cloudEvent);
        websocketsTemplate.convertAndSend("/topic/messaging-bridge", cloudEvent);
    }

}
