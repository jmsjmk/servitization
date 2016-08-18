package com.servitization.commons.user.remote.result;

import com.servitization.commons.user.remote.common.NewChannelEncryptKey;

import java.util.LinkedList;
import java.util.List;


public class QueryEncryptKeyListResp extends BaseResult {

    private List<NewChannelEncryptKey> queryEncryptKeyList = new LinkedList<>();

    public List<NewChannelEncryptKey> getQueryEncryptKeyList() {
        return queryEncryptKeyList;
    }

    public void setQueryEncryptKeyList(List<NewChannelEncryptKey> queryEncryptKeyList) {
        this.queryEncryptKeyList = queryEncryptKeyList;
    }
}
