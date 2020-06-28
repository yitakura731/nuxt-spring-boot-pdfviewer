package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.api.Document;

@Mapper
public interface DocumentRepository {

    @Insert("INSERT INTO "
            +     "DOCUMENT(NAME, UPDATE_DATE, CONTENT_TYPE) "
            + "VALUES "
            +     "(#{name}, #{updateDate}, #{contentType})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void create(Document document);


    @Select("SELECT ID, NAME, UPDATE_DATE, CONTENT_TYPE FROM DOCUMENT")
    public List<Document> findAll();


    @Select("SELECT FILE FROM DOCUMENT WHERE ID = {id}")
    public byte[] getFile(int id);


    @Update("UPDATE DOCUMENT SET NAME = #{name} WHERE ID = #{id}")
    public boolean updateName(@Param("id") int id, @Param("name") String name);

}
