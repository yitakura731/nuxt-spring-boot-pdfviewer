package com.example.demo.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void HelloWorldテスト() throws Exception {
        this.mockMvc.perform(get("/api/rep/ping")).
            andExpect(status().isOk()).
            andExpect(content().string("Hello world"));
    }

    @Test
    void 文書テスト() throws Exception {

      // テストデータA
      int expId = 1;
      String expName = "PDF文書A.pdf";
      byte[] expBytes = Files.readAllBytes(Paths.get("src","test","resources", expName));

      // 登録
      MvcResult postResponse = this.mockMvc.perform(
              MockMvcRequestBuilders.
                  multipart("/api/rep/documents/").
                  file(new MockMultipartFile("docFile", expName, "applicaton:pdf", expBytes)).
                  param("docName", expName)).
      andExpect(status().isOk()).
      andExpect(jsonPath("id").value(String.valueOf(expId))).
      andExpect(jsonPath("name").value(expName)).
      andReturn();
      Map<String, String> actDoc = toDocumentResult(postResponse);

      // 文書取得
      MvcResult listResponse = this.mockMvc.perform(get("/api/rep/documents")).
            andExpect(status().isOk()).
            andReturn();
      List<Map<String, String>> getResult = toDocumentListResult(listResponse);
      assertEquals(1, getResult.size());
      for (Map<String, String> expDoc : getResult) {
        assertEquals(actDoc.get("id"), expDoc.get("id"));
        assertEquals(actDoc.get("name"), expDoc.get("name"));
        assertEquals(actDoc.get("contentType"), expDoc.get("contentType"));
        assertEquals(actDoc.get("updateDate"), expDoc.get("updateDate"));
      }

      // 文書実態取得
      this.mockMvc.perform(
            get(String.format("/api/rep/documents/%s/files", actDoc.get("id")))).
            andExpect(status().isOk()).
            andReturn();
    }

//    @Test
//    void 文書登録テスト() throws Exception {
//
//        // テストデータ
//        Path testPDF = Paths.get("src","test","resources", "pdfs.zip");
//        MockMultipartFile file = new MockMultipartFile(
//                "docFile", Files.toByteArray(testPDF.toFile()));
//
//        // 文書登録。ジョブ
//        MvcResult response = this.mockMvc.perform(
//                MockMvcRequestBuilders.
//                    multipart("/api/rep/documents/").
//                    file(file)).
//        andExpect(status().isOk()).
//        andExpect(jsonPath("job.label").value(JobLabel.REGIST_DOC.name())).
//        andExpect(jsonPath("job.status").value(JobStatus.PREPARE.name())).
//        andReturn();
//
//        Map<String, Map<String, String>> result = toPostDocResult(response);
//        String expJobId = result.get("job").get("id");
//        String expFolderId = result.get("job").get("folderId");
//
//        boolean isSuccess = false;
//        for (int index = 1; index < 5; index++) {
//            Thread.sleep(3000);
//
//            // ジョブIDを指定してジョブを取得
//            MvcResult resp = this.mockMvc.perform(get(
//                    String.format("/api/rep/jobs/%s", expJobId))).
//                    andExpect(status().isOk()).
//                    andExpect(jsonPath("id").value(expJobId)).
//                    andExpect(jsonPath("label").value(JobLabel.REGIST_DOC.name())).
//                    andExpect(jsonPath("folderId").value(expFolderId)).
//                    andReturn();
//
//            Map<String, String> jobResp = toJobResult(resp);
//            if (jobResp.get("status").equals(JobStatus.SUCCESS.name())) {
//                isSuccess = true;
//                break;
//            }
//        }
//        assertTrue(isSuccess);
//    }

    private List<Map<String, String>> toDocumentListResult(MvcResult result) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<List<Map<String, String>>>(){});
    }

    private Map<String, String> toDocumentResult(MvcResult result) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<Map<String, String>>(){});
    }

}
