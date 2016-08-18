package com.servitization.webms.http.entity;

public class AosMachine {
    private int id;
    private String ip;
    private String hostname;
    private String domain;
    private int isShared;
    private String fullname;
    private String agentStatus;
    private String agentLastupdate;
    private String agentVersion;
    private AosNode node;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getIsShared() {
        return isShared;
    }

    public void setIsShared(int isShared) {
        this.isShared = isShared;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus;
    }

    public String getAgentLastupdate() {
        return agentLastupdate;
    }

    public void setAgentLastupdate(String agentLastupdate) {
        this.agentLastupdate = agentLastupdate;
    }

    public String getAgentVersion() {
        return agentVersion;
    }

    public void setAgentVersion(String agentVersion) {
        this.agentVersion = agentVersion;
    }

    public AosNode getNode() {
        return node;
    }

    public void setNode(AosNode node) {
        this.node = node;
    }
}
