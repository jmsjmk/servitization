package com.servitization.commons.permission.http.entity;

import java.util.Comparator;

public class PermissionEntity implements Comparator<String> {
    private String id;
    private String name;
    private String alias;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    private String group;
    private String dimension;

    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}
