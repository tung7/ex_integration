package com.tung7.ex.repository.base.reflect;

/**
 * 用于内省机制 测试的JavaBean。
 * setter getter应该标准规范。不要使用链式setter。否则内省时获取不到相关的writer方法。
 * @author Tung
 * @version 1.0
 * @date 2017/9/11.
 * @update
 */

public class User {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * IQ
     */
    private int genius;
    /**
     * 是否已婚
     */
    private boolean married;
    /**
     * 是否亚洲人
     */
    private Boolean asian;

    public User() {
    }

    public User(String name, Integer age, int genius, boolean married, Boolean asian) {
        this.name = name;
        this.age = age;
        this.genius = genius;
        this.married = married;
        this.asian = asian;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getGenius() {
        return genius;
    }

    public void setGenius(int genius) {
        this.genius = genius;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public Boolean getAsian() {
        return asian;
    }

    public void setAsian(Boolean asian) {
        this.asian = asian;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", genius=" + genius +
                ", married=" + married +
                ", asian=" + asian +
                '}';
    }
}
