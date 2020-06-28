package com.example.demo.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.api.Document;
import com.example.demo.service.RepositoryService;

@RestController
@RequestMapping(value = "/api/rep")
public class RepositoryController {

    private static final Log log = LogFactory.getLog(RepositoryController.class);

    private final RepositoryService repository;

    @Autowired
    public RepositoryController(RepositoryService repository) {
        this.repository = repository;
    }

    @GetMapping("/ping")
    public String ping() { return "Hello world"; }

    @PostMapping("/documents")
    public Document postDocument(
            @RequestParam("docFile") MultipartFile file) throws Exception {
        log.info("startRegistDocument   start");
        Document retVal =repository.createDocument(
                file.getOriginalFilename(), file.getBytes(), file.getContentType());
        log.info("startRegistDocument   end");
        return retVal;
    }

    @GetMapping("/documents")
    public List<Document> getDocuments() throws Exception {
        log.info("getDocuments  start");
        List<Document> retVal = repository.getDocuments();
        log.info("getDocuments  end");
        return retVal;
    }

    @GetMapping("/documents/{docId}/files")
    public byte[] getFiles(@PathVariable("docId") int docId) throws Exception {
        log.info("getFiles  start");
        byte[] retVal = repository.getFile(docId);
        log.info("getFiles  end");
        return retVal;
    }
}
