package com.njfu.entity;

/**
 * @ClassName:
 * @Description:
 * @Author: lvxz
 * @Date: 2018-10-09  10:40
 */
public class InvMoveOrderView {

    private String transType;
    private String transCode;
    private String transName;
    private String transFlow;
    private String userDefineFlag;
    private String enableFlag;

    private Integer flagCode;

    public InvMoveOrderView(String transType, String transCode, String transName, String transFlow, String userDefineFlag, String enableFlag) {
        this.transType = transType;
        this.transCode = transCode;
        this.transName = transName;
        this.transFlow = transFlow;
        this.userDefineFlag = userDefineFlag;
        this.enableFlag = enableFlag;
    }

    public Integer getFlagCode() {
        return flagCode;
    }

    public void setFlagCode(Integer flagCode) {
        this.flagCode = flagCode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getTransFlow() {
        return transFlow;
    }

    public void setTransFlow(String transFlow) {
        this.transFlow = transFlow;
    }

    public String getUserDefineFlag() {
        return userDefineFlag;
    }

    public void setUserDefineFlag(String userDefineFlag) {
        this.userDefineFlag = userDefineFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}
