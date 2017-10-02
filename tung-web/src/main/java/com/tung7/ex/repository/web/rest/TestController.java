package com.tung7.ex.repository.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tung7.ex.repository.web.bean.People;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/7/20.
 * @update
 */
@Controller
public class TestController {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/requestBody/testString")
    public void testString(@RequestBody String body, Writer writer) throws IOException {
        writer.write(body);
    }

    @PostMapping("/requestBody/testByte")
    public void testString(@RequestBody byte[] body, OutputStream outputStream) throws IOException {
        outputStream.write(body);
    }

    @PostMapping("/requestBody/testForm")
    public void testString(@RequestBody MultiValueMap<String, String> map, Writer writer) throws IOException {
        String bodyStr = objectMapper.writeValueAsString(map);
        System.out.println(bodyStr);
        writer.write(bodyStr);
    }

    @PostMapping("/requestBody/testModelAttribute")
    public void testString(@ModelAttribute People people, Writer writer) throws IOException {
        String bodyStr = objectMapper.writeValueAsString(people);
        System.out.println(bodyStr);
        writer.write(bodyStr);
    }





}
