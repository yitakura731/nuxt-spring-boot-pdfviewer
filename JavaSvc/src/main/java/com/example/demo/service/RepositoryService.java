package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.api.Document;

@Service
public interface RepositoryService {

    public Document createDocument(String name, byte[] bytes, String contentType) throws Exception;

    public List<Document> getDocuments() throws Exception;

    public byte[] getFile(int docId) throws Exception;

}
