package com.tung7.ex.repository.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/9.
 * @update
 */

public class TestPerson {
    private String name;
    private int age;
    private Boolean married;
    private double weight;
    private TestPerson partner;
    List<String> comments;

    @JsonFormat(timezone = "GMT+08", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birth;

    public Boolean getMarried() {
        return married;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    private List<String> messages = new ArrayList<String>() {
        {
            add("You wouldn't hit a man with no trousers..");

            add("At this point, I'd set you up with a..");

            add("You know, your bobby dangler, giggle stick,..");
        }
    };

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean isMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public TestPerson getPartner() {
        return partner;
    }

    public void setPartner(TestPerson partner) {
        this.partner = partner;
    }



}
