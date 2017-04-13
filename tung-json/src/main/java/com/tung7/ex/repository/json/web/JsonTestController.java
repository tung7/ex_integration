package com.tung7.ex.repository.json.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.tung7.ex.repository.json.TestPerson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/9.
 * @update
 */
@RestController
@RequestMapping("/json_test")
public class JsonTestController {
    @RequestMapping("string")
    public String testJson() {
        return "String return";
    }

    @RequestMapping("map")
    public Map testMap() {
        Map map = new HashMap();
        map.put("a", "abc");
        map.put("b", "ABC");
        TestPerson person = new TestPerson();
        person.setName("testBeanName");
        person.setAge(25);
        person.setWeight(66.6);
        person.setMarried(true);
//        person.setPartner(null);
        person.setBirth(new Date());
        map.put("testBean", person);
        return map;
    }

    @RequestMapping("jacksonWrite")
    public String testJacksonObject2String() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TestPerson tp =  new TestPerson(){
            {
                setName("name");
                setAge(123);
                setWeight(1231.12);
                setPartner(null);
                setBirth(new Date());
            }
        };
        // 转成string
        String jsonStr = mapper.writeValueAsString(tp);
        //写入到文件
        mapper.writeValue(new File("f://eee.json"), tp);
        return  jsonStr;
    }

    @RequestMapping("jacksonRead")
    public List<TestPerson> testJacksonString2Object() throws IOException {
        List<TestPerson> list = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        //从文件中读取
        TestPerson tp = mapper.readValue(new File("f://eee.json"), TestPerson.class);
        //通过字符串读取
        TestPerson tp2 = mapper.readValue(
                "{\"name\":\"name\",\"age\":123,\"married\":null,\"weight\":1231.12,\"partner\":null,\"birth\":\"2017-04-09 19:47:39\"}",
                TestPerson.class);

        ObjectReader reader = mapper.readerFor(TestPerson.class);
        list.add(tp);
        list.add(tp2);
        return list;
    }



}
