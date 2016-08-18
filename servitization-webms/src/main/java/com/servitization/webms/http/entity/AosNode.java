package com.servitization.webms.http.entity;

public class AosNode {

    private int id;
    private String name;
    private int pid;
    private int isServiceUnit;
    private String fullpath;

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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getIsServiceUnit() {
        return isServiceUnit;
    }

    public void setIsServiceUnit(int isServiceUnit) {
        this.isServiceUnit = isServiceUnit;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }
}
