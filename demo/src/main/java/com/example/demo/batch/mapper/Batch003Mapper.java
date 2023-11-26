package com.example.demo.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.example.demo.batch.vo.Batch003Vo;

public class Batch003Mapper implements FieldSetMapper<Batch003Vo>{
    @Override
    public Batch003Vo mapFieldSet(FieldSet fieldset) {
        
        Batch003Vo vo = new Batch003Vo();
        
        vo.setUserId(fieldset.readString(0));
        vo.setUserNm(fieldset.readString(1));
        vo.setEmil(fieldset.readString(2));

        return vo;
    }
}
/*
 * NOTE: 'FieldSetMapper'
 * 
 * FlatFileItemReader와 함께 사용되어 플랫 파일의 라인을 객체로 매핑하는 역할을 담당한다. 
 */
