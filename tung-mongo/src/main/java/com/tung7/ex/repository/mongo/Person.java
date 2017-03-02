package com.tung7.ex.repository.mongo;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/2/15.
 * @update
 */

public class Person {
    private String id;
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
