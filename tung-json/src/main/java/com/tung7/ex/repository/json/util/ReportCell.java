package com.tung7.ex.repository.json.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Map;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2018/3/7.
 * @update
 */
public class ReportCell {
    private String name;
//    @Dimension(valueType = "id")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setAnnotationIntrospector(new DimensionFieldSerializer());

        ReportCell reportCell = new ReportCell();
        reportCell.setName("adsf");

        System.out.println(objectMapper.writeValueAsString(reportCell));
    }


}
