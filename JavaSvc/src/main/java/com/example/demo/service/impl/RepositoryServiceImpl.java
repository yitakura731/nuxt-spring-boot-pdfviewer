package com.example.demo.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.api.Document;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.service.RepositoryService;



@Service
public class RepositoryServiceImpl implements RepositoryService {

    private static final String DATA_DIR;

    static {
        String dateDirPropVal = System.getProperty("com.example.demo.datadir");
        if (dateDirPropVal != null && !dateDirPropVal.isEmpty()) {
            DATA_DIR = dateDirPropVal;
        } else {
            DATA_DIR = "data";
        };
    }

    private final DocumentRepository docRepository;

    @Autowired
    public RepositoryServiceImpl(DocumentRepository docRepository) {
        this.docRepository = docRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Document createDocument(
            String name, byte[] file, String contentType) throws Exception {
        Document document = Document.builder().
                name(name).
                updateDate(new Date()).
                contentType(contentType).
                build();
        docRepository.create(document);
        String filePath = String.format("%09d", document.getId());
        Files.write(Paths.get(DATA_DIR, filePath), file);
        return document;
    }

    @Override
    public List<Document> getDocuments() throws Exception {
        return docRepository.findAll();
    }

    @Override
    public byte[] getFile(int docId) throws Exception {
        String filePath = String.format("%09d", docId);
        return Files.readAllBytes(Paths.get(DATA_DIR, filePath));
    }
}
