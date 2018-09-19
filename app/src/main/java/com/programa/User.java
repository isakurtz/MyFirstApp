package com.programa;

public class User {

    private String name;
    private String uid;
    private String email;
    private String points;
    private int tarefa;

    public User(){}
    public User(String name, String uid, String email, String points) {
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.points = points;
        tarefa = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public int getTarefa(){ return tarefa;}
    public void setTarefa(){
        tarefa++;
    }
}
