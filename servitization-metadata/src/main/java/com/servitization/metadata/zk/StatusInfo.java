package com.servitization.metadata.zk;

public class StatusInfo {

    private final String name;

    private final String version;

    private final String hostname;

    private final String ip;

    public StatusInfo(String name, String version, String hostname, String ip) {
        this.name = name;
        this.version = version;
        this.hostname = hostname;
        this.ip = ip;
    }

    public StatusInfo(String fullstr) {
        String[] name_version = fullstr.split(",");
        if (name_version.length != 4)
            throw new RuntimeException(
                    "Error fullstr for which length is not 4!");
        if (name_version[0] != null && name_version[0].trim().length() > 0)
            name = name_version[0];
        else
            name = null;
        if (name_version[1] != null && name_version[1].trim().length() > 0)
            version = name_version[1];
        else
            version = null;
        if (name_version[2] != null && name_version[2].trim().length() > 0)
            hostname = name_version[2];
        else
            hostname = null;
        if (name_version[3] != null && name_version[3].trim().length() > 0)
            ip = name_version[3];
        else
            ip = null;

    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getHostname() {
        return hostname;
    }

    public String getIp() {
        return ip;
    }

    public String toString() {
        return (name == null ? "" : name) + ","
                + (version == null ? "" : version) + ","
                + (hostname == null ? "" : hostname) + ","
                + (ip == null ? "" : ip);
    }
}
