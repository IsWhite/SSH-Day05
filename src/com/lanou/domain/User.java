package com.lanou.domain;

import java.io.Serializable;

/**
 * 以表建类;实体类序列化 implements Serializable
 */
public class User implements Serializable{
    private int id;
    private String name;
    private String password;

    public User() {
    }
//    id自增长 所以不要有参的id构造方法
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
