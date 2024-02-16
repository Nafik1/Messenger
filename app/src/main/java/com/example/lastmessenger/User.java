package com.example.lastmessenger;

public class User {
    private String id;
    private String name;
    private boolean online;
    private String lastname;
    private String age;

    public User(String id, String name, boolean online, String lastname, String age) {
        this.id = id;
        this.name = name;
        this.online = online;
        this.lastname = lastname;
        this.age = age;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return online;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAge() {
        return age;
    }
}
