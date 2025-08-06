package com.example.classcancellationmanager.mapper;

import com.example.classcancellationmanager.entity.Term;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TermMapper {

    @Select("SELECT term_id, term_name FROM terms ORDER BY term_id")
    List<Term> findAll();
}
