package com.njfu.dto;

/**
 * @ClassName: InvInventoryDto
 * @Description: Item数据库接口具体操作 具体出库时，数据交换传输对象，无具体意义，但必不可少
 * @Author: lvxz
 * @Date: 2018-06-25  19:06
 */
public class InvInventoryDto {

    String orderNumber = "";
    String lineNumber = "";
    String item = "";
    String whId = "";
    String locaId = "";
    double execQuantity = 0;

    int pdno = 0;//PrcdVariable数据库接口具体实现才所需参数

    public InvInventoryDto() {
    }

    public InvInventoryDto(String orderNumber, String lineNumber, String item, String whId, String locaId, double execQuantity) {
        this.orderNumber = orderNumber;
        this.lineNumber = lineNumber;
        this.item = item;
        this.whId = whId;
        this.locaId = locaId;
        this.execQuantity = execQuantity;
    }

    public InvInventoryDto(String orderNumber, String lineNumber, String item, String whId, String locaId, double execQuantity, int pdno) {
        this.orderNumber = orderNumber;
        this.lineNumber = lineNumber;
        this.item = item;
        this.whId = whId;
        this.locaId = locaId;
        this.execQuantity = execQuantity;
        this.pdno = pdno;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getWhId() {
        return whId;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }

    public String getLocaId() {
        return locaId;
    }

    public void setLocaId(String locaId) {
        this.locaId = locaId;
    }

    public double getExecQuantity() {
        return execQuantity;
    }

    public void setExecQuantity(double execQuantity) {
        this.execQuantity = execQuantity;
    }

    public int getPdno() {
        return pdno;
    }

    public void setPdno(int pdno) {
        this.pdno = pdno;
    }
}
