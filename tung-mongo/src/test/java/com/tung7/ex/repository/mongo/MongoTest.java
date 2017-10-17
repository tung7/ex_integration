package com.tung7.ex.repository.mongo;

import com.mongodb.*;
import com.tung7.ex.repository.mongo.domain.Person;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/2/15.
 * @update
 */

public class MongoTest {
    private static final Logger log = LoggerFactory.getLogger(MongoTest.class);

    @Test
    public void testConnectionWithSpringMongo(){
        try {
            MongoClient client = new MongoClient("192.16.8.105", 37017);
            MongoOperations mongoOps = new MongoTemplate(client, "test_db");
            mongoOps.insert(new Person("Joe", 34));
            System.out.println(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));
//            mongoOps.dropCollection("person");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("hhahah");
    }

    @Test
    public void testConnection() {
        try {
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
            //ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress("192.16.8.105",37017);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);

            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential credential = MongoCredential.createScramSha1Credential("abc", "test_db", "abc".toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);

            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
            MongoClientOptions options =  builder.connectionsPerHost(150).build();

            //通过连接认证获取MongoDB连接
            MongoClient mongoClient = new MongoClient(addrs, options);

            //连接到数据库
            DB mongoDatabase = mongoClient.getDB("test_db");
            if (mongoDatabase != null) {
                log.debug("Connect to database successfully");
                String collectionName = "test_collection";
//                GridFSFile mongoFile = new GridFS(mongoDatabase, collectionName).createFile();
//                mongoFile.put("name","Tom");
//                mongoFile.put("age","87");
//                mongoFile.save();
                DBCollection collection = mongoDatabase.getCollection(collectionName);
                DBObject object = new BasicDBObject();
                object.put("name", "Tom2");
                object.put("age", "234");
                collection.insert(object);

                BasicDBObject query = new BasicDBObject("name", "Tom2");
                log.debug(collection.findOne(query).toString());
                collection.drop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
