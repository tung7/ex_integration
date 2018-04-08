package com.tung7.ex.repository.json.util;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportCell {
    private String id;
    private Boolean add;
    @JsonIgnore
    private CellItem item;

    @JsonAnySetter
    public void setCell(String key, Serializable value) {
        this.item = new CellItem(key, value);
    }

    @JsonAnyGetter
    public Map<String,Serializable> getCell(){
        return Collections.singletonMap(item.getKey(), item.getValue());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAdd() {
        return add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setAnnotationIntrospector(new DimensionFieldSerializer());

        ReportCell reportCell = new ReportCell();
        reportCell.setId("1347-asdf-134123");
        reportCell.setCell("num", 123);

        System.out.println(objectMapper.writeValueAsString(reportCell));
    }

    private static class CellItem implements Serializable {
        private String key;
        private Serializable value;

        public CellItem() {
        }

        public CellItem(String key, Serializable value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Serializable getValue() {
            return value;
        }

        public void setValue(Serializable value) {
            this.value = value;
        }
    }
}
