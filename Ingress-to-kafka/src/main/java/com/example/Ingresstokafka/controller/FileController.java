// FileController.java

package com.example.Ingresstokafka.controller;

import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FileController {

    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Value("${kafka.request.topic}")
    private String requestTopic;

    @Value("${kafka.response.topic}")
    private String responseTopic;
    
    private final KafkaTemplate<String, String> kafkaTemplate;

    public FileController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String content = new String(file.getBytes());
            kafkaTemplate.send(kafkaTopic, content);
            return ResponseEntity.ok("File uploaded!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading file");
        }
    }
    // Метод для отправки сообщения в Kafka и переадресации на /result
    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessageToKafka(@RequestParam("message") String message,
                                                      HttpServletResponse response) {
        try {
            // Отправляем сообщение в Kafka
            kafkaTemplate.send(requestTopic, message);
            // Перенаправляем на /result
            response.sendRedirect("/result");
            return ResponseEntity.ok("Redirecting to /result");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error sending message to Kafka");
        }
    }
    
}


