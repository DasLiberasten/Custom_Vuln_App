
package com.example.Ingresstokafka.KafkaListener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.Ingresstokafka.service.KafkaProducerService;

@RestController
@RequestMapping("/result")
public class KafkaResponseListener {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaResponseListener.class);
    
    private final KafkaProducerService kafkaProducerService;
    
    private String receivedMessage;
    
    @Autowired
    public KafkaResponseListener(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }
    
    @GetMapping
    public ResponseEntity<String> getMessageFromKafka() {
        // Check if a message has been received from Kafka
        if (receivedMessage != null) {
            // If a message has been received, return it
            String message = receivedMessage;
            receivedMessage = null; // Reset receivedMessage for future requests
            return ResponseEntity.ok(message);
        } else {
            // If no message has been received yet, return a response indicating no message
            return ResponseEntity.ok("No message received yet");
        }
    }
    /*@GetMapping
    public ResponseEntity<String> getMessageFromKafka() {
    // Check if a message has been received from Kafka
    if (receivedMessage != null) {
        // Split the message by the SolrDocument separator
        String[] solrDocuments = receivedMessage.split("SolrDocument\\{");

        // Reconstruct the message with newline characters between SolrDocuments
        StringBuilder formattedMessage = new StringBuilder();
        for (String solrDocument : solrDocuments) {
            // Add SolrDocument prefix back and append to the formatted message
            formattedMessage.append("SolrDocument{").append(solrDocument);
            // Add newline character after each SolrDocument except the last one
            if (!solrDocument.equals(solrDocuments[solrDocuments.length - 1])) {
                formattedMessage.append("\n");
            }
        }

        receivedMessage = null; // Reset receivedMessage for future requests
        return ResponseEntity.ok(formattedMessage);
    } else {
        // If no message has been received yet, return a response indicating no message
        return ResponseEntity.ok("No message received yet");
    }
}*/


    
    @KafkaListener(topics = "topic-topic-3", groupId = "your-consumer-group")
    public void listen(ConsumerRecord<String, String> record) {
        logger.info("Received message from Kafka: {}", record.value());
        // Save the received message for later retrieval
        receivedMessage = record.value();
    }
}




