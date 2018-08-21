package com.njfu.entity;

/**机器码对象属性
 * Created by lvxz on 2018/6/1.
 */

public class MachineLog {

    private String prcd;
    private String machId;
    private String machName;

    public MachineLog() {
    }

    public MachineLog(String prcd, String machId, String machName) {
        this.prcd = prcd;
        this.machId = machId;
        this.machName = machName;
    }

    public String getPrcd() {
        return prcd;
    }

    public void setPrcd(String prcd) {
        this.prcd = prcd;
    }

    public String getMachId() {
        return machId;
    }

    public void setMachId(String machId) {
        this.machId = machId;
    }

    public String getMachName() {
        return machName;
    }

    public void setMachName(String machName) {
        this.machName = machName;
    }

    @Override
    public String toString() {
        return "MachineLog{" +
                "prcd='" + prcd + '\'' +
                ", machId='" + machId + '\'' +
                ", machName='" + machName + '\'' +
                '}';
    }
}
