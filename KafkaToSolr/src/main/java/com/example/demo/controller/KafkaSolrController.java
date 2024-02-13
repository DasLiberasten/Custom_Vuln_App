package com.example.demo.controller;
// KafkaSolrController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.KafkaSolrService;

@RestController
@RequestMapping("/kafka-solr")
public class KafkaSolrController {

    @Autowired
    private KafkaSolrService kafkaSolrService;

    @KafkaListener(topics = "topic-topic")//, groupId = "your-consumer-group")
    public void consumeJsonMessage(String json) {
        kafkaSolrService.indexJsonMessage(json);
    }


    @KafkaListener(topics = "topic-topic-2")//, groupId = "your-consumer-group")
    public void consumeCommand(String command) {
        kafkaSolrService.queryIndexedFiles("books", command);
    }

    @PostMapping("/publish")
    public void publishJsonMessage(@RequestBody String json) {
        // This endpoint can be used to manually publish JSON messages
        kafkaSolrService.indexJsonMessage(json);
    }
}
