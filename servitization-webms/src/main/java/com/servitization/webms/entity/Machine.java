package com.servitization.webms.entity;

public class Machine {

    private String machineIp;
    private String machineName;
    private String NodeName;

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getNodeName() {
        return NodeName;
    }

    public void setNodeName(String nodeName) {
        NodeName = nodeName;
    }
}
