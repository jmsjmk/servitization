package com.servitization.commons.user.remote.common;

public class NewChannelEncryptKey {
    //主键ID
    private int id;
    //渠道id
    private String channelId;
    //Aes加密，解密key
    private String key;
    //记录名称
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
