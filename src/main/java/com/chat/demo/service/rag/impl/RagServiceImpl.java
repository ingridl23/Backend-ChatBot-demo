package com.chat.demo.service.rag.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.ChatRequest;
import com.chat.demo.dto.ChatResponse;
import com.chat.demo.service.rag.RagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RagServiceImpl implements RagService {

    private final VectorStore vectorStore;

    private final ChatClient chatClient;

    @Override
    public void ingestDocument(String filePath) {

        PagePdfDocumentReader reader =
                new PagePdfDocumentReader(filePath);

        List<Document> docs = reader.read();

        docs = new TokenTextSplitter().apply(docs);

        vectorStore.add(docs);
    }

    @Override
    public List<Document> searchRelevantChunks(String question) {

        SearchRequest request = SearchRequest.builder()
                .query(question)
                .topK(5)
                .similarityThreshold(0.7)
                .build();

        return vectorStore.similaritySearch(request);
    }

    @Override
    public String buildContext(List<Document> docs) {

        return docs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public ChatResponse ask(ChatRequest request) {

        List<Document> docs =
                searchRelevantChunks(request.getQuestion());

        String context = buildContext(docs);

        String prompt = """
                CONTEXTO:
                %s

                PREGUNTA:
                %s
                """.formatted(context, request.getQuestion());

        String answer = chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();

        ChatResponse response = new ChatResponse();

        response.setAnswer(answer);

        return response;
    }
}