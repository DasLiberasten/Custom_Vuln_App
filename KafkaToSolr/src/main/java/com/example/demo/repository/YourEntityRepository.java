package com.example.demo.repository;

// YourEntityRepository.java
import org.springframework.data.solr.repository.SolrCrudRepository;
import com.example.demo.model.YourEntity;

public interface YourEntityRepository extends SolrCrudRepository<YourEntity, String> {
}

