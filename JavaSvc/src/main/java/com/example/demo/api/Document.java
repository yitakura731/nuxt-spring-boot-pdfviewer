package com.example.demo.api;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {

    private int id;

    private String name;

    private Date updateDate;

    private String contentType;

}
