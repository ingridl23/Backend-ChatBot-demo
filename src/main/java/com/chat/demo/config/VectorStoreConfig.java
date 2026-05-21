package com.chat.demo.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class VectorStoreConfig {

    @Bean
    VectorStore vectorStore(
            DataSource dataSource,
            EmbeddingModel embeddingModel) {

        return PgVectorStore.builder(dataSource, embeddingModel)
                .build();
    }
}