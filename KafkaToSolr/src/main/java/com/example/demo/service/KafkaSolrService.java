package com.example.demo.service;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestParam;



@Service
public class KafkaSolrService {

    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    private static final Logger logger = LoggerFactory.getLogger(KafkaSolrService.class);

    @Value("${solr.url}")
    private String solrUrl;

    public void queryIndexedFiles(String collectionName, String queryString) {
        try {
            SolrClient solrClient = new HttpSolrClient.Builder(solrUrl + "/" + collectionName).build();
            SolrQuery query = new SolrQuery();
            query.setQuery(queryString);
            query.setRows(Integer.MAX_VALUE); 
            QueryResponse response = solrClient.query(query);

            // Отправка данных в Kafka топик
            sendToKafkaTopic(response.toString());

            solrClient.close();
        } catch (IOException | SolrServerException e) {
            logger.error("Error querying indexed files from Solr", e);
        }
    }
    private void sendToKafkaTopic(@RequestParam("message") String message) {
        try {
            kafkaTemplate.send("topic-topic-3", message);
            logger.info("Data sent to Kafka topic successfully");
            logger.info(message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error sending data to Kafka topic", e);
        }
    }
    public void indexJsonMessage(String json) {
        try {
            SolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build();
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.add(jsonToSolrInputDocument(json));
            updateRequest.commit(solrClient, "/books"); 
            solrClient.close();
        } catch (IOException | SolrServerException e) {
            logger.error("Error indexing JSON message to Solr", e);
        }
    }

    private SolrInputDocument jsonToSolrInputDocument(String json) {
        SolrInputDocument solrInputDocument = new SolrInputDocument();

        try {
            JSONObject jsonObject = new JSONObject(json);

            // Пример добавления поля "id"
            if (jsonObject.has("id") && !jsonObject.isNull("id")) {
                solrInputDocument.addField("id", jsonObject.getString("id"));
            }

            // Пример добавления поля "author"
            if (jsonObject.has("author") && !jsonObject.isNull("author")) {
                solrInputDocument.addField("author", jsonObject.getString("author"));
            }

            // Пример добавления поля "bookname"
            if (jsonObject.has("bookname") && !jsonObject.isNull("bookname")) {
                solrInputDocument.addField("bookname", jsonObject.getString("bookname"));
            }

            // Пример добавления поля "year"
            if (jsonObject.has("year") && !jsonObject.isNull("year")) {
                solrInputDocument.addField("year", jsonObject.getString("year"));
            }

            // Пример добавления поля "genre"
            if (jsonObject.has("genre") && !jsonObject.isNull("genre")) {
                solrInputDocument.addField("genre", jsonObject.getString("genre"));
            }

        } catch (JSONException e) {
            logger.error("Error converting JSON to SolrInputDocument", e);
        }

        return solrInputDocument;
    }


}

