package com.zzq.util;

import java.util.List;

public class Element {
    private String id;
    private String pid;
    private String name;

    private List<Element> childs;

    public Element() {
    }

    public List<Element> getChilds() {
        return childs;
    }

    public void setChilds(List<Element> childs) {
        this.childs = childs;
    }

    public Element(String id, String pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
